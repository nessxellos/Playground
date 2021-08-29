package com.cos.playground.config;

import com.cos.playground.Model.User;

import lombok.Data;

@Data
public class SessionUser {
    public static User user;
    public static String sessionId;
}
