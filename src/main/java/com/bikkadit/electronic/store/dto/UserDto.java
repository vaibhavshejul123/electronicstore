package com.bikkadit.electronic.store.dto;

import com.bikkadit.electronic.store.util.ImageNameValid;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 4, message = "Username must be minimum of 4 character!!")
    private String name;

    @Email(message = "Email Address is not valid!!")
    @NotBlank(message = "Email Is Required !!")
    private String email;

    @NotBlank(message = "Password Is Required !!")
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars")
    private String password;

    @NotBlank(message ="Write something yourself !!")
    private String about;

    @Size(min= 3,max=6,message ="Invalid gender !!")
    private String gender;

    @ImageNameValid
    private String imagename;

}
