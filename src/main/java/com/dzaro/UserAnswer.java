package com.dzaro;

import java.util.List;
import java.util.Scanner;

public class UserAnswer {

    Scanner scanner = new Scanner(System.in);

    public String userAnswerString() {
        String answer = scanner.nextLine();
        return answer;
    }

    public Meal collectMeal() {
        String category = collectCategoryName();
        String name = collectMealName();
        List<String> ingredients = collectIngredients();

        return new Meal(name, category, ingredients);
    }

    private String collectCategoryName() {
        while(true) {
            String category = scanner.nextLine();

            // spradzic czy kategoria jest ok

            if (/*checkCategory(category)*/) {
                return category;
            } else {
                System.out.println("Wring meal category...");
            }
        }
    }

}
