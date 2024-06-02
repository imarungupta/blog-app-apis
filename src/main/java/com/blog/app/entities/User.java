package com.blog.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private String password;
    private String about;

    // One user can have many post so let's make it OneToMany
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> post = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns =@JoinColumn(name = "user_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // The above line will create a table with name user_role where user_d and role_id will be present as forgion key

    // The below methods are coming from UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Here first will fetch the role and then provide the authority

        List<SimpleGrantedAuthority> grantedAuthorityList = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return grantedAuthorityList;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        /*return UserDetails.super.isAccountNonExpired();*/
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        /*return UserDetails.super.isAccountNonLocked();*/
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        /*return UserDetails.super.isCredentialsNonExpired();*/
    }

    @Override
    public boolean isEnabled() {
        return true;
        /*return UserDetails.super.isEnabled();*/
    }





}
