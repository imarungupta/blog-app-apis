package com.blog.app.repositories;

import com.blog.app.entities.Category;
import com.blog.app.entities.Post;
import com.blog.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findPostByUser(User user);

    List<Post> findPostByCategory(Category category);

    List<Post> findByPostTitleContaining(String title);

    @Query("select p from Post p where p.postTitle like :key")
    List<Post> searchPostByTitle(@Param("key") String title);
}
