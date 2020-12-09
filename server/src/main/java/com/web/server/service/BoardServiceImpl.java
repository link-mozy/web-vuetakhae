package com.web.server.service;

import com.web.server.dto.*;
import com.web.server.repo.BoardDao;
import com.web.server.repo.CommentDao;
import com.web.server.repo.OpenApiDao;
import com.web.server.repo.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDao boardDao;

    @Autowired
    UserinfoDao userinfoDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    OpenApiDao openapiDao;

    @Override
    public List<BoardSimpleDto> searchAll(String email) throws SQLException {
        return boardDao.searchAll(email);
    }

    @Override
    public List<BoardSimpleDto> searchAllCreateAt(String email) throws SQLException {
        return boardDao.searchAllCreateAt(email);
    }

    @Override
    public Board searchByBoardId(int boardId) throws SQLException {
        Board board = null;
        board = boardDao.searchByBoardId(boardId);              // 1. 게시글 조회
        board.setSteps(boardDao.searchStepsInBoard(boardId));   // 2. 단계 조회
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < board.getSteps().size(); i++) {
            System.out.println(board.getSteps().get(i).getStepId());
            int selectedStepId = board.getSteps().get(i).getStepId();
            List<Tags> tagList = boardDao.getTagList(selectedStepId);
            StringBuilder sb = new StringBuilder();
            if(tagList.size()==0){
                tempList.add("");
            }else{

            for (Tags tag : tagList) {
                System.out.println(tag.toString());
                sb.append(tag.getTagName() + ",");
            }
            tempList.add(sb.substring(0, sb.length() - 1));
            }
        }
        board.setTags(tempList);

        board.setComments(commentDao.selectByBoardId(boardId)); // 3. 댓글 조회
        return board;
    }

    @Override
    @Transactional
    public boolean write(String email, Board board) throws SQLException {
        User user = userinfoDao.selectByIsEmail(email);
        board.setUserId(user.getUserId());
        boardDao.insertBoard(board);              // 1. 게시글 로우 생성
        System.out.println("ssss  " + board.getBoardId());
        int resultBoardId = board.getBoardId();

        List<String> listTag = board.getTags();
        if (board.getSteps() != null && board.getSteps().size() > 0) { // 2. 게시글에 저장되는 단계 저장
            for (int i = 0; i < board.getSteps().size(); i++) {
                board.getSteps().get(i).setBoardId(resultBoardId);
                boardDao.insertStep(board.getSteps().get(i));
                System.out.println(board.getSteps().get(i).getStepId());
                String[] arrTags = listTag.get(i).split(",");
                if (arrTags.length == 0) {
                    Tags temp = new Tags(-1, "");
                    temp.setTagName("");
                    boardDao.insertTag(temp);
                } else {

                    for (String tag : arrTags) {

                        System.out.println(tag);
                        Tags temp = new Tags(-1, "");
                        temp.setTagName(tag);
                        Tags selectedTag = new Tags(-1, "");
//                    selectedTag = boardDao.selectTagExist(temp);
//                    if (selectedTag == null) {
                        System.out.println("null!!");
                        boardDao.insertTag(temp);
                        System.out.println(temp.getTagId());
                        StepTags stepTags = new StepTags(-1, -1);
                        stepTags.setTagId(temp.getTagId());
                        stepTags.setStepId(board.getSteps().get(i).getStepId());
                        boardDao.insertStepTags(stepTags);
//                    } else {
//                        System.out.println("id : " + selectedTag.getTagId());
//                        System.out.println("name : " + selectedTag.getTagName());
//                        StepTags stepTags = new StepTags(-1, -1);
//                        stepTags.setTagId(selectedTag.getTagId());
//                        stepTags.setStepId(board.getSteps().get(i).getStepId());
//                        boardDao.insertStepTags(stepTags);
//                    }
                    }
                }

            }
        }
        return resultBoardId != 0 ? true : false;
    }

    @Override
    public int deleteBoard(int boardId) throws SQLException {
        return boardDao.deleteBoard(boardId);
    }

    @Override
    public List<BoardSimpleDto> searchAllByFood(BoardSearchByFoodList foodList) throws SQLException {
        return boardDao.searchAllByFood(foodList);
    }

    @Override
    public void writeComment(CommentDto comment) throws SQLException {
        commentDao.createCommentByBoardId(comment);
    }

    @Override
    public void updateComment(CommentDto comment) throws SQLException {
        commentDao.updateCommentByBoardId(comment);
    }

    @Override
    public int deleteComment(Integer commentId) throws SQLException {
        return commentDao.deleteCommentByBoardId(commentId);
    }

    @Override
    public List<CommentDto> selectCommentByBoardId(int boardId) {
        return commentDao.selectCommentByBoardId(boardId);
    }

    @Override
    public void updateViewCnt(Integer BoardId) throws SQLException {
        boardDao.updateViewCnt(BoardId);
    }


    @Override
    public List<Board> scrollList(ScrollDto scrollDto) throws SQLException {
        return boardDao.scrollList(scrollDto);
    }

    @Override
    public List<Board> searchAllFoodSafeRecipes(int page) throws SQLException {
        List<Board> boards = null;
        boards = openapiDao.selectAllFormBoards(page);
        return boards;
    }

    @Override
    public List<Board> searchFoodSafeRecipesByRecipeName(String rcpNm) throws SQLException {
        return openapiDao.selectByRecipeNameFormBoards(rcpNm);
    }

    @Override
    public List<FoodSafeRecipeDto> searchFoodSafeRecipesByRecipeSeq(int rcpSeq) throws SQLException {
        List<FoodSafeRecipeDto> recipes = null;
        recipes = openapiDao.selectByRecipeSeq(rcpSeq);
        return recipes;
    }

    @Override
    public List<Board> searchFoodSafeRecipesByRcpPartsDtls(List<String> rcpPartsDtls) throws SQLException {
        List<Board> boards = null;
        boards = openapiDao.selectByRecipePartsDtlsFormBoards(rcpPartsDtls);
        return boards;
    }

    @Override
    @Transactional
    public boolean update(String email, Board board) throws SQLException {
        User user = userinfoDao.selectByIsEmail(email);
        board.setUserId(user.getUserId());

        boardDao.updateDeleteTags(board.getBoardId()); // 게시글 수정할 때 기존에 있던 태그 다 삭제
        boardDao.updateDeleteSteps(board.getBoardId()); // 게시글 수정할 때  기존에 있던 단계 다 삭제

        boardDao.updateBoard(board);              // 1. 게시글 수정
        int resultBoardId = board.getBoardId();

        List<String> listTag = board.getTags();
        if (board.getSteps() != null && board.getSteps().size() > 0) { // 2. 게시글에 저장되는 단계 저장
            for (int i = 0; i < board.getSteps().size(); i++) {
                board.getSteps().get(i).setBoardId(resultBoardId);
                System.out.println("ㅁㄴㅇ"+board.getSteps().get(i));
                boardDao.insertStep(board.getSteps().get(i));
                String[] arrTags = listTag.get(i).split(",");
                for (String tag : arrTags) {
                    if (tag == "") {
                        continue;
                    }
                    System.out.println(tag);
                    Tags temp = new Tags(-1, "");
                    temp.setTagName(tag);
                    Tags selectedTag = new Tags(-1, "");
//                    selectedTag = boardDao.selectTagExist(temp);
//                    if (selectedTag == null) {
                        System.out.println("null!!");
                        boardDao.insertTag(temp);
                        System.out.println(temp.getTagId());
                        StepTags stepTags = new StepTags(-1, -1);
                        stepTags.setTagId(temp.getTagId());
                        stepTags.setStepId(board.getSteps().get(i).getStepId());
                        boardDao.insertStepTags(stepTags);
//                    } else {
//                        System.out.println("id : " + selectedTag.getTagId());
//                        System.out.println("name : " + selectedTag.getTagName());
//                        StepTags stepTags = new StepTags(-1, -1);
//                        stepTags.setTagId(selectedTag.getTagId());
//                        stepTags.setStepId(board.getSteps().get(i).getStepId());
//                        boardDao.insertStepTags(stepTags);
//                    }
                }

            }
        }
        return resultBoardId != 0 ? true : false;
    }

    @Override
    public int postFavorite(String email, FavoriteRequestBody boardId) throws SQLException {
        int cnt = boardDao.isExistFavorite(email, boardId);
        System.out.println("cnt : " + cnt);
        int result = 0;
        if (cnt == 1) {
            result = boardDao.deleteFavorite(email, boardId);
            if (result == 1) {
                result = -1;
            }
        } else {
            result = boardDao.insertFavorite(email, boardId);
            if (result == 1) {
                result = 1;
            }
        }
        return result;
    }

    @Override
    public List<BoardSimpleDto> searchByKeyword(String email, SearchByKeywordDto searchByKeywordDto) throws SQLException {
        return boardDao.searchByKeyword(email, searchByKeywordDto);
    }
}
