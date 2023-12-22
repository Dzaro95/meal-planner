package com.dzaro;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserAnswer {
    SetMealInterface setMealInterface = new SetMealInterface();
    Scanner scanner = new Scanner(System.in);

    public UserAnswer() {
    }

    public String userAnswerString() {
        String answer = scanner.nextLine();
        return answer;
    }

    public Meal collectMeal() {
        String category = setMealInterface.setCategory();
        String name = setMealInterface.setMealName();
        List<String> ingredients = setMealInterface.setIngredients();

        return new Meal(category, name, ingredients);
    }

    public DailyPlan collectPlan ( String category, String mealOption) {
        return new DailyPlan(category, mealOption);
    }




}

