package com.dzaro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SetMealInterface {
    Scanner scanner = new Scanner(System.in);
    SpellingCheck spellingCheck = new SpellingCheck();

    public SetMealInterface() {
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
        List<String> ingredients = new ArrayList<>();
        boolean l = true;
        String ingrediensBefore = null;
        System.out.println("Input the ingredients:");
        while (l) {
            ingrediensBefore = scanner.nextLine();

            if (spellingCheck.checkWord (ingrediensBefore.replaceAll("\\s+", ""))) {
                //ingrediensBefore = ingrediensBefore.replaceAll("\\s+", "");
                ArrayList<String> separateIngredients = new ArrayList<>(List.of(ingrediensBefore.split(",")));
                separateIngredients = spellingCheck.deleteWhiteSpace(separateIngredients);
                ingredients = separateIngredients;
                l = false;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
        return ingredients;
    }


}
