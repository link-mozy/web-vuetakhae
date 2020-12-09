package com.web.server.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private int boardId;
    private int userId;
    private int views;
    private int easyCount;
    private int difficultyCount;
    private String title;
    private String writerImage;
    private String nickname;
    private String email;
    private String ingredient;
    private String content;
    private Date createAt;
    private Date updateAt;


    private Integer grade;
    private Double cookingTime;
    private String thumbnailImage;

    private List<Steps> steps;
    private List<CommentDto> comments;
    private List<String> tags;

//    private User user;
}
