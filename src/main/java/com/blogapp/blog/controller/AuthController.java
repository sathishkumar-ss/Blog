package com.blogapp.blog.controller;

import com.blogapp.blog.model.User;
import com.blogapp.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        boolean valid = userService.validateUser(user.getUsername(), user.getPassword());
        return valid ? "Login success" : "Invalid credentials";
    }
}
