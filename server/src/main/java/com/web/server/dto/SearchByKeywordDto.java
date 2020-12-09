package com.web.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchByKeywordDto {
    private String keyword;
    private int type; // 1 -> 제목으로 검색, 2 -> 글쓴이로 검색
}
