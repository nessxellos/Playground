package com.cos.playground.Model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class Comment implements Serializable {

    private int cid;
    private int userId;
    private int boardId;
    private String username;
    private String content;
    private Timestamp regdate;
    private int upId; // 상위 댓글의 id
}
