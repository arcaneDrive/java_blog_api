package com.securityGear.app.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.securityGear.app.Entities.User;
import com.securityGear.app.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;





@RestController
@RequestMapping("api/v1/users")
public class UserController {




//    needs access to service object

    //    @Autowired
    UserService userService;

//    private byte[] decodeImage(String base64ImageData) {
//        return Base64.getDecoder().decode(base64ImageData);
//    }



    public byte[] decodeImage(String src)
    {
        if (src == null || src.isEmpty()) {
            // Handle the null or empty case appropriately
            // For example, return a default image or throw a custom exception
            throw new IllegalArgumentException("Image data is missing.");
        }
        try {
            return Base64.getDecoder().decode(src);
        } catch (IllegalArgumentException e) {
            // Handle invalid Base64 data
            throw new IllegalArgumentException("Invalid image data format.", e);
        }
    }




    //  this is the constructor injector
    public UserController(UserService userService) {
        this.userService = userService;
    }

//  -------------------------
//   returns data
//  -------------------------

//  this returns all the Blogs
    @GetMapping("/users")
    public List<User> getUsers(){
//        List<User> users = userService.getUsers();;
//        for(User user: users){

//
//        }

        return userService.getUsers();
    }






    @GetMapping("/getByUserName/{username}")
    public void getUsersPassword(@PathVariable String username){
        userService.getUsername(username);
    }









    //  this getUserImage the userById in the database and returns it
    @GetMapping("/getUserImage/{userId}")
    public ResponseEntity<?> getUserImage(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        byte[] userImage = decodeImage(user.getProfileImageBase64());
        if (userImage != null && userImage.length > 0) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Change this based on your image type
                    .body(userImage); // Return the byte array as the response body
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // No image data available
        }
    }









//  -------------------------
//  void services /no return data
//  -------------------------


    @PostMapping(value = "/createUser", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addUser(@RequestPart("user") String userJson, @RequestPart("image") MultipartFile imageFile) {


        try {
            // Convert the image file to Base64 string
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(userJson, User.class);

            if (!imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                user.setProfileImageBase64(base64Image);
                // Set the Base64 string on the User object
            }

            // Save the user along with the profile image
            userService.addUser(user);

            // Return a success response
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }






    //  this updates a user's details if they exists
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId,@RequestBody User user) {
        try {
            User userWithId = userService.getUserById(userId);
            userWithId.setUserName(user.getUserName());
            userWithId.setEmail(user.getEmail());
            userService.updateUser(userWithId);
            // Return a success response
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user: " + e.getMessage());
        }
    }


//  -------------------------
//  requires a path variable/path parameter in the uri
//  -------------------------

//  this deletes specified user identified by its id a path variable/path parameter
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (EntityNotFoundException e) {
            // If the user is not found, return a 404 Not Found response
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        } catch (Exception e) {
            // If there is any other error, return a 500 Internal Server Error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }


}
