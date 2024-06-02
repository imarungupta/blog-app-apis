package com.blog.app.controllers;

import com.blog.app.exceptions.APIException;
import com.blog.app.payloads.UserDto;
import com.blog.app.security.jwt.JwtAuthRequest;
import com.blog.app.security.jwt.JwtAuthResponse;
import com.blog.app.security.jwt.JwtTokenHelper;
import com.blog.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class JwtAuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws Exception {

     // authenticate this username and password using authentication manager
       /* this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword()); */

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        try {
            this.authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            System.out.println("Invalid credential !!");
            //throw new Exception("Invalid Username or Password");
            throw new APIException("Invalid Username or Password");
        }


        // Get the user detail for load user and pass to generate token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String generatedToken = this.jwtTokenHelper.generateToken(userDetails);

        // Set the token in the response
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(generatedToken);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);

    }
   // Now let's config this URl in SecurityConfig so that it can be accessed
  /*  private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        this.authenticationManager.authenticate(authenticationToken);
    }*/

    @PostMapping("/register/{user}")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto, @PathVariable String user){

        UserDto registeredUser = this.userService.registerUser(userDto,user);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }
   /* @PostMapping("/register/admin")
    public ResponseEntity<UserDto> registerAdminUser(@RequestBody UserDto userDto){

        UserDto registeredUser = this.userService.registerUser(userDto);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }*/

    // Now let's config this URl in SecurityConfig so that it can be accessed
}
