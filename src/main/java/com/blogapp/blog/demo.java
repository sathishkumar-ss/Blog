package com.blogapp.blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
    @GetMapping("/welcome")
    public String welcome(){
        return "Starting";
    }
    
}
