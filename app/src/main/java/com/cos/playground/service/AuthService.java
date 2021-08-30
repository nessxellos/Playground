package com.cos.playground.service;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.DTO.LoginDto;
import com.cos.playground.Model.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

//권한없이 이용하는 페이지를 담당하는 Service
public interface AuthService {

    @POST("/user/join")
    Call<CMRespDto<User>> join(@Body JoinDto joinDto);

    @POST("/user/login")
    Call<CMRespDto<User>> login(@Body LoginDto loginDto);

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    AuthService authService = retrofit.create(AuthService.class);
}
