package com.blog.app.payloads;

import com.blog.app.entities.Category;
import com.blog.app.entities.Comment;
import com.blog.app.entities.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Integer postId;
    private String postTitle;
    private String content;
    private String image;
    private Date addedDate;
    private UserDto user;
    private CategoriesDto category;

    // Since a post can have many comments
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> commentSet = new HashSet<>();
}
