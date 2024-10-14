package com.securityGear.app.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog_post") // Explicit table name
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id") // Explicit column name
    private Long blogId;

    @Column(name = "blog_title")
    private String blogTitle;

    @Column(name = "blog_content")
    private String blogContent;

    @Column(name = "blog_author")
    private String blogAuthor;

    @Column(name = "blog_category")
    private String blogCategory;

    @Column(name = "blog_description")
    private String blogDescription;

//    @Lob
    @Column(name = "blog_cover_image")
    private String blogCoverImage;

    @Column(name = "blog_publish_date")
    private String blogPublishDate;

    @Column(name = "total_likes")
    private int totalLikes;


    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlogLike> blogLike;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();


//  method to push to list
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBlogPost(this); // Set the bidirectional relationship
    }


}


