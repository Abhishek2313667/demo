package com.example.demo.service.impl;
import org.springframework.dao.DataIntegrityViolationException;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceV1")
public class UserServiceImplV1 implements UserService {

    private final UserRepository repo;

    public UserServiceImplV1(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        try {
            return repo.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());

        try {
            return repo.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateEmailException("Email already exists");
        }
    }


    @Override
    public User patchUser(Long id, User user) {
        User existing = getUserById(id);

        if (user.getName() != null) {
            existing.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }
        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        repo.delete(user);
    }
}
