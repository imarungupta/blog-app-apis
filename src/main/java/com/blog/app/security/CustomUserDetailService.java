package com.blog.app.security;

import com.blog.app.entities.User;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // Loading user from database by username, here username is nothing but email
        User user = this.userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","user name",username));
        // Now we have to user but we need to return UserDetails , so let's implement UserDetail in user and implement all the methods in that class

        return user;
    }


}
