package com.web.server.repo;

import com.web.server.dto.CommentDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao{
    private String ns = "com.web.server.repo.CommentDao.";
    @Autowired
    SqlSessionTemplate template;

    @Override
    public List<CommentDto> selectByBoardId(int boardId) throws SQLException {
        return template.selectList(ns + "selectCommentsInBoardByBoardId", boardId);
    }

    @Override
    public void createCommentByBoardId(CommentDto comment) throws SQLException{
        template.insert( ns+ "createCommentsInBoardByBoardId", comment);
    }

    @Override
    public void updateCommentByBoardId(CommentDto comment) throws SQLException{
        template.update(ns+"updateCommentsInBoardByBoardId", comment );
    }

    @Override
    public int deleteCommentByBoardId(Integer commentId) throws SQLException{
        return template.delete(ns+"deleteCommentsInBoardByBoardId", commentId);
    }

    @Override
    public List<CommentDto> selectCommentByBoardId(int boardId) {
        return template.selectList(ns+"selectCommentsByBoardId",boardId);
    }
}
