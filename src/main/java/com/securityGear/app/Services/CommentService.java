package com.securityGear.app.Services;

import com.securityGear.app.Entities.BlogPost;
import com.securityGear.app.Entities.Comment;
import com.securityGear.app.Repositories.BlogRepo;
import com.securityGear.app.Repositories.CommentRepo;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

//    @Autowired
    private final CommentRepo commentRepo;
    private final BlogRepo blogRepo;

//  constructor injector
    public CommentService(CommentRepo commentRepo, BlogRepo blogRepo) {
        this.commentRepo = commentRepo;
        this.blogRepo = blogRepo;
    }

//  return comments for a particular blogPost by referencing its id
    public List<Comment> getCommentsByBlogId(Long blogId) {
        return commentRepo.findByBlogPost_BlogId(blogId);
    }

//  this is where comments are added to their table but also to the blog

    public Comment addCommentToBlog(Long blogId, Comment comment) {

        BlogPost blogPost = blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));

//      this adds the comment to the blogPost's comments property
        blogPost.addComment(comment);
        commentRepo.save(comment);
        return comment;
    }


    public Comment updateCommentToBlog(Long blogId, Comment comment) {

        BlogPost blogPost = blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));

//      this adds the comment to the blogPost's comments property
        blogPost.addComment(comment);
        blogRepo.save(blogPost);
        commentRepo.save(comment);
        return comment;
    }




    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
