package com.cos.playground.Controller.DTO;

import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardWriteDto {

    private User user;
    private CBoard cBoard;
    private int code;
    private String msg;
}
