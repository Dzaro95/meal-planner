package com.dzaro.add;

import com.dzaro.dateBase.MealDAO;
import com.dzaro.UserInputHandler;

public class AddMealUseCase {
    UserInputHandler userInputHandler = new UserInputHandler();
    MealDAO mealDAO = new MealDAO();
    public void start() {
        System.out.println();
        try {
            mealDAO.saveMeal(userInputHandler.collectMeal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The meal has been added!");
    }
}
