package com.web.server.repo;

import com.web.server.dto.User;
import com.web.server.dto.UserProfileDto;

import java.sql.SQLException;
import java.util.List;


public interface UserinfoDao {
    public User login(String email, String password);

    // c: 유저생성
    public int insertUser(User userinfo);

    // r: 유저정보 읽기
    public List<User> selectAll();

    public User selectByIsId(String uid);

    public User selectByIsEmail(String email);

    public User selectByNickName(String nickname);
    
    public UserProfileDto selectUserProfileByUserId (int userId) throws SQLException;

    public int checkAccount(String nickname) throws SQLException;

    public int checkAccount(String email, String nickname) throws SQLException;

    public List<User> search(String by, String keyword);
    // u: 회원정보 수정

    public int updateUser(User userinfo) throws SQLException;

    // d: 회원탈퇴
    public int deleteUser(String email);
}
