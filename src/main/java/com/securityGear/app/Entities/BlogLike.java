package com.securityGear.app.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BlogLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogLikeId;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private BlogPost blogPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
}
