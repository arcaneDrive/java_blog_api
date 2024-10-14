package com.securityGear.app.Services;
import com.securityGear.app.Entities.User;
import com.securityGear.app.Entities.UserDetailsCustom;
import com.securityGear.app.Repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//we will have to override UserDetailsService
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserNameIgnoreCase(username);

        if(user == null){
            System.out.println("user not found in my service");
            throw new UsernameNotFoundException("user not found");
        }


        return new UserDetailsCustom(user);
    }
}
