package com.blogapp.blog.controller;

import com.blogapp.blog.model.Post;
import com.blogapp.blog.model.User;
import com.blogapp.blog.service.PostService;
import com.blogapp.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Object createPost(@RequestBody Post post, @RequestParam String username, @RequestParam String password) {
        if (!userService.validateUser(username, password)) {
            return "Unauthorized";
        }
        User user = userService.getByUsername(username).get();
        return postService.create(post, user);
    }

    @GetMapping
    public List<Post> getAll() {
        return postService.getAll();
    }
}
