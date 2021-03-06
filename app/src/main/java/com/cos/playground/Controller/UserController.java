package com.cos.playground.Controller;

import com.airbnb.lottie.L;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.DTO.Fav;
import com.cos.playground.Controller.DTO.LoginDto;
import com.cos.playground.Controller.DTO.RemoveDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.service.AuthService;
import com.cos.playground.service.UserService;

import java.util.List;

import retrofit2.Call;

public class UserController {
    private UserService userService = UserService.userService;
    private AuthService authService = AuthService.authService;

    public Call<CMRespDto<User>> join(JoinDto joinDto){return authService.join(joinDto);}

    public Call<CMRespDto<User>> login(LoginDto loginDto){return authService.login(loginDto);}

    public Call<CMRespDto<User>> update(JoinDto joinDto){
        return userService.update(joinDto);
    }

    public Call<CMRespDto<User>> deleteByUsername(RemoveDto removeDto){
        return userService.deleteByUsername(removeDto);
    }

    public Call<CMRespDto<Fav>> likeById(Fav fav){ return userService.likeById(fav);}

    public Call<CMRespDto<List<Comment>>> getMyComments(int id){return  userService.getMyComments(id);}

    public Call<CMRespDto<List<CBoard>>> favPostById(int userId){return userService.favPostById(userId);}

    public Call<CMRespDto<List<CBoard>>> myPostById(int userId){return userService.myPostById(userId);}
}
