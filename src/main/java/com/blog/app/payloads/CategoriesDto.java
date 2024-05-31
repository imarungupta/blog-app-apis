package com.blog.app.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDto {


    private Integer categoryId;

    @NotEmpty(message = "Category title cannot be empty !")
    @Size(min = 5,message = "Please enter title between 4 to 10 character !")
    private String categoryTitle;

    @NotEmpty(message = "Category Description cannot be empty !")
    @Size(min = 10,message = "Please enter Description between 4 to 10 character !")
    private String categoryDescription;

}
