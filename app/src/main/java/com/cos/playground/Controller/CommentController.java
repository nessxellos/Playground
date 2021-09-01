package com.cos.playground.Controller;

import com.cos.playground.Controller.DTO.CMRespDto;
import com.cos.playground.Controller.DTO.CommentDto;
import com.cos.playground.Controller.DTO.DelCoDto;
import com.cos.playground.Model.Comment;
import com.cos.playground.Model.User;
import com.cos.playground.service.CommentService;

import retrofit2.Call;

public class CommentController {
    private CommentService commentService = CommentService.commentService;

    public Call<CMRespDto<Comment>> writeComment(int id, CommentDto commentDto){
        return commentService.writeComment(id, commentDto);
    }

    public Call<CMRespDto<Comment>> updateByCid(int cid, Comment comment){
        return commentService.updateByCid(cid, comment);
    }

    public Call<CMRespDto<User>> deleteByCid(int cid, DelCoDto delCoDto){
        return commentService.deleteByCid(cid, delCoDto);
    }
}
