package com.securityGear.app.Services;

import com.securityGear.app.Entities.BlogPost;
import com.securityGear.app.Entities.BlogLike;
import com.securityGear.app.Entities.User;
import com.securityGear.app.Repositories.BlogRepo;
//import org.springframework.beans.factory.annotation.Autowired;
import com.securityGear.app.Repositories.BlogLikeRepo;
import com.securityGear.app.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BlogLikeService {

    //    @Autowired
    private final BlogLikeRepo blogLikeRepo;
    private final BlogRepo blogRepo;
    private final UserRepo userRepo;

    //  constructor injector
    public BlogLikeService(BlogLikeRepo blogLikeRepo, BlogRepo blogRepo,UserRepo userRepo) {
        this.blogLikeRepo = blogLikeRepo; //??????
        this.blogRepo = blogRepo;
        this.userRepo = userRepo;
    }

    //  return likes for a particular blogPost by referencing the blogs id
//    check your cappsss---a-s-a-a-s-as-a-sa----------------------------------------in blogLikeRepo
    @Transactional
    public List<BlogLike> getLikesByBlogId(Long blogId) {
        return blogLikeRepo.findByBlogPost_BlogId(blogId);
    }
    @Transactional
    public List<BlogLike> getLikesByUserId(Long userId) {
        return blogLikeRepo.findByUser_UserId(userId);
    } // ??????

    public BlogLike addLikes(Long blogId, Long userId, BlogLike blogLike) {

        BlogPost blogPost = blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!blogLikeRepo.existsByUser_UserIdAndBlogPost_BlogId(userId,blogId)){
            // increment the total likes in blogPost
            blogPost.setTotalLikes(blogPost.getTotalLikes() + 1);
            // Save the BlogPost (with the updated list) and the BlogLike

//          don't need to save this
//            blogRepo.save(blogPost);

            blogLike.setUser(user);
            blogLike.setBlogPost(blogPost);
            return blogLikeRepo.save(blogLike);// Associate the BlogLike with the BlogPost

        }else {
            System.out.println("already liked this post");
            return new BlogLike();
        }

    }

    public void deleteBlogLike(Long blogLikeId) {
        blogLikeRepo.deleteById(blogLikeId);
    }
}


//firebase services

//Firebase id
//springimages-2fc92
//
//FileInputStream serviceAccount =
//        new FileInputStream("path/to/serviceAccountKey.json");
//
//FirebaseOptions options = new FirebaseOptions.Builder()
//        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//        .build();
//
//FirebaseApp.initializeApp(options);