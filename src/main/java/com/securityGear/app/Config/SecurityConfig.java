package com.securityGear.app.Config;

import com.securityGear.app.Services.MyUserDetailsService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//the @config makes this a configuration class for spring_boot
@EnableWebSecurity
//force spring to use this configuration |^^^^^^
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

//  one of the bean objects to be later called by spring
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

//      imperative definitions
//      create an anonymous class then implement the method-------------------------|down|


        Customizer<CsrfConfigurer<HttpSecurity>> customCsrfConfig = new Customizer<CsrfConfigurer<HttpSecurity>> (){
//      imperative way -----------------------------|^ up
            @Override
            public void customize(CsrfConfigurer<HttpSecurity> customizer){
                customizer.disable();
            }
        };

//      don't need formLogin Now
//        httpSecurity.formLogin(Customizer.withDefaults());


//      disable the csrf check
        httpSecurity.csrf(customCsrfConfig);
//      enable postman login
        httpSecurity.httpBasic(Customizer.withDefaults());
//      free paths
        httpSecurity.authorizeHttpRequests(request -> request
                .requestMatchers("login","api/v1/users/createUser").permitAll()
                .anyRequest().authenticated());
        httpSecurity.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//      this httpSecurity.build method present in the httpSecurity object of  returns an object of securityFilter chain
        return httpSecurity.build();
    }


// for jwt but used by default by the authentication provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //  this is to use our own authenticationProvider meaning the database objects
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(10));
        provider.setUserDetailsService(myUserDetailsService);
        return provider;
    }







//  use your own user details service

//    @Bean
//    public UserDetailsService userDetailsService(){
////creating a user because we need to use this instead of the application.Properties
//        UserDetails user1 = User.withDefaultPasswordEncoder().username("anthony").password("1234").roles("USER").build();
//
//
////      use our own user details service
//        return new InMemoryUserDetailsManager(user1);
//    }









}
