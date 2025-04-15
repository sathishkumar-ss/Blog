package com.blogapp.blog.service;

import com.blogapp.blog.repository.PostRepository;
import com.blogapp.blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setAuthor(postDetails.getAuthor());
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
