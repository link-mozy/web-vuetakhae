// 하단 DB 설정 부분은 Sub PJT II에서 데이터베이스를 구성한 이후에 주석을 해제하여 사용.

package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String email;
    private String password;
    private String nickname;
    private String introduce; // 자기소개
    private String image;     // 이미지 경로
    private Date createDate;
    private String authority; // 사용자 권한
    private String box;       // 냉장고
}
