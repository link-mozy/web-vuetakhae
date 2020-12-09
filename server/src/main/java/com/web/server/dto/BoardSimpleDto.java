package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSimpleDto {
    @NonNull
    private int     boardId;         // 게시글 번호
    @NonNull
    private String  title;           // 제목
    @NonNull
    private String  nickname;        // 작성자
    private Date    createAt;        // 작성 시간
    private String  thumbnailImage;  // 이미지
    private Double  cookingTime;     // 소요시간
    private int     grade;           // 난이도
    private int favorite;            // 즐겨찾기 여부 1->즐겨찾기 , 0-> 즐겨찾기 안한 것
    private int commentCount;       // 댓글 갯수
//    private int tagCount;           // 테그 (나중에 작업)
}
