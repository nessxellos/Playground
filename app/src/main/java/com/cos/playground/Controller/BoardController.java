package com.cos.playground.Controller;

import com.cos.playground.Controller.DTO.BoardUpdateDto;
import com.cos.playground.Controller.DTO.BoardWriteDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.DetailDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.Model.User;
import com.cos.playground.service.AuthService;
import com.cos.playground.service.BoardService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Multipart;

public class BoardController {

    private static final String TAG = "BoardCon";
    private BoardService boardService = BoardService.boardService;
    private AuthService authService = AuthService.authService;

    public Call<CMRespDto<CBoard>> findById(int id, DetailDto detailDto) {
        return authService.findById(id, detailDto);
    }

    public Call<CMRespDto<CBoard>> update(int id, BoardUpdateDto boardUpdateDto) {
        return boardService.update(id, boardUpdateDto);
    }

    public Call<CMRespDto<User>> deleteById(int id, User user) { return boardService.deleteById(id, user);}

    public Call<CMRespDto<CBoard>> write(Multipart file, String strBoardDto) {
        return boardService.write(file,strBoardDto);
    }

    public Call<CMRespDto<List<CBoard>>> findAll(){
        return authService.findAll();
    }
}
