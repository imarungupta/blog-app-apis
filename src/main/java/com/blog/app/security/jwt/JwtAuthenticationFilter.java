package com.blog.app.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*4. JwtAuthenticationFilter  extends OnceRequestFilter
Get jwt token from request
Validate token
Get user from token
Load user associated with token
Set spring security Context*/

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    // The below method is called on each request call i.e. on each api hit this method will be called
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1-  First get token : Get jwt token from request
        String getTokenFromHeader = request.getHeader("Authorization");
        // Bearer 234568411
        System.out.println(getTokenFromHeader);
        // Get username from this token
        String actualTokenAfterRemoveBearer = null;
        String usernameFromToken = null;

        if (getTokenFromHeader != null && getTokenFromHeader.startsWith("Bearer")) {
            // actualTokenAfterRemoveBearerPlusOneSpace
            actualTokenAfterRemoveBearer = getTokenFromHeader.substring(7);
            try {
                usernameFromToken = this.jwtTokenHelper.getUsernameFromToken(actualTokenAfterRemoveBearer);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get Jwt token");
            } catch (ExpiredJwtException e) {
                System.out.println("Jwt token has been expired");
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT Token");
            }
        } else {
            System.out.println("JWT Token is not begin with Bearer");
        }
        // Once we get the token then step 2 is to validate token

        if (usernameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Step- 3 Get user from token
            // user from token is not null and user could not be authenticated yet then let's authenticate user
            // Load user associated with token
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(usernameFromToken);
            System.out.println(STR."userDetails::\{userDetails}");
            // Set spring security Context
            if (this.jwtTokenHelper.validateToken(actualTokenAfterRemoveBearer, userDetails)) {
                // user is validated and now we will authenticate token here
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else {
                System.out.println(" Invalid JWT Token");
            }
        }else{
            System.out.println("User is name is null or Context is not null");
        }
        // Apply filterChain now
        filterChain.doFilter(request,response);
    }
}
