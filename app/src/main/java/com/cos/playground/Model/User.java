package com.cos.playground.Model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    private int id;
//    private String username;
    private String password;
    private String name;
    private String email;
//    private String phone;
//    private String career;
//    private Timestamp date;
}
