package com.web.server.service;

import com.web.server.dto.FollowDto;
import com.web.server.dto.User;
import com.web.server.dto.UserProfileDto;
import com.web.server.repo.BoardDao;
import com.web.server.repo.FollowDao;
import com.web.server.repo.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    UserinfoDao uDao;

    @Autowired
    BoardDao bDao;

    @Autowired
    FollowDao fDao;

    @Override
    public User login(String email, String password) {
        return uDao.login(email, password);
    }

    /**
     * 닉네임 중복 여부 체크
     *
     * @param nickname
     * @return
     * @throws SQLException
     */
    @Override
    public boolean checkAccount(String nickname) throws SQLException {
        int check = uDao.checkAccount(nickname);
        return check == 0 ? true : false;
    }

    /**
     * 이메일, 닉네임의 중복 여부를 체크한다.
     *
     * @param email
     * @param nickname
     * @return true or false (true : 중복 없음, false : 중복 있음)
     */
    @Override
    public boolean checkAccount(String email, String nickname) throws SQLException {
        int check = uDao.checkAccount(email, nickname);
        return check == 0 ? true : false;
    }

    @Override
    public boolean checkFollow(String email, String nickname) throws SQLException {
        int follower = uDao.selectByIsEmail(email).getUserId();
        int followee = uDao.selectByNickName(nickname).getUserId();
        int check = fDao.checkFollow(follower, followee);
        return check == 0 ? true : false;
    }

    /**
     * 사용자 정보 수정
     *
     * @param user
     * @return
     */
    @Override
    public int modify(User user) throws SQLException{
        int result = 0;
        result = uDao.updateUser(user);
        return result;
    }

    @Override
    public User searchByEmail(String email) {
        User resultUser = uDao.selectByIsEmail(email);
        return resultUser;
    }

    @Override
    public User searchByNickName(String nickname) {
        return uDao.selectByNickName(nickname);
    }

    /**
     * 사용자 마이페이지 조회
     *
     * @param email
     * @return
     * @throws SQLException
     */
    @Override
    public UserProfileDto searchUserProfileByEmail(String email) throws SQLException{
        UserProfileDto resultDto  = null;
        // get user
        int userId = uDao.selectByIsEmail(email).getUserId();
        // users table (nickname, introduce, box) + follow table (following count, follower count)
        resultDto = uDao.selectUserProfileByUserId(userId);
        // boards table (Boards written by user)
        resultDto.setMyBoards(bDao.selectByUserId(userId));
        // interest_boards table (user's interest boards)
        resultDto.setInterestBoards(bDao.selectInterestBoardsByUserId(userId));
        return resultDto;
    }

    @Override
    public List<Map<String, Object>> searchFollowList(Map<String, String> followMap) {
        return fDao.selectFollowList(followMap);
    }

    /**
     * 사용자 삭제
     *
     * @param email
     * @return
     */
    @Override
    public int deleteUser(String email) throws SQLException {
        return uDao.deleteUser(email);
    }

    /**
     * 팔로우 취소
     *
     * @param email
     * @param nickname
     */
    @Override
    public int deleteFollow(String email, String nickname) {
        int follower = uDao.selectByIsEmail(email).getUserId();
        int followee = uDao.selectByNickName(nickname).getUserId();
        return fDao.deleteFollow(follower, followee);
    }

    @Override
    public int insertUser(User user) {
        return uDao.insertUser(user);
    }

    @Override
    public int insertFollow(FollowDto follow) throws SQLException {
        return fDao.insert(follow);
    }


    @Override
    public int updateUser(User user) throws SQLException{
        return uDao.updateUser(user);
    }
}
