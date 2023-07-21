package com.bikkadit.electronic.store.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="user_name",length = 30,nullable = false)
    private String name;

    @Column(name="user_email",length = 30,nullable = false,unique = true)
    private String email;

    @Column(name="user_password",length = 30,nullable = false)
    private String password;

    @Column(name="user_gender",length = 30,nullable = false)
    private String gender;

    @Column(name="user_about",length = 30,nullable = false)
    private String about;

    @Column(name="user_imageName")
    private String imageName;



}
