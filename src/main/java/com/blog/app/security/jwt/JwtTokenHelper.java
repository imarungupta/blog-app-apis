package com.blog.app.security.jwt;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
// Step 3.Create JWTTokenHelper

// https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac
@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY= 5*60*60; // Token will be expired after this mili second
    private String secret="jwtTokenKeyfdsfsdfdsfsdfdsfdsfdsfsdfdsfdsfdsfdsfdsfdsfsffsdfdsfsdfsdfdsfsdff";

    // Retrieve username from token, when we have token then using this method we can retrieve username
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims= getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieve any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //Retrieve Expiration date from token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }
    // Check if token is expired
    private Boolean isTokenExpired(String token){
        final Date expiration= getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    // Generate token for user
    public String generateToken(UserDetails userDetails){
         Map<String, Object> claims = new HashMap<>();
         return doGenerateToken(claims,userDetails.getUsername());
    }
    // while creating token-
    //1- Define claims of the token, like issuer, Expiration, Subject and ID
    //2- Sign the JWT using the HS512 algorithm and secret key.
    //3- According JWS Compact Serialization (https://tools.ietf.org/html/draft-ietf-josh

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    //Validate token
    public Boolean validateToken(String token,UserDetails userDetails){
        final String username= getUsernameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));
    }
}