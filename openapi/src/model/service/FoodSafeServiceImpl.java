package model.service;

import model.dao.FoodSafeDao;
import model.dao.FoodSafeDaoImpl;
import model.dto.FoodSafeManualDto;
import model.dto.FoodSafeRecipeDto;
import util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class FoodSafeServiceImpl implements FoodSafeService{
    // 싱글통 디자인 패턴
    private FoodSafeDao foodSafeDao;

    private static FoodSafeServiceImpl foodSafeService = new FoodSafeServiceImpl();

    public FoodSafeServiceImpl() {
        foodSafeDao = new FoodSafeDaoImpl();
    }

    // 서비스
    @Override
    public int insertRecipe(FoodSafeRecipeDto recipeDto) {
        Connection con = null;
        int result = -1;
        try {
            con = DBUtil.getConnection();
            if(foodSafeDao.checkRecipe(con, recipeDto.getRcpSeq())) return result;
            con.setAutoCommit(false);
            result = foodSafeDao.insertFoodSafeRecipe(con, recipeDto);
            con.commit();
        } catch (SQLException e) {
        	e.printStackTrace();
        	System.out.println("insertRecipe() ERROR : " + recipeDto.toString());
            DBUtil.rollback(con);
        } finally {
            DBUtil.close(con);
        }
        return result;
    }

    @Override
    public int insertManual(FoodSafeManualDto manualDto) {
        Connection con = null;
        int result = -1;
        try {
            con = DBUtil.getConnection();
            if(foodSafeDao.checkManual(con, manualDto.getManualId(), manualDto.getRcpSeq())) return result;
            con.setAutoCommit(false);
            result = foodSafeDao.insertFoodSafeManual(con, manualDto);
            con.commit();
        } catch (SQLException e) {
            DBUtil.rollback(con);
        } finally {
            DBUtil.close(con);
        }
        return result;
    }
}
