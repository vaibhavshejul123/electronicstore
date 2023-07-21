package com.bikkadit.electronic.store.dto;

import com.sun.istack.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;
    @NotNull
    private String name;

    private String email;

    private String password;

    private String about;

    private String gender;

    private String imagename;

}
