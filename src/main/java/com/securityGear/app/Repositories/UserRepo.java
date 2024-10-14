package com.securityGear.app.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.securityGear.app.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUserNameIgnoreCase(String username);
//  add more queries to the database here
}
