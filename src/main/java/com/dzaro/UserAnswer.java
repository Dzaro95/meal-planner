package com.dzaro;

import java.util.List;
import java.util.Scanner;

public class UserAnswer {
    AddMealInterface addMealInterface = new AddMealInterface();
    Scanner scanner = new Scanner(System.in);

    public UserAnswer() {
    }

    public String userAnswerString() {
        String answer = scanner.nextLine();
        return answer;
    }

    public Meal collectMeal() {
        String category = addMealInterface.setCategory();
        String name = addMealInterface.setMealName();
        List<String> ingredients = addMealInterface.setIngredients();

        return new Meal(category, name, ingredients);
    }
}
/*
    public Plan collectPlan() {

    }


    public void collectCategoryName(String day,String category) {
        while(true) {
            meal.showMeals(category.toString().toLowerCase());
            if (first == 0) {
                System.out.println("Choose the " +category.toString().toLowerCase() + " for " + UpperFirstLetter.upper(day.toString()) +
                        " from the list above:");
                first++;
            }
            String chooseMeal = scanner.nextLine();
            if (checkCorrectCategoryAndMeal(category.toString().toLowerCase(), chooseMeal)) {
                insertIntoPlan(day.ordinal(), category.toString(), chooseMeal);
                first = 0;
                break;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        }
    }

}

 */
