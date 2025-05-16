package com.blogapp.blog.controller;

import com.blogapp.blog.model.Post;
import com.blogapp.blog.service.PostService;
import com.blogapp.blog.service.FileStorageService;
import com.blogapp.blog.util.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private MarkdownProcessor markdownProcessor;

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
    public ResponseEntity<Post> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setAuthor(author);

            if (image != null && !image.isEmpty()) {
                String imageName = fileStorageService.storeFile(image);
                post.setImageName(imageName);
                post.setImageUrl("/api/files/" + imageName);
            }

            return ResponseEntity.ok(postService.savePost(post));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("author") String author,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Optional<Post> postOptional = postService.getPostById(id);
            if (postOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            Post existingPost = postOptional.get();
            existingPost.setTitle(title);
            existingPost.setContent(content);
            existingPost.setAuthor(author);

            if (image != null && !image.isEmpty()) {
                // Delete old image if exists
                if (existingPost.getImageName() != null) {
                    fileStorageService.deleteFile(existingPost.getImageName());
                }
                String imageName = fileStorageService.storeFile(image);
                existingPost.setImageName(imageName);
                existingPost.setImageUrl("/api/files/" + imageName);
            }

            return ResponseEntity.ok(postService.savePost(existingPost));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
    
    /**
     * Preview endpoint to convert markdown to HTML
     * This allows client-side preview without saving the post
     */
    @PostMapping("/preview")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<String, String>> previewMarkdown(@RequestBody Map<String, String> payload) {
        try {
            String markdownContent = payload.get("markdown");
            String htmlContent = markdownProcessor.convertMarkdownToHtml(markdownContent);
            
            Map<String, String> response = new HashMap<>();
            response.put("html", htmlContent);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
