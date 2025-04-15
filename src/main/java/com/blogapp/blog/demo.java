package com.blogapp.blog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
    @GetMapping("/blog")
    public String blog(){
        return "Changed application properties for postgres";
    }
    
}
