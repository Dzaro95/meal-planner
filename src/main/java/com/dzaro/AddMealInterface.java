package com.dzaro;

public class AddMealInterface {
    UserAnswer userAnswer = new UserAnswer();
    MealDAO mealDAO = new MealDAO();
    public void start() {
        System.out.println();
        try {
            mealDAO.addMealAndIngriedientsForDB(userAnswer.collectMeal());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("The meal has been added!");
    }
}
