package com.web.server.controller;


import com.web.server.dto.*;
import com.web.server.service.BoardService;
import com.web.server.service.JwtService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class BoardRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    BoardService boardService;

    @Autowired
    JwtService jwtService;

    /**
     * 전체 게시글 조회
     *
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 인기순 조회")
    @GetMapping("/boards")
    public ResponseEntity<Map<String, Object>> searchAllBoards(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String email = "";
        try {
            String token = req.getHeader("jwt-auth-token");
            email = jwtService.getEamil(token);
        } catch (Exception e) {
            System.out.println("Ere" + e.getMessage());
        }
        try {
            List<BoardSimpleDto> boards = null;
            System.out.println("EEEE" + email);
            boards = boardService.searchAll(email);

            status = HttpStatus.OK;
            // body json add
            resultMap.put("boards", boards);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @ApiOperation(value = "게시글 제목 or 글쓴이로 검색")
    @PostMapping("/boards/search")
    public ResponseEntity<Map<String, Object>> searchByKeyword(HttpServletRequest req, @RequestBody SearchByKeywordDto searchByKeywordDto, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String email = "";
        try {
            String token = req.getHeader("jwt-auth-token");
            email = jwtService.getEamil(token);
        } catch (Exception e) {

        }

        try {
            List<BoardSimpleDto> boards = null;
            boards = boardService.searchByKeyword(email, searchByKeywordDto);

            status = HttpStatus.OK;
            // body json add
            resultMap.put("boards", boards);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "냉장고를 뷰탁해 페이지에서 재료들로 게시글을 검색")
    @PostMapping("/boards/foodList")
    public ResponseEntity<Map<String, Object>> searchAllBoardsByFood(HttpServletResponse res, @RequestBody final BoardSearchByFoodList foodList) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            List<BoardSimpleDto> boards = null;
            boards = boardService.searchAllByFood(foodList);

            status = HttpStatus.OK;
            // body json add
            resultMap.put("boards", boards);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /**
     * 게시글 디테일
     *
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 번호로 게시글 조회", response = String.class)
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<Map<String, Object>> searchBoard(@PathVariable final String boardId,
                                                           HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            Board board = null;
            // 서비스
            board = boardService.searchByBoardId(Integer.parseInt(boardId));
            boardService.updateViewCnt(Integer.parseInt(boardId));
            board.setViews(board.getViews() + 1);
            status = HttpStatus.OK;
            // body json add
            resultMap.put("board", board);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            logger.info("게시글 디테일 정보 요청 실패 : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @ApiOperation(value = "유저가 게시글을 즐겨찾기 등록/취소")
    @PostMapping("/boards/favorite")
    public ResponseEntity<Map<String, Object>> postFavorite(HttpServletRequest req, @RequestBody final FavoriteRequestBody favoriteRequestBody,
                                                            HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            int result = boardService.postFavorite(email, favoriteRequestBody);
            status = HttpStatus.OK;
            // body json add
            resultMap.put("status", status.value());
            String message = "성공";
            if (result == 1) {
                message = "즐겨찾기 등록에 성공하셨습니다.";
            } else if (result == -1) {
                message = "즐겨찾기 취소에 성공하셨습니다.";
            }
            resultMap.put("message", message);
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /**
     * 게스글 작성
     *
     * @param req
     * @param board
     * @param res
     * @return
     */
    @ApiOperation(value = "게시글 작성", response = String.class)
    @PostMapping("/boards")
    public ResponseEntity<Map<String, Object>> writeBoard(HttpServletRequest req,
                                                          @RequestBody final Board board,
                                                          HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            if (board.getTitle() == null || board.getTitle().length() == 0 || board.getGrade() == null) { // 입력값 이상
                status = HttpStatus.BAD_REQUEST;
                resultMap.put("status", status.value());
                resultMap.put("message", "잘못된 요청값");
            } else { // 서비스 실행
                status = HttpStatus.OK;

                if (boardService.write(email, board)) {
                    resultMap.put("status", status.value());
                    resultMap.put("message", "성공");
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            logger.info("ERROR : {}", e.getMessage());
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "게시글 수정", response = String.class)
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<Map<String, Object>> updateBoard(HttpServletRequest req, @PathVariable int boardId,
                                                           @RequestBody final Board board,
                                                           HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            board.setBoardId(boardId);
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            if (board.getTitle() == null || board.getTitle().length() == 0 || board.getGrade() == null) { // 입력값 이상
                status = HttpStatus.BAD_REQUEST;
                resultMap.put("status", status.value());
                resultMap.put("message", "잘못된 요청값");
            } else { // 서비스 실행
                status = HttpStatus.OK;

                if (boardService.update(email, board)) {
                    resultMap.put("status", status.value());
                    resultMap.put("message", "수정 성공");
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            logger.info("ERROR : {}", e.getMessage());
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "게시글 삭제", response = String.class)
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable int boardId) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            int result = boardService.deleteBoard(boardId);
            if (result == 1) {
                status = HttpStatus.OK;
                resultMap.put("message", "삭제에 성공했습니다.");
            } else {
                status = HttpStatus.NO_CONTENT;
                resultMap.put("message", "삭제할 게시글이 없습니다.");
            }
            resultMap.put("status", status.value());

        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            logger.info("게시글 삭제 ERROR : {}", e.getMessage());
            resultMap.put("status", status.value());
            resultMap.put("message", "삭제 실패");
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @ApiOperation(value = "댓글 작성", response = String.class)
    @RequestMapping(value = "/boards/comments", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> writeComment(HttpServletRequest req, @RequestBody CommentDto comment) {
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();
        String token = req.getHeader("jwt-auth-token");
        try {
            String email = jwtService.getEamil(token);
            comment.setEmail(email);
            boardService.writeComment(comment);
            status = HttpStatus.OK;
            resultMap.put("status", status.value());
            resultMap.put("message", "글 작성이 완료되었습니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
    }

    @ApiOperation(value = "댓글 수정", response = String.class)
    @RequestMapping(value = "/boards/comments/{commentId}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable("commentId") Integer commentId, @RequestBody CommentDto comment) {
        ResponseEntity<Map<String, Object>> entity = null;
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            comment.setCommentId(commentId);
            boardService.updateComment(comment);
            status = HttpStatus.OK;
            resultMap.put("status", status.value());
            resultMap.put("message", "글 수정을 성공하였습니다.");
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
    }


    @ApiOperation(value = "댓글 삭제", response = String.class)
    @RequestMapping(value = "/boards/comments/{commentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("commentId") Integer commentId) {
        ResponseEntity<Map<String, Object>> entity = null;
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();

        try {
            if (boardService.deleteComment(commentId) == 1) {
                status = HttpStatus.OK;
                resultMap.put("status", status.value());
                resultMap.put("message", "삭제 성공하였습니다.");
                return new ResponseEntity<Map<String, Object>>(resultMap, status);
            } else {
                status = HttpStatus.NOT_FOUND;
                resultMap.put("status", status.value());
                resultMap.put("message", "삭제 실패하였습니다.");
            }
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
    }

    @ApiOperation(value = "게시글 번호로 댓글 조회", response = String.class)
    @RequestMapping(value = "/boards/comments", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> selectCommentByBoardId(@RequestBody int boardId) {
        ResponseEntity<Map<String, Object>> entity = null;
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<CommentDto> commentList = null;
            commentList = boardService.selectCommentByBoardId(boardId);

        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }


        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @RequestMapping(value = "/boards/scroll", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> scroll(Board board, @RequestBody ScrollDto scrollDto) {
        ResponseEntity<Map<String, Object>> entity = null;
        HttpStatus status = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            status = HttpStatus.OK;
            List<Board> boardList = null;
//            board.setScrollNumber(scrollDto);
            boardList = boardService.scrollList(scrollDto);
            resultMap.put("board", boardList);
            resultMap.put("message", "리스트 불러오기 성공");
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(resultMap, status);

        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 공공 API (조리식품의 레시피 DB) 레시피 목록 조회
     *
     * @return List<Board>
     */
    @ApiOperation(value = "공공API(조리식품 레시피 DB) 레시피 목록 조회")
    @GetMapping("/boards/foodsafe/recipes/pages/{page}")
    public ResponseEntity<Map<String, Object>> searchAllFoodSafeRecipes(@PathVariable("page") int page) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            List<Board> boards = null;
            boards = boardService.searchAllFoodSafeRecipes(page * 12);
            resultMap.put("success", true);
            resultMap.put("recipes", boards);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.info("ERROR searchFoodSafeRecipesAll() : {}", e.getMessage());
            resultMap.put("success", false);
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 공공 API (조리식품의 레시피 DB) 레시피 데이터 레시피 번호로 조회
     *
     * @return
     */
    @ApiOperation(value = "공공API(조리식품 레시피 DB) 레시피 번호로 조회")
    @GetMapping("/boards/foodsafe/recipes/{rcpSeq}")
    public ResponseEntity<Map<String, Object>> searchFoodSafeRecipesByRecipeSeq(@PathVariable("rcpSeq") int rcpSeq) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            List<FoodSafeRecipeDto> recipes = null;
            recipes = boardService.searchFoodSafeRecipesByRecipeSeq(rcpSeq);
            resultMap.put("success", true);
            resultMap.put("recipes", recipes);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.info("ERROR searchFoodSafeRecipesAll() : {}", e.getMessage());
            resultMap.put("success", false);
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 공공 API (조리식품의 레시피 DB) 제목으로 레시피 조회
     *
     * @param rcpNm
     * @return
     */
    @ApiOperation(value = "공공 API (조리식품의 레시피 DB) 제목으로 레시피 조회")
    @GetMapping("/boards/foodsafe/recipes/title/{rcpNm}")
    public ResponseEntity<Map<String, Object>> searchFoodSafeRecipesByRecipeName(@PathVariable("rcpNm") String rcpNm) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            List<Board> boards = null;
            boards = boardService.searchFoodSafeRecipesByRecipeName(rcpNm);
            resultMap.put("success", true);
            resultMap.put("recipes", boards);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.info("ERROR searchFoodSafeRecipesAll() : {}", e.getMessage());
            resultMap.put("success", false);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }


    /**
     * 공공 API (조리식품의 레시피 DB) 재료로 레시피 조회
     *
     * @param ingredient List<String> eg. ["스파게티", "바나나"]
     * @return
     */
    @ApiOperation(value = "공공 API (조리식품의 레시피 DB) 재료로 레시피 조회")
    @PostMapping("/boards/foodsafe/recipes/ingredient")
    public ResponseEntity<Map<String, Object>> searchFoodSafeRecipesByRecipeParts(@RequestBody Map<String, Object> map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            List<Board> boards = null;
            List<String> ingredients = null;
            ingredients = (List<String>) map.get("ingredient");
            boards = boardService.searchFoodSafeRecipesByRcpPartsDtls(ingredients);
            resultMap.put("success", true);
            resultMap.put("recipes", boards);
            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.info("ERROR searchFoodSafeRecipesAll() : {}", e.getMessage());
            resultMap.put("success", false);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "게시글 최신순으로 조회")
    @GetMapping("/boards/create")
    public ResponseEntity<Map<String, Object>> searchAllBoardsByCreateAt(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        String email = "";
        try {
            String token = req.getHeader("jwt-auth-token");
            email = jwtService.getEamil(token);
        } catch (Exception e) {
            System.out.println("Ere" + e.getMessage());
        }
        try {
            List<BoardSimpleDto> boards = null;
            System.out.println("EEEE" + email);
            boards = boardService.searchAllCreateAt(email);

            status = HttpStatus.OK;
            // body json add
            resultMap.put("boards", boards);
            resultMap.put("status", status.value());
            resultMap.put("message", "성공");
        } catch (RuntimeException | SQLException e) {
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("message", "실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
