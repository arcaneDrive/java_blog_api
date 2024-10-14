package com.securityGear.app.Services;


import com.securityGear.app.Entities.BlogLike;
import com.securityGear.app.Entities.BlogPost;
import com.securityGear.app.Repositories.BlogLikeRepo;
import com.securityGear.app.Repositories.BlogRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BlogService {

    //    @Autowired

//  repo object important coupling getting database object
//  it has some useful methods the blogRepo object
    private final BlogRepo blogRepo;

//  constructor injection -- injecting object through constructor

    public BlogService(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;


    }


//  -------------------------
//  the start of queries
//  -------------------------

//-----------------------------------------------------------------------------

//  -------------------------
//  return services /return services
//  -------------------------

    @Transactional
    public List<BlogPost> getBlogs(){ return blogRepo.findAll(); }

    @Transactional
    public BlogPost getBlogById(Long blogId) {
        return blogRepo.findById(blogId).orElse(new BlogPost());
    }


//  -------------------------
//  void services /no return services
//  -------------------------


//  adds a blog post -> PostMapping

    public void addBlog(BlogPost blogPost) {
        blogRepo.save(blogPost);
    }


//  updates/edits a blog post -> PutMapping
    public void updateBlog(Long blogId,BlogPost blogPost) {
//
        BlogPost blogPostChange = blogRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));


//      setContent
        blogPostChange.setBlogContent(blogPost.getBlogContent());

        blogRepo.save(blogPostChange);
    }


//  deletes/removes a blog post -> DeleteMapping
    public void deleteBlog(Long blogId) {
        blogRepo.deleteById(blogId);
    }




}