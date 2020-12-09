package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int commentId;          // 댓글 번호
    private int userId;             // 사용자 번호
    private int boardId;            // 게시글 번호
    private Date createAt;          // 작성 시간
    private String commentContent;  // 내용
    private String userImage;       // 댓글 쓴 사람의 이미지

    private String nickname;        // 닉네임
    private String email;
}