package com.blogapp.blog.service;

import com.blogapp.blog.model.User;
import com.blogapp.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        return userRepository.save(user);
    }

    public boolean validateUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.isPresent() &&
                optionalUser.get().getPassword().equals(password);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
