package com.cos.playground.service;

import com.cos.playground.Controller.DTO.BoardUpdateDto;
import com.cos.playground.Controller.DTO.BoardWriteDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BoardService {

    @GET("/cBoard/detail/{id}")
    Call<CMRespDto<CBoard>> findById(@Path("id") int id);

    @PUT("/cBoard/update/{id}")
    Call<CMRespDto<CBoard>> update(@Path("id") int id, @Body BoardUpdateDto boardUpdateDto);

    @DELETE("/cBoard/delete/{id}")
    Call<CMRespDto<CBoard>> deleteById(@Path("id") int id);

    @PUT("/cBoard/write")
    Call<CMRespDto<CBoard>> write(@Body BoardWriteDto boardWriteDto);

    @GET("/cBoard")
    Call<CMRespDto<List<CBoard>>> findAll();

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    BoardService boardService = retrofit.create(BoardService.class);
}

