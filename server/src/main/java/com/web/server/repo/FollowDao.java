package com.web.server.repo;

import com.web.server.dto.FollowDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FollowDao {
    public int insert(FollowDto follow);

    public int checkFollow(int follower, int followee) throws SQLException;

    public List<Map<String, Object>> selectFollowList(Map<String, String> followMap);

    public int deleteFollow(int follower, int followee);
}
