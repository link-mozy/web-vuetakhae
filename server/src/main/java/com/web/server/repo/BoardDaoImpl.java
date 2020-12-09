package com.web.server.repo;

import com.web.server.dto.*;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


@Repository
public class BoardDaoImpl implements BoardDao {
    private String ns = "com.web.server.repo.BoardDao.";

    static Logger logger = LoggerFactory.getLogger(BoardDaoImpl.class);

    @Autowired
    SqlSessionTemplate template;

    @Override
    public List<BoardSimpleDto> searchAll(String email) throws SQLException {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("keyword",null);
        map.put("type",null);
        return template.selectList(ns + "selectAll", map);
    }

    @Override
    public List<BoardSimpleDto> searchAllCreateAt(String email) throws SQLException {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("keyword",null);
        map.put("type",null);
        return template.selectList(ns + "selectAllByCreateAt", map);
    }

    @Override
    public Board searchByBoardId(int boardId) throws SQLException {
        return template.selectOne(ns + "selectOneBoardByBoardId", boardId);
    }

    @Override
    public List<Steps> searchStepsInBoard(int boardId) throws SQLException {
        return template.selectList(ns + "selectStepsInBoardByBoardId", boardId);
    }

    @Override
    public List<Board> selectByUserId(int userId) throws SQLException {
        return template.selectList(ns + "selectBoardsByUserId", userId);
    }

    @Override
    public List<Board> selectInterestBoardsByUserId(int userId) throws SQLException {
        return template.selectList(ns + "selectInterestBoardsByUserId", userId);
    }

    @Override
    public int insertBoard(Board board) throws SQLException {
        return template.insert(ns + "insertBoard", board);
    }

    @Override
    public void updateBoard(Board board) throws SQLException {
        template.update(ns+"updateBoard",board);
    }

    @Override
    public int insertStep(Steps step) throws SQLException {
        return template.insert(ns + "insertStep", step);
    }

    @Override
    public int insertTag(Tags tagName) throws SQLException {
        return template.insert(ns + "insertTag", tagName);
    }

    @Override
    public Tags selectTagExist(Tags tag) throws SQLException {
        return template.selectOne(ns + "selectTagExist", tag);
    }

    @Override
    public int insertStepTags(StepTags stepTags) throws SQLException {
        return template.insert(ns + "insertStepTags", stepTags);
    }

    @Override
    public List<Tags> getTagList(int selectedStepId) throws SQLException {
        return template.selectList(ns + "getTagList", selectedStepId);
    }

    @Override
    public int deleteBoard(int boardId) throws SQLException {
        return template.delete(ns + "deleteBoard", boardId);
    }

    @Override
    public List<BoardSimpleDto> searchAllByFood(BoardSearchByFoodList foodList) throws SQLException {
        HashMap map = new HashMap();
        map.put("foodList", foodList.getFoodList());
        return template.selectList(ns + "searchAllByFood", map);
    }

    @Override
    public void updateViewCnt(Integer boardId) throws SQLException {
        template.update(ns + "addViews", boardId);
    }

    @Override
    public int isExistFavorite(String email, FavoriteRequestBody favoriteRequestBody) {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("boardId", favoriteRequestBody.getBoardId());
        return template.selectOne(ns + "isExistFavorite", map);
    }

    @Override
    public List<BoardSimpleDto> searchByKeyword(String email, SearchByKeywordDto searchByKeywordDto) throws SQLException {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("keyword",searchByKeywordDto.getKeyword());
        map.put("type",searchByKeywordDto.getType());
        return template.selectList(ns + "selectAll", map);
    }

    @Override
    public int deleteFavorite(String email, FavoriteRequestBody favoriteRequestBody) {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("boardId", favoriteRequestBody.getBoardId());
        return template.delete(ns + "deleteFavorite", map);
    }

    @Override
    public int insertFavorite(String email, FavoriteRequestBody favoriteRequestBody) {
        HashMap map = new HashMap();
        map.put("email", email);
        map.put("boardId", favoriteRequestBody.getBoardId());
        return template.insert(ns + "insertFavorite", map);
    }

    @Override
    public List<Board> scrollList(ScrollDto scrollDto) throws SQLException{
        return template.selectList(ns+"infiniteScroll",scrollDto);
    }

    @Override
    public void updateDeleteTags(int boardId) throws SQLException {
        template.delete(ns + "updateDeleteTags", boardId);

    }

    @Override
    public void updateDeleteSteps(int boardId) throws SQLException {
        template.delete(ns + "updateDeleteSteps", boardId);

    }
}
