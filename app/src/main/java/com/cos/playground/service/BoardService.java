package com.cos.playground.service;


import com.cos.playground.Controller.DTO.BoardUpdateDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;
import com.cos.playground.config.SessionInterceptor;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BoardService {


    @PUT("/cboard/update/{id}")
    Call<CMRespDto<CBoard>> update(@Path("id") int id, @Body BoardUpdateDto boardUpdateDto);

    @POST("/cboard/delete/{id}")
    Call<CMRespDto<User>> deleteById(@Path("id") int id, @Body User user);

    @Multipart
    @POST("/cboard/write")
    Call<CMRespDto<CBoard>> write(@Part("file") Multipart file, @Part("strBoardDto") String strBoardDto);




    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new SessionInterceptor()).build();

    Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.25.41:9000")
            .build();

    BoardService boardService = retrofit.create(BoardService.class);
}

