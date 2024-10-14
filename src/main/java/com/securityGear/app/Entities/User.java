package com.securityGear.app.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
//@Data
@Entity
@AllArgsConstructor //generate all arguments constructor
@NoArgsConstructor  // to generate a default constructor meaning one without arguments
public class User {

    @Id
//  strategy to generate the id like uuid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;



    private String userName;
    private String password;
    private String profileImageUrl;

    @Lob
    @JsonIgnore
    @Column(name = "profile_image_base64", columnDefinition = "varchar(255)")
    private String profileImageBase64;

    private String location; 
    private String email;
    private String joinDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
    private List<BlogLike> blogLike = new ArrayList<>();


}





//-------------------------------------------------------------------------------
//random questions
//what is a comments
//users create comments
//users can make comments in comments
//users edit comments
//users like comments
//users dislike comments
//does a students id have to be unique in a


//one record points to one record the other sides
//the other record has its view to
