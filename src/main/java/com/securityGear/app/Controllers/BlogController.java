package com.securityGear.app.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityGear.app.Entities.BlogPost;
import com.securityGear.app.Entities.User;
import com.securityGear.app.Services.BlogService;
import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// -----------------attention----------------------------
// the blogPost should have the like


import java.util.Base64;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
public class BlogController {

//    @Autowired
    BlogService blogService;
//  this is the constructor injector
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
//  generates the Csrf-token
//    @GetMapping("/csrf-token")
//    public CsrfToken getCsrfToken(HttpServletRequest request){
//        return (CsrfToken) request.getAttribute("_csrf");
//    }
//  -------------------------
//   returns data
//  -------------------------
//  this returns all the Blogs


@GetMapping("/")
public ResponseEntity<List<BlogPost>> getBlogs() {
    List<BlogPost> blogs = blogService.getBlogs();

    if (!blogs.isEmpty()) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(blogs);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());

    }
}


    //  this filters the userById in the database and returns it
    @RequestMapping("/filterBlog/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable Long blogId) {
        BlogPost blogPost = blogService.getBlogById(blogId);

        // Check if the blogPost is null or has no content (assuming title is a property)
        if (blogPost.getBlogContent() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Such BlogPost"); // Blog not found
        }else{
            return ResponseEntity.ok(blogPost); // Return the found blog post
        }

    }
// comments
//  -------------------------
//  void services /no return data
//  -------------------------



//  this need a change to use id
//  this adds a new blog post
    @PostMapping("/postBlog")
    public ResponseEntity<String> addBlog(@RequestBody BlogPost blogPost) {
        // Assuming blogService.addBlog() returns the added BlogPost or a confirmation value
        try {
            blogService.addBlog(blogPost);
            return ResponseEntity.status(HttpStatus.CREATED).body("Blog post added successfully."); // Return success message
        } catch (Exception e) {
            // Handle any exception that might occur during the process
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add blog post: " + e.getMessage());
        }
    }

//  this updates a blog if it exists
    @PutMapping(value = "/updateBlog/{blogId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateBlog(@RequestPart("blog") String blogJson, @PathVariable Long blogId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BlogPost post = objectMapper.readValue(blogJson, BlogPost.class);

            // Attempt to update the blog post
            blogService.updateBlog(blogId, post);

            return ResponseEntity.ok("Blog post updated successfully."); // Return success message
        } catch (Exception e) {
            // Handle any exception that might occur during the process
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update blog post: " + e.getMessage());
        }
    }



//  -------------------------
//  requires a path variable/path parameter in the uri
//  -------------------------

//  this deletes specified blog identified by its id a path variable/path parameter
    @DeleteMapping("/deleteBlog/{blogId}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long blogId) {
        try {
            if(!(blogService.getBlogById(blogId) == null)){
                return ResponseEntity.ok("<h1>Blog post not found</h1>"); // Return success message
            }else{
                blogService.deleteBlog(blogId);
                return ResponseEntity.ok("Blog post deleted successfully."); // Return success message
            }
            // Attempt to delete the blog post

        } catch (Exception e) {
            // Handle any exception that might occur during the process
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete blog post: " + e.getMessage());
        }
    }









}
