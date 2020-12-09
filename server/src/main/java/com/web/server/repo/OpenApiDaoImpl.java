package com.web.server.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.server.dto.Board;
import com.web.server.dto.FoodSafeRecipeDto;

@Repository
public class OpenApiDaoImpl implements OpenApiDao{
	
	private String ns = "com.web.server.repo.OpenApiDao.";
	
	@Autowired
	SqlSessionTemplate template;

	@Override
	public List<Board> selectAllFormBoards(int page) {
		List<Board> result = null;
		result = template.selectList(ns + "selectAllFormBoards", page);
		return result;
	}

	@Override
	public List<FoodSafeRecipeDto> selectByRecipeSeq(int rcpSeq) {
		List<FoodSafeRecipeDto> result = null;
		result = template.selectList(ns + "selectByRcpSeq", rcpSeq);
		return result;
	}
	
	@Override
	public List<Board> selectByRecipeNameFormBoards(String rcpNm) {
		List<Board> result = null;
		result = template.selectList(ns + "selectByRcpNmFormBoards", rcpNm);
		return result;
	}

	@Override
	public List<Board> selectByRecipePartsDtlsFormBoards(List<String> rcpPartsDtls) {
		List<Board> result = null;
		Map<String, Object> map = new HashMap<>();
		map.put("rcpPartsDtls", rcpPartsDtls);
		result = template.selectList(ns + "selectByRcpPartsDtlsFormBoards", map);
		return result;
	}

}
