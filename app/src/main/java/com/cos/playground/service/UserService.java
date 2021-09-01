package com.cos.playground.service;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.JoinDto;
import com.cos.playground.Controller.DTO.Fav;
import com.cos.playground.Controller.DTO.RemoveDto;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.config.SessionInterceptor;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @PUT("/user/update")
    Call<CMRespDto<User>> update(@Body JoinDto joinDto);

    @POST("/user/remove")
    Call<CMRespDto<User>> deleteByUsername(@Body RemoveDto removeDto);

    @POST("/user/fav")
    Call<CMRespDto<Fav>> likeById(@Body Fav fav);

    @GET("/user/myComment/{userId}")
    Call<CMRespDto<List<Comment>>> getMyComments(@Path("userId")int id);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new SessionInterceptor()).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    UserService userService = retrofit.create(UserService.class);
}
