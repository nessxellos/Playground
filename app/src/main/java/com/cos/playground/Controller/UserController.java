package com.cos.playground.Controller;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.DTO.LoginDto;
import com.cos.playground.Model.User;
import com.cos.playground.service.UserService;

import retrofit2.Call;

public class UserController {
    private UserService userService = UserService.userService;

    public Call<CMRespDto<User>> join(JoinDto joinDto){return userService.join(joinDto);}

    public Call<CMRespDto<User>> login(LoginDto loginDto){return userService.login(loginDto);}

    public Call<CMRespDto<User>> update(String username, JoinDto joinDto){
        return userService.update(username, joinDto);
    }
}
