package com.blog.app.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private int id;

    @NotEmpty
    @Size(min = 4,message = "Name must not be less than 4 character!")
    private String name;

    @Email(message = "Enter the valid email!")
    //@Pattern()
    private String email;

    @NotEmpty
    @Size(min=3,max = 10,message = "Password must be at least 3 to 10 character!")
    private String password;

    @NotEmpty
    private String about;

}
