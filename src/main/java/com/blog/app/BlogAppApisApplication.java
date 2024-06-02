package com.blog.app;

import com.blog.app.entities.Role;
import com.blog.app.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;


	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passwordEncoder.encode("123"));
		Role adminUser = new Role();

		adminUser.setId(501);
		adminUser.setName("ADMIN_USER");

		Role normalUser= new Role();

		normalUser.setId(502);
		normalUser.setName("NORMAL_USER");

		List<Role> roleList = List.of(adminUser, normalUser);
		roleList.forEach(role -> System.out.println(role));
		this.roleRepository.saveAll(roleList);

	}
}
