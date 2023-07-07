package com.bikkadit.electronic.store.dto;

import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Email;
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
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min of 3 chars and max of 10 chars")
    private String password;

    @NotEmpty
    private String about;

    private String gender;

    private String imagename;

}
