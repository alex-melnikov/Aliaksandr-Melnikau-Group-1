package org.shop.api.impl;

import java.util.List;

import org.shop.api.UserService;
import org.shop.data.User;
import org.shop.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private UserRepository repository;
    
    public void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    public Long registerUser(User user) {
        return repository.createUser(user);
    }

    public User getUserById(Long userId) {
        return repository.getUserById(userId);
    }

    public void updateUserProfile(User user) {
        repository.updateUser(user);
    }

    public List<User> getUsers() {
        return repository.getUsers();
    }
}
