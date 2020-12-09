package model.dao;

import model.dto.FoodSafeManualDto;
import model.dto.FoodSafeRecipeDto;
import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.SQLException;

public interface FoodSafeDao {

    public int insertFoodSafeRecipe(Connection con, FoodSafeRecipeDto recipeDto) throws SQLException;

    public int insertFoodSafeManual(Connection con, FoodSafeManualDto manualDto) throws SQLException;

    public boolean checkRecipe(Connection con, int rcpSeq) throws SQLException;

    public boolean checkManual(Connection con, int manualId, int rcpSeq) throws SQLException;

}
