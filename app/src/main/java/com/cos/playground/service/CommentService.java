package com.cos.playground.service;


import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.CommentDto;
import com.cos.playground.Controller.DTO.DelCoDto;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.config.SessionInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentService {

    @POST("/comment/write/{id}")
    Call<CMRespDto<Comment>> writeComment(@Path("id")int id, @Body CommentDto commentDto);

    @PUT("/comment/update/{cid}")
    Call<CMRespDto<Comment>> updateByCid(@Path("cid")int id, @Body CommentDto commentDto);

    @POST("/comment/delete/{cid}")
    Call<CMRespDto<User>> deleteByCid(@Path("cid")int id, @Body DelCoDto delCoDto);

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new SessionInterceptor()).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    CommentService commentService = retrofit.create(CommentService.class);
}
