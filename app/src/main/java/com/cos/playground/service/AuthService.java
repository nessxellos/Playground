package com.cos.playground.service;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.DTO.LoginDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//권한없이 이용하는 페이지를 담당하는 Service
public interface AuthService {

    // 로그인, 회원가입
    @POST("/user/join")
    Call<CMRespDto<User>> join(@Body JoinDto joinDto);

    @POST("/user/login")
    Call<CMRespDto<User>> login(@Body LoginDto loginDto);


    // 게시판 조회
    @GET("/cboard/list")
    Call<CMRespDto<List<CBoard>>> findAll();

    @GET("/cboard/detail/{id}")
    Call<CMRespDto<CBoard>> findById(@Path("id") int id);



    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    AuthService authService = retrofit.create(AuthService.class);
}
