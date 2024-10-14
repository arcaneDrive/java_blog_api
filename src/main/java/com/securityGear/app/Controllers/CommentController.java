package com.securityGear.app.Controllers;

import com.securityGear.app.Entities.Comment;
import com.securityGear.app.Services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


//  get the blog by referencing its id in the uri
    @GetMapping("/{blogId}")
    public ResponseEntity<List<Comment>> getCommentsByBlogId(@PathVariable Long blogId) {
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);

        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No comments found
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(comments); // Return comments with 200 OK status
        }
    }



    //  adds a comment to a specific blog
    @PostMapping("/addComment/{blogId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long blogId, @RequestBody Comment comment) {
        try {
            Comment addedComment = commentService.addCommentToBlog(blogId, comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedComment); // Return the added comment with 201 Created status
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Blog not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // General error
        }
    }



    //  updates a comment
    @PostMapping("/updateComment/{blogId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long blogId, @RequestBody Comment comment) {
        try {
            Comment updatedComment = commentService.updateCommentToBlog(blogId, comment);
            return ResponseEntity.ok(updatedComment); // Return updated comment with 200 OK status
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Comment not found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // General error
        }
    }



    //  deletes a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok("Comment deleted successfully."); // Return success message
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete comment: " + e.getMessage());
        }
    }

}
