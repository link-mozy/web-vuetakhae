package model.service;

import model.dto.FoodSafeManualDto;
import model.dto.FoodSafeRecipeDto;

public interface FoodSafeService {

    public int insertRecipe(FoodSafeRecipeDto recipeDto);

    public int insertManual(FoodSafeManualDto manualDto);
}
