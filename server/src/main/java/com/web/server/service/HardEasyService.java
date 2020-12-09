package com.web.server.service;

import com.web.server.dto.HardEasy;

import java.sql.SQLException;

public interface HardEasyService {

    HardEasy checkChoice(HardEasy hardEasy) throws SQLException;

    void createChoice(HardEasy hardEasy) throws SQLException;
}
