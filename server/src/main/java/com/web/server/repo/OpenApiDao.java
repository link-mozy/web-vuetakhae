package com.web.server.repo;

import java.util.List;

import com.web.server.dto.Board;
import com.web.server.dto.FoodSafeRecipeDto;

public interface OpenApiDao {
	public List<Board> selectAllFormBoards(int page);
	
	public List<FoodSafeRecipeDto> selectByRecipeSeq(int rcpSeq);
	
	public List<Board> selectByRecipeNameFormBoards(String rcpNm); 

	public List<Board> selectByRecipePartsDtlsFormBoards(List<String> rcpPartsDtls);
}
