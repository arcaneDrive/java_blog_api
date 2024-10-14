package com.securityGear.app.Repositories;


import com.securityGear.app.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogPost_BlogId(Long blogId);
}