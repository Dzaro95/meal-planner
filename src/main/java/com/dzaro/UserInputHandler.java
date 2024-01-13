package com.dzaro;

import com.dzaro.add.SetMealUseCase;
import com.dzaro.plan.DailyPlan;

import java.util.List;
import java.util.Scanner;

public class UserInputHandler {
    SetMealUseCase setMealUseCase = new SetMealUseCase();
    Scanner scanner = new Scanner(System.in);

    public UserInputHandler() {
    }

    public String userAnswerString() {
        String answer = scanner.nextLine();
        return answer;
    }

    public Meal collectMeal() {
        String category = setMealUseCase.setCategory();
        String name = setMealUseCase.setMealName();
        List<String> ingredients = setMealUseCase.setIngredients();

        return new Meal(category, name, ingredients);
    }

    public DailyPlan setSavePlan(String category, String meal) {

        return new DailyPlan(UpperFirstLetter.upper(category),meal);
    }



    public DailyPlan collectPlan ( String category, String mealOption) {
        return new DailyPlan(category, mealOption);
    }




}

