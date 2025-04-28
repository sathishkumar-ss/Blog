package com.blogapp.blog.controller;

import com.blogapp.blog.model.Post;
import com.blogapp.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        System.out.println("sathish1");
        return postService.getAllPosts();
    }
    
    @GetMapping("/search")
    public List<Post> searchPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String author) {
        // Here you would implement search logic with the service
        // For now we just return all posts
        System.out.println("Search request with: title=" + title + ", content=" + content + ", author=" + author);
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Optional<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Only set the author to the logged-in username if the author field is null or empty
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal()) && 
            (post.getAuthor() == null || post.getAuthor().trim().isEmpty())) {
            // Set the author as the logged-in username
            post.setAuthor(authentication.getName());
        }
        
        return postService.createPost(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}

// package com.blogapp.blog.controller;

// import com.blogapp.blog.model.Post;
// import com.blogapp.blog.model.User;
// import com.blogapp.blog.service.PostService;
// import com.blogapp.blog.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/posts")
// public class PostController {

//     @Autowired
//     private PostService postService;

//     @Autowired
//     private UserService userService;

//     @PostMapping
//     public Object createPost(@RequestBody Post post, @RequestParam String username, @RequestParam String password) {
//         if (!userService.validateUser(username, password)) {
//             return "Unauthorized";
//         }
//         User user = userService.getByUsername(username).get();
//         return postService.create(post, user);
//     }

//     @GetMapping
//     public List<Post> getAll() {
//         return postService.getAll();
//     }
// }
