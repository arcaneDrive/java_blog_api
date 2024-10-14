package com.securityGear.app.Repositories;


import com.securityGear.app.Entities.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.securityGear.app.Entities.BlogPost;

import java.util.List;

@Repository
public interface BlogRepo extends JpaRepository<BlogPost, Long> {

//    List<BlogLike> findByBlogLike_BlogLikeId();
//add more queries to the database here
}


