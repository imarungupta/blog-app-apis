package com.blog.app.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
// Step 2. Create JWT authenticationEntryPoint implements AuthenticationEntryPoint

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // The below method first will check whether token is there in the request header or not, if not present then will send error
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Error will be sent using HttpServlet response to the user or client
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!");
    }
}
