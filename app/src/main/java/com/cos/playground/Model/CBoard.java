package com.cos.playground.Model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class CBoard implements Serializable {
    private int id; // pk
    private String title;
    private String content;
    private Timestamp regdate;
    private String file;
    private int favCount;
    private int viewCount;
    private int commentCount;
    private int userId; // fk
    private String writer;
    private List<Comment> comments;


}
