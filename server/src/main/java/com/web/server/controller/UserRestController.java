package com.web.server.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.server.dto.FollowDto;
import com.web.server.dto.UserProfileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.server.dto.User;
import com.web.server.service.JwtService;
import com.web.server.service.UserinfoService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private static final boolean FOLLOWER = true;
    private static final boolean FOLLOWING = false;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserinfoService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // simpleMailMessage.setFrom("example@naver.com"); // 다른 메일은 적어줘야한다.
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("이메일 인증");
        simpleMailMessage.setText("가입을 축하드립니다.");
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 로그인 성공 : 202
     * 로그인 실패 : 401
     *
     * @param user
     * @param res
     */
    @ApiOperation(value = "로그인", response = String.class)
    @PostMapping("/users/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody final User user, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            logger.debug("User 로그인");
            status = HttpStatus.ACCEPTED; // status code : 202

            // 로그인 정보 확인
            User loginUser = userService.login(user.getEmail(), user.getPassword());
            // 로그인 성공했다면 토큰 생성
            String token = jwtService.create(loginUser);
            // 토큰 정보는 request header & body 에 보낸다.
            // header set
            res.setHeader("jwt-auth-token", token); // header에 "jwt-auth-token" 추가
            // body json add
            resultMap.put("accessToken", token); // body에 "auth_token" 추가
            resultMap.put("status", status.value());
            resultMap.put("success", true);
            resultMap.put("message", "로그인 성공");

            logger.info("로그인 성공: " + loginUser.toString());
            // return new ResponseEntity<User>(userService.login(email, password), HttpStatus.NO_CONTENT);
            // session.setAttribute("user", email);
            // return new ResponseEntity<User>(userService.login(email, password), HttpStatus.OK);

        } catch (RuntimeException e) {
            logger.info("로그인 실패");
            status = HttpStatus.UNAUTHORIZED; // status code : 401
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("success", false);
            resultMap.put("message", "로그인 실패");
            logger.info("로그인 에러: {}", e.getMessage());
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
        // return new ResponseEntity<User>(userService.login(user.getId(),
        // user.getPw()), HttpStatus.OK);
    }

    /**
     * 회원가입 성공 : 201
     * 이메일 또는 닉네임 중복 : 409
     * 회원가입 실패 : 403
     *
     * @param user
     */
    @ApiOperation(value = "회원가입", response = String.class)
    @PostMapping("/users/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody final User user) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        // sub1 필수 조건. 회원가입시 이메일 인증

        try {
            logger.debug("User 회원가입");
            String email = user.getEmail();
            String nickname = user.getNickname();

            // 이메일, 닉네임 중복처리 필수
            if (userService.checkAccount(email, nickname)) {
                status = HttpStatus.CREATED; // status code : 201
                // 회원가입 service
                userService.insertUser(user);
                // body json add
                resultMap.put("status", status.value());
                resultMap.put("success", true);
                resultMap.put("message", "회원가입 성공");
                logger.info("회원가입 성공 : {}", email);
                // 이메일 확인 메일 전송
                sendMail(email);
            } else {
                status = HttpStatus.CONFLICT; // status code : 409
                // body json add
                resultMap.put("status", status.value());
                resultMap.put("success", false);
                resultMap.put("message", "이메일 또는 닉네임 중복");
            }

        } catch (RuntimeException | SQLException e) {
            logger.info("회원가입 실패", e.getMessage());
            status = HttpStatus.FORBIDDEN; // status code : 403
            // body json add
            resultMap.put("status", status.value());
            resultMap.put("success", false);
            resultMap.put("message", "회원가입 실패");
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 개인 정보 요청
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @param res
     * @return
     */
    @ApiOperation(value = "개인 정보 조회")
    @GetMapping("/users/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(final HttpServletRequest req,
                                                           HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            // 서비스 실행
            User user = userService.searchByEmail(email);
            status = HttpStatus.OK;
            // body json add
            resultMap.put("success", true);
            resultMap.put("email", user.getEmail());
            resultMap.put("nickname", user.getNickname());
            // log
            logger.info("개인 정보 조회 요청 성공");
        } catch (RuntimeException e) {
            logger.info("개인 정보 조회 요청 실패");
            status = HttpStatus.BAD_REQUEST; // status code : 400
            // body json add
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 개인 정보 변경
     * 성공 : 202
     * 실패 : 400
     *
     * @param req
     * @param user
     * @return
     */
    @ApiOperation(value = "개인 정보 수정")
    @PutMapping("/users/info")
    public ResponseEntity<Map<String, Object>> setUserInfo(final HttpServletRequest req,
                                                           @RequestBody final User user) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            // 서비스 실행
            user.setEmail(email);
            int result = userService.modify(user);
            if(result == 1) {
                status = HttpStatus.ACCEPTED; // status code : 202
                // body json add
                resultMap.put("success", true);
                // log
                logger.info("개인 정보 조회 수정 성공");
            } else {
                throw new RuntimeException("개인 정보 수정 쿼리가 비정상 실행");
            }
        } catch (RuntimeException | SQLException e) {
            logger.info("개인 정보 조회 수정 실패");
            logger.info("test error getMessage : {}", e.getMessage());
            logger.info("test email : {}", jwtService.getEamil(req.getHeader("jwt-auth-token")).toString());
            logger.info("test user : {}", user.toString());
            status = HttpStatus.BAD_REQUEST; // status code : 400
            // body json add
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 닉네임 중복 검사
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "닉네임 중복 검사")
    @PostMapping("/users/info/nickname")
    public ResponseEntity<Map<String, Object>> getUserInfoByNickname(@RequestBody final User user) {
        String nickname = user.getNickname();
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            // 서비스 실행
            if(userService.checkAccount(nickname)) {
                // nickname 중복 X
                status = HttpStatus.OK; // status code : 200
                // body json add
                resultMap.put("success", true);
            } else {
                // nickname 중복 O
                status = HttpStatus.CONFLICT; // status code : 409
            }
        } catch (RuntimeException | SQLException e) {
            logger.info("ERROR.getUserInfoByNickname (닉네임 중복 검사) : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST; // status code : 400
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 사용자 삭제
     * 성공 : 202
     * 실패 : 406, 400
     * 
     * @param req
     * @return
     */
    @ApiOperation(value = "사용자 삭제")
    @DeleteMapping("/users")
    public ResponseEntity<Map<String, Object>> deleteUser(final HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            // 서비스 실행
            if(userService.deleteUser(email) == 1) {
                // 서비스 성공
                status = HttpStatus.ACCEPTED; // status code : 202
                // body json add
                resultMap.put("success", true);
                logger.info("user : {} delete success", email);
            } else {
                // 서비스 실패
                status = HttpStatus.NOT_ACCEPTABLE; // status code : 406
            }
        }
        catch (RuntimeException | SQLException e) {
            // 에러
            logger.info("ERROR.deleteUser (유저 삭제) : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST; // status code : 400
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 회원 마이페이지 조회
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "회원 마이페이지 조회")
    @GetMapping("/users/mypage")
    public ResponseEntity<Map<String, Object>> getUserProfile(final HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        UserProfileDto userProfileDto = null;
        try {
            String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
            userProfileDto = userService.searchUserProfileByEmail(email);
            status = HttpStatus.OK;
            // body json add
            resultMap.put("success", true);
            resultMap.put("mypage", userProfileDto);
            logger.info("회원 마이페이지 조회 성공 : {}", userProfileDto.toString());
        } catch (RuntimeException | SQLException e) {
            logger.info("회원 마이페이지 조회 실패 에러 메세지 : {}", e.getMessage());
            logger.info("회원 마이페이지 조회 실패 userProfile : {}", userProfileDto.toString());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    
    /**
     * 회원 냉장고 식자재 목록 조회
     * 
     * @param req
     * @return
     */
    @ApiOperation(value = "회원 냉장고 식자재 목록 조회")
    @GetMapping("/users/mypage/box")
    public ResponseEntity<Map<String, Object>> getUserProfileBox(final HttpServletRequest req) {
    	Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        UserProfileDto userProfileDto = null;
        String[] boxs = null;
        try {
            String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
            userProfileDto = userService.searchUserProfileByEmail(email);
            status = HttpStatus.OK;
            // body json add
            resultMap.put("success", true);
            if(!userProfileDto.getBox().isEmpty()) {
            	String boxStr = userProfileDto.getBox();
            	boxs = boxStr.split(",");
            	resultMap.put("box", boxs);
            } else {
            	resultMap.put("box", null);
            }
        } catch (RuntimeException | SQLException e) {
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    
    /**
    * 다른 유저 마페이지 조회
    * 성공 : 200
    * 실패 : 400
    *
    * @param req
    * @return
    * @throws Exception
    */
    @ApiOperation(value = "다른 유저 마이페이지 조회")
    @GetMapping("/users/otherpage/{nickname}")
    public ResponseEntity<Map<String, Object>> getOtherProfile(final HttpServletRequest req,
    															@PathVariable final String nickname) {
    	Map<String, Object> resultMap = new HashMap<>();
    	HttpStatus status = null;
    	UserProfileDto userProfileDto = null;
    	try {
    		String email = userService.searchByNickName(nickname).getEmail();
    		userProfileDto = userService.searchUserProfileByEmail(email);
    		status = HttpStatus.OK;
    		// body json add
    		resultMap.put("success", true);
    		resultMap.put("mypage", userProfileDto);
    		logger.info("회원 마이페이지 조회 성공 : {}", userProfileDto.toString());
    	} catch (RuntimeException | SQLException e) {
    		logger.info("회원 마이페이지 조회 실패 에러 메세지 : {}", e.getMessage());
    		logger.info("회원 마이페이지 조회 실패 userProfile : {}", userProfileDto.toString());
    		status = HttpStatus.BAD_REQUEST;
    		resultMap.put("success", false);
    	}
    	return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    


    /**
     * 회원 마이페이지 수정
     * 자기소개 수정 또는 식자재 수정
     * 성공 : 202
     * 실패 : 400
     *
     * @param req
     * @param user
     * @param columnName
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "회원 마이페이지 수정")
    @PutMapping("/users/mypage/{columnName}")
    public ResponseEntity<Map<String, Object>> setUserProfile(final HttpServletRequest req,
                                                              @RequestBody User user,
                                                              @PathVariable final String columnName) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            if(columnName.equals("introduce") || columnName.equals("box") || columnName.contentEquals("image")) {
                user.setEmail(jwtService.getEamil(req.getHeader("jwt-auth-token")));
                userService.modify(user);
                status = HttpStatus.ACCEPTED; // status code : 202
                // body json add
                resultMap.put("success", true);
            } else {
                resultMap.put("success", false);
                throw new RuntimeException("없는 속성 수정 요청");
            }
        } catch (RuntimeException | SQLException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 팔로우/팔로잉 요청
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @param map
     * @return
     */
    @ApiOperation(value = "팔로우/팔로잉 요청")
    @PostMapping("/users/follow")
    public ResponseEntity<Map<String, Object>> requestFollow(final HttpServletRequest req,
                                                             @RequestBody Map<String, String > map) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            if(map.containsKey("follow") || map.containsKey("other")){
                String followValue = map.get("follow");
                String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
                int userId = userService.searchByEmail(email).getUserId();
                int otherUserId = userService.searchByNickName(map.get("other")).getUserId();
                FollowDto follow = new FollowDto();

                if(followValue.equals("follower")) {
                    follow.setFollower(userId);                 // 팔로워에 유저   userId 추가
                    follow.setFollowee(otherUserId);            // 팔로이에 상대방 userId 추가
                } else if (followValue.equals("following")) {
                    follow.setFollower(otherUserId);            // 팔로원에 상대방 userId 추가
                    follow.setFollowee(userId);                 // 팔로이에 유저   userId 추가
                }
                userService.insertFollow(follow);
                status = HttpStatus.OK;                         // status code : 200
                // body json add
                resultMap.put("success", true);
            } else {
                // body json add
                resultMap.put("success", false);
                throw new RuntimeException("follow 값 확인 필요.");
            }
        } catch (RuntimeException | SQLException e) {
            logger.info("ERROR message: {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;                    // status code : 400
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 팔로우 여부 확인
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @param nickname
     * @return
     */
    @ApiOperation(value = "팔로우 여부 확인")
    @GetMapping("/users/follow/other/{nickname}")
    public ResponseEntity<Map<String, Object>> getFollowCheck(final HttpServletRequest req,
                                                              @PathVariable final String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
            status = HttpStatus.OK;
            resultMap.put("success", true);
            if(userService.checkFollow(email, nickname)) {
                resultMap.put("follow", "no");
            } else {
                resultMap.put("follow", "yes");
            }
        } catch (RuntimeException | SQLException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 팔로우 취소
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @param nickname
     * @return
     */
    @ApiOperation(value = "팔로우 취소")
    @DeleteMapping("/users/follow/other/{nickname}")
    public ResponseEntity<Map<String, Object>> deleteFollow(final HttpServletRequest req,
                                                            @PathVariable final String nickname) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
            userService.deleteFollow(email, nickname);
            status = HttpStatus.OK;
            resultMap.put("success", true);
        } catch (RuntimeException | SQLException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 팔로잉/팔로우 명단 조회
     * 성공 : 200
     * 실패 : 400
     *
     * @param req
     * @param follow
     * @return
     */
    @GetMapping("/users/follow/list/{follow}")
    public ResponseEntity<Map<String, Object>> getFollowList(final HttpServletRequest req,
                                                            @PathVariable final String follow) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {

            if(follow.equals("follower") || follow.equals("followee")) {
                status = HttpStatus.OK;
                String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
                Map<String, String> followMap = new HashMap<>();
                List<Map<String, Object>> followList = null;

                if(follow.equals("follower")) {
                    followMap.put("follower", email);
                } else {
                    followMap.put("followee", email);
                }
                // 서비스 실행
                followList = userService.searchFollowList(followMap);
                // body json add
                resultMap.put("users", followList);
                resultMap.put("success", true);
            }
        } catch (RuntimeException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    /**
     * 다른 사용자의 팔로우/팔로잉 명단 조회
     * 
     * @param map
     * @param follow
     * @return
     */
    @PostMapping("/users/follow/list/{follow}")
    public ResponseEntity<Map<String, Object>> getFollowOtherList(@RequestBody final Map<String, String> map,
                                                                  @PathVariable final String follow) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {

            if(follow.equals("follower") || follow.equals("followee")) {
                status = HttpStatus.OK;
                String email = userService.searchByNickName(map.get("other")).getEmail();
                Map<String, String> followMap = new HashMap<>();
                List<Map<String, Object>> followList = null;

                if(follow.equals("follower")) {
                    followMap.put("follower", email);
                } else {
                    followMap.put("followee", email);
                }
                // 서비스 실행
                followList = userService.searchFollowList(followMap);
                // body json add
                resultMap.put("users", followList);
                resultMap.put("success", true);
            }
        } catch (RuntimeException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
