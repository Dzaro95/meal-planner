package com.dzaro.add;

import com.dzaro.SpellingCheck;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SetMealUseCase {
    Scanner scanner = new Scanner(System.in);
    SpellingCheck spellingCheck = new SpellingCheck();

    public SetMealUseCase() {
    }

    public String setCategory() {
        String category = null;
        boolean l = true;
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        while (l) {
            String selectCategory = scanner.nextLine();
            switch(selectCategory) {
                case("breakfast"):
                case("lunch"):
                case("dinner"):
                    category = selectCategory;
                    l = false;
                    break;
                default:
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
        return category;
    }
    public String setMealName() {
        String mealName = null;
        boolean l = true;
        System.out.println("Input the meal's name:");
        while (l) {
            String checkMealName = scanner.nextLine();
            if (spellingCheck.checkWord(checkMealName.replaceAll("\\s+", ""))) {
                mealName = checkMealName;
                l = false;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
        return mealName;
    }

    public List<String> setIngredients() {
        System.out.println("Input the ingredients:");
        while (true) {
            String ingredientsInput = scanner.nextLine();
            List<String> ingredients = Arrays.stream(ingredientsInput.split(",")).map(String::trim).toList();
            if (ingredients.stream().allMatch(spellingCheck::checkWord)) {
                return ingredients;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }


}
