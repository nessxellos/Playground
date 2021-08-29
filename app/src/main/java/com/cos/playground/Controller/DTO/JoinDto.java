package com.cos.playground.Controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JoinDto {

    private String username;
    private String password;
    private String name;
    private String phone;
    private String career;
    private String email;
}
