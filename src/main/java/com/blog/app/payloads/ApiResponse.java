package com.blog.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

    private String message;
    private boolean success;


    /*public ApiResponse(String userDeletedSuccessfully, boolean b) {
        this.message=userDeletedSuccessfully;
        this.success=b;
    }*/
}
