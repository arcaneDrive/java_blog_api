package com.securityGear.app.Controllers;


import com.securityGear.app.Entities.BlogLike;
import com.securityGear.app.Services.BlogLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/likes")
public class BlogLikeController {

    private final BlogLikeService blogLikeService;

    public BlogLikeController(BlogLikeService blogLikeService) {
        this.blogLikeService = blogLikeService;

    }


//  retrieving the blog by referencing its id in the uri
    @GetMapping("blog/{blogId}")
    public List<BlogLike> getBloglikesByBlogId(@PathVariable Long blogId) {
        return blogLikeService.getLikesByBlogId(blogId);
    }

//  return user likes to different blogPosts hopefully

    @GetMapping("/user/{userId}")
    public List<BlogLike> getBloglikesByUserId(@PathVariable Long userId) {
        return blogLikeService.getLikesByUserId(userId);
    }





//  adding like per user
    @PostMapping("/addLike/{blogId}/{userId}")
    public BlogLike addComment(@PathVariable Long blogId,@PathVariable Long userId, @RequestBody BlogLike blogLike) {

        return blogLikeService.addLikes(blogId,userId,blogLike);
    }

//  removing likes
    @DeleteMapping("/{blogLikeId}")
    public ResponseEntity<?> deleteLike(@PathVariable Long blogLikeId) {
        blogLikeService.deleteBlogLike(blogLikeId);
        return ResponseEntity.noContent().build();
    }
}

// a user can have many likes but he cant like a blogPost twice

