package com.securityGear.app.Services;


import com.securityGear.app.Entities.User;
import com.securityGear.app.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
public class UserService {


    @Autowired
    private JwtService jwtService;

    //    @Autowired
//  repo object important coupling getting database object
//  it has some useful methods, the UserRepo object that is
    private final UserRepo userRepo;

//    for encrypting your database we need to change how it looks in the database first
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//  constructor injection -- injecting object through constructor
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private AuthenticationManager authenticationManager;


//  -------------------------
//  the start of queries
//  -------------------------

//-----------------------------------------------------------------------------

//  -------------------------
//  return services /return services
//  -------------------------



//  returns all users
    public List<User> getUsers(){
        return userRepo.findAll();
    }


//  returns a single userById
    public User getUserById(Long userId) {
        return userRepo.findById(userId).orElse(new User());
    }


    @Transactional
    public void getUsername(String username) {
        User marvin = userRepo.findByUserNameIgnoreCase(username);
        System.out.println(marvin.getPassword());
    }


//  -------------------------
//  void services /no return services
//  -------------------------


//  adds a User -> PostMapping

    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }


    //  updates/edits User details -> PutMapping
    public void updateUser(User user) {
        userRepo.save(user);
    }


    //  deletes/removes a user -> DeleteMapping
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }



//  method to authenticate the users
    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserName());
        }

        return "fail";

    }
}