package com.dzaro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Add  {

    private String selectMealCategory;
    private String mealName;
    private ArrayList<String> ingredients;


    Scanner scanner = new Scanner(System.in);
    List<Object> meal = new ArrayList<>();
    public Add() {
        //System.out.println("Your list is empty!");
        this.selectMealCategory = "Empty";
        this.mealName = "Empty";
        this.ingredients = new ArrayList<>();
    }
    public Add(String selectMealCategory, String mealName, ArrayList<String> ingredients) {
        this.selectMealCategory = selectMealCategory;
        this.mealName = mealName;
        this.ingredients = ingredients;
        System.out.println("The meal has been added!");
    }
    public void instructionAddMealCategory() {
        boolean l = true;
        String selectMeal = null;
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        while (l) {
            selectMeal = scanner.nextLine();
            switch(selectMeal) {
                case("breakfast"):
                case("lunch"):
                case("dinner"):
                    this.selectMealCategory = selectMeal;
                    l = false;
                    break;
                default:
                    System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }
    }
    public boolean checkWord(String s) {
        String check = s.replaceAll(",", "");

        if(s.contains(",,") || s.endsWith(",") || s.startsWith(",")) {
            return false;
        }

        if(check.isEmpty()) return false;
        if (check == null) // checks if the String is null {
            return false;
        int len = check.length();
        for (int i = 0; i < len; i++) {
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isLetter(check.charAt(i)) == false)) {
                return false;
            }
        }

        return true;
    }
    public void instructionAddMeal() {
        boolean l = true;
        String mealName = null;
        System.out.println("Input the meal's name:");
        while (l) {
            mealName = scanner.nextLine();
            if (checkWord(mealName.replaceAll("\\s+", ""))) {
                this.mealName = mealName;
                l = false;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    public ArrayList<String> deleteWhiteSpace(ArrayList<String> array) {
        ArrayList<String> arrayWithoutWhiteSpace = new ArrayList<>();
        for(String word : array) {
            arrayWithoutWhiteSpace.add(word.trim());
        }
        return arrayWithoutWhiteSpace;
    }

    public void instructionAddIngredients() {

        boolean l = true;
        String ingrediensBefore = null;
        System.out.println("Input the ingredients:");
        while (l) {
            ingrediensBefore = scanner.nextLine();

            if (checkWord(ingrediensBefore.replaceAll("\\s+", ""))) {
                //ingrediensBefore = ingrediensBefore.replaceAll("\\s+", "");
                ArrayList<String> separateIngredients = new ArrayList<>(List.of(ingrediensBefore.split(",")));
                separateIngredients = deleteWhiteSpace(separateIngredients);
                this.ingredients = separateIngredients;
                l = false;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }
    }

    @Override
    public String toString() {
        System.out.printf("Category: %s%nName: %s\nIngredients:\n" , this.selectMealCategory, this.mealName);
        for (String i : ingredients) {
            System.out.println(i);
        }
        return "";
    }

    public String getSelectMealCategory() {
        return selectMealCategory;
    }

    public String getMealName() {
        return mealName;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }


}
 /*
    public void showMeal() {
        System.out.printf("Name: %s%nName: %s \n" , this.selectMeal, this.mealName);
        for (String i : ingredients) {
            System.out.println(i);
        }

    }

  */