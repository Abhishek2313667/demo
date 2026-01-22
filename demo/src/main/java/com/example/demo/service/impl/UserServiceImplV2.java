package com.example.demo.service.impl;

import com.example.demo.entity.User;
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
        user.setName(user.getName().toUpperCase());
        return repo.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        return createUser(user);
    }

    // ✅ ADD THIS METHOD
    @Override
    public User patchUser(Long id, User user) {
        User existing = repo.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        if (user.getName() != null) {
            existing.setName(user.getName().toUpperCase());
        }

        if (user.getEmail() != null) {
            existing.setEmail(user.getEmail());
        }

        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        if (user != null) {
            repo.delete(user);
        }
    }
}
