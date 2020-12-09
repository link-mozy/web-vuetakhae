package com.web.server.repo;

import com.web.server.dto.FollowDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FollowDaoImpl implements FollowDao {
    private String ns = "com.web.server.dto.User.";

    @Autowired
    SqlSessionTemplate template;

    @Override
    public int insert(FollowDto follow) {
        return template.insert(ns + "insertFollow", follow);
    }

    @Override
    public int checkFollow(int follower, int followee) throws SQLException {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("follower", follower);
        map.put("followee", followee);
        return template.selectOne(ns + "checkfollow", map);
    }

    @Override
    public List<Map<String, Object>> selectFollowList(Map<String, String> followMap) {
        return template.selectList(ns + "selectFollowList", followMap);
    }

    @Override
    public int deleteFollow(int follower, int followee) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("follower", follower);
        map.put("followee", followee);
        return template.delete(ns + "deletefollow", map);
    }
}
