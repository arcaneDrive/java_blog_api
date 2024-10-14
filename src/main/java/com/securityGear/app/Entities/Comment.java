package com.securityGear.app.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment") // Explicit table name
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id") // Explicit column name
    private Long commentId;

    @Column(name = "content")
    private String content;

    // Foreign key relationship to BlogPost
    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "blog_id", nullable = false)
//    @JsonBackReference

    @JsonIgnore
    private BlogPost blogPost;

}
