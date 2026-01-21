package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userServiceV2")
public class UserServiceImplV2 implements UserService {

    private final UserRepository repo;

    public UserServiceImplV2(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        user.setName(user.getName().toUpperCase());
        return repo.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);

        existing.setName(user.getName().toUpperCase());
        existing.setEmail(user.getEmail());

        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        repo.delete(user);
    }
}
