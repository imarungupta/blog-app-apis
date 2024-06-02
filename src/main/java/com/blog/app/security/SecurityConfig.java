package com.blog.app.security;

import com.blog.app.security.jwt.JWTAuthenticationEntryPoint;
import com.blog.app.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc                                       // For Swagger UI
@EnableGlobalMethodSecurity(prePostEnabled = true)  // This is for making any URI to give permission like ADMIN or Normal User
@Component
@EnableWebSecurity
public class SecurityConfig {
// https://medium.com/@aamir.zaidi5/spring-security-implementation-805520a297d5

   public static final String[] PUBLIC_SWAGGER_URLS= {"/v3/api-docs",
                                                      "/v2/api-docs",
                                                      "/swagger-resources/**",
                                                      "/swagger-ui/**",
                                                      "/webjars/**"};
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Pass the above bean to this DaoAuthenticationProvider to authenticate, it will authenticate credential from DB
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    // This bean will Authenticate user credential during login from form.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


       /* httpSecurity.authorizeHttpRequests(
                (auth) -> {
                    auth.requestMatchers("/api/users/")
                            .permitAll()
                            .anyRequest()
                            .authenticated();

                }).httpBasic(Customizer.withDefaults());*/

        httpSecurity.csrf(csrf->csrf.disable()).authorizeHttpRequests(

                (uriAuth)->{
                        uriAuth.requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.GET).permitAll()
                                .requestMatchers("/v3/api-docs").permitAll()
                                .anyRequest().authenticated();

// OR                           .requestMatchers("/api/auth/**").permitAll()
//                              .requestMatchers(HttpMethod.DELETE).hasRole("ROLE_ADMIN")
//                              .requestMatchers(HttpMethod.POST).hasRole("ROLE_ADMIN")

        }).exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
          .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // The problem with Basic Auth is that for each and every request we need to send username and password.
    // This problem can be solved by JWT authentication. It is a token based authentication and is the best way to secure api
    // JWT is a stateless while httpBasic is stateful, stateless means we dont need to store anything on the server

}
