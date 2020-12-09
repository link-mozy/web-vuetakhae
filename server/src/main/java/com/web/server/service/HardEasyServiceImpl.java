package com.web.server.service;

import com.web.server.dto.HardEasy;
import com.web.server.repo.HardEasyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class HardEasyServiceImpl implements HardEasyService{

    @Autowired
    HardEasyDao hardEasyDao;

    @Override
    public HardEasy checkChoice(HardEasy hardEasy) throws SQLException {
            return hardEasyDao.checkChoice(hardEasy);
    }

    @Override
    @Transactional
    public void createChoice(HardEasy hardEasy) throws SQLException {
//        System.out.println(hardEasy);
        System.out.println(hardEasyDao.checkChoice(hardEasy));
        if (hardEasyDao.checkChoice(hardEasy)==null){
            hardEasyDao.firstChoice(hardEasy);
        }else{
            hardEasy.setGrade(hardEasyDao.checkChoice(hardEasy).getGrade());
            hardEasyDao.hardEasyChoice(hardEasy);
        }

    }
}
