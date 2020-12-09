package com.web.server.repo;

import com.web.server.dto.HardEasy;

import java.sql.SQLException;

public interface HardEasyDao {

    HardEasy checkChoice(HardEasy hardEasy) throws SQLException;

    void firstChoice(HardEasy hardEasy) throws SQLException;

    void hardEasyChoice(HardEasy hardEasy) throws SQLException;
}
