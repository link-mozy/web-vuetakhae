package com.web.server.repo;

import com.web.server.dto.CommentDto;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface CommentDao {


    List<CommentDto> selectByBoardId(int boardId) throws SQLException;

    void createCommentByBoardId(CommentDto comment) throws SQLException;

    void updateCommentByBoardId(CommentDto comment) throws SQLException;

    int deleteCommentByBoardId(Integer commentId) throws SQLException;

    List<CommentDto> selectCommentByBoardId(int boardId);
}

