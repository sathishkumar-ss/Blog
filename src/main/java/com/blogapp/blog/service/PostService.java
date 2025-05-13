package com.blogapp.blog.service;

import com.blogapp.blog.repository.PostRepository;
import com.blogapp.blog.model.Post;
import com.blogapp.blog.util.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private MarkdownProcessor markdownProcessor;

    public List<Post> getAllPosts() {
        System.out.println("sathishhh");
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post) {
        // Process Markdown content to HTML
        if (post.getContent() != null) {
            post.setHtmlContent(markdownProcessor.convertMarkdownToHtml(post.getContent()));
        }
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        
        // Process Markdown content to HTML
        if (postDetails.getContent() != null) {
            post.setHtmlContent(markdownProcessor.convertMarkdownToHtml(postDetails.getContent()));
        }
        
        post.setAuthor(postDetails.getAuthor());
        
        // Only update image if it's provided
        if (postDetails.getImageName() != null) {
            post.setImageName(postDetails.getImageName());
            post.setImageUrl(postDetails.getImageUrl());
        }
        
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
// package com.blogapp.blog.service;

// import com.blogapp.blog.model.Post;
// import com.blogapp.blog.model.User;
// import com.blogapp.blog.repository.PostRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class PostService {

//     @Autowired
//     private PostRepository postRepository;

//     public Post create(Post post, User user) {
//         post.setCreatedBy(user);
//         post.setCreatedAt(LocalDateTime.now());
//         return postRepository.save(post);
//     }

//     public List<Post> getAll() {
//         return postRepository.findAll();
//     }
// }
