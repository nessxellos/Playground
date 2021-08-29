package com.cos.playground.Controller;

import com.cos.playground.Controller.DTO.BoardUpdateDto;
import com.cos.playground.Controller.DTO.BoardWriteDto;
import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Model.CBoard;
import com.cos.playground.service.BoardService;

import java.util.List;

import retrofit2.Call;

public class BoardController {

    private static final String TAG = "BoardCon";
    private BoardService boardService = BoardService.boardService;

    public Call<CMRespDto<CBoard>> findById(int id) { return boardService.findById(id);}

    public Call<CMRespDto<CBoard>> update(int id, BoardUpdateDto boardUpdateDto) {
        return boardService.update(id, boardUpdateDto);
    }

    public Call<CMRespDto<CBoard>> deleteById(int id) { return boardService.deleteById(id);}

    public Call<CMRespDto<CBoard>> write(BoardWriteDto boardWriteDto) {
        return boardService.write(boardWriteDto);
    }

    public Call<CMRespDto<List<CBoard>>> findAll(){
        return boardService.findAll();
    }
}
