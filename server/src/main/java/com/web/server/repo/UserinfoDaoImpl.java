package com.web.server.repo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.server.dto.User;
import com.web.server.dto.UserProfileDto;

@Repository
public class UserinfoDaoImpl implements UserinfoDao {

    private static String ns = "com.web.server.dto.User.";

    @Autowired
    SqlSessionTemplate template;

    @Override
    public User login(String email, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", email);
        map.put("password", password);
        return template.selectOne(ns + "login", map);
    }

    /**
     * 닉네임 중복 검사
     *
     * @param nickname
     * @return
     * @throws SQLException
     */
    @Override
    public int checkAccount(String nickname) throws SQLException {
        return template.selectOne(ns + "checkbynickname", nickname);
    }

    /**
     * 계정의 중복 여부 확인
     *
     * @param email
     * @param nickname
     * @return 0 or 1 (0: 중복 없음, 1: 중복 있음)
     */
    @Override
    public int checkAccount(String email, String nickname) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("nickname", nickname);
        return template.selectOne(ns + "checkbyemailandnickname", map);
    }


    @Override
    public int insertUser(User user) {
        return template.insert(ns + "insert", user);
    }

    /**
     * select area
     */
    @Override
    public List<User> selectAll() {
        return template.selectList(ns + "selectAll");
    }

    @Override
    public User selectByIsId(String id) {
        return template.selectOne(ns + "selectbyid", id);
    }

    @Override
    public User selectByIsEmail(String email) {
        return template.selectOne(ns + "selectbyemail", email);
    }

    @Override
    public User selectByNickName(String nickname) {
        return template.selectOne(ns + "selectbynickname", nickname);
    }

    @Override
    public UserProfileDto selectUserProfileByUserId(int userId) {
    	return template.selectOne(ns + "selectbyuserprofilebyuserid", userId);
    }
    
    @Override
    public List<User> search(String by, String keyword) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("by", by);
        map.put("keyword", keyword);
        System.out.println(by);
        System.out.println(keyword);
        return template.selectList(ns + "search", map);
    }

    @Override
    public int updateUser(User user) throws SQLException{
        return template.update(ns + "update", user);
    }

    @Override
    public int deleteUser(String email) {
        return template.delete(ns + "delete", email);
    }


}
