package com.web.server.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardEasy{
    private String email;             // 사용자 번호
    private int boardId;
    private int grade;
    private int pressedBtn;
    private int boardHardCnt;
    private int boardEasyCnt;

}
