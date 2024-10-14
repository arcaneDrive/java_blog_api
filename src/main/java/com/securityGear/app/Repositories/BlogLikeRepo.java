package com.securityGear.app.Repositories;

import com.securityGear.app.Entities.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogLikeRepo extends JpaRepository<BlogLike, Long> {
//  we can do two things here
// -1
//    .returning the likes for a specific blogPost
    List<BlogLike> findByBlogPost_BlogId(Long blogId);
// -2
//    .return a users likes to different posts
    List<BlogLike> findByUser_UserId(Long userId);

    Optional<BlogLike> findByUser_UserIdAndBlogPost_BlogId(Long userId, Long blogPostId);

    boolean existsByUser_UserIdAndBlogPost_BlogId(Long userId, Long blogPostId);

}
