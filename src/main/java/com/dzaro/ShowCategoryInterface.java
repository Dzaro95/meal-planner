package com.dzaro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowCategoryInterface {
    UserAnswer userAnswer = new UserAnswer();
    MealDAO mealDAO = new MealDAO();
    PlanDAO planDAO = new PlanDAO();
    DateBaseCon dbCon = new DateBaseCon();
    List<Meal> listMeal = new ArrayList<>();
    public void start() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner, plan)?");
        boolean loopShow = true;
        while(loopShow) {
            String chooseCategory = userAnswer.userAnswerString().toLowerCase();
            switch (chooseCategory) {
                case ("breakfast"):
                case ("lunch"):
                case ("dinner"):
                    try {
                        if (dbCon.checkEmptyTable(chooseCategory)) {
                            System.out.println("No meals found.");
                            loopShow = false;
                            break;
                        } else {
                            System.out.println();
                            System.out.println("Category: " + chooseCategory);
                            listMeal = mealDAO.getMealAndIngredientsForCategory(chooseCategory);
                            for(Meal meal : listMeal) {
                                System.out.println("Name: " + meal.getNameMeal());
                                System.out.println("Ingredients:");
                                meal.getIngredients().forEach(ingredient -> System.out.println(ingredient));
                                System.out.println();
                            }
                            loopShow = false;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                case("plan"):
                    try {
                        planDAO.showPlannedPlan();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break;
                    }
                default:
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner, plan");
            }
        }
    }
}
