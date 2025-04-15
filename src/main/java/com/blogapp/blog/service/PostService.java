package com.blogapp.blog.service;

import com.blogapp.blog.model.Post;
import com.blogapp.blog.model.User;
import com.blogapp.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post create(Post post, User user) {
        post.setCreatedBy(user);
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
