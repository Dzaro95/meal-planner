package com.dzaro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)  {

        Add addMeal;
        Meal meal = new Meal();
        Plan plan = new Plan();
        DateBaseCon dbCon = new DateBaseCon();
        FileOperation fileOperation;
        Scanner scanner = new Scanner(System.in);
        List<Object> mealList = new ArrayList<>();
        boolean loop = true;

        try {
            dbCon.startProgramTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(meal.meal_id);
        System.out.println(dbCon.meal_id);
        while(loop) {
            System.out.println("What would you like to do (add, show, plan, save, delete plan, exit)?");
            String choose = scanner.nextLine();

            switch(choose){
                case ("add"):
                    addMeal = new Add();
                    addMeal.instructionAddMealCategory();
                    addMeal.instructionAddMeal();
                    addMeal.instructionAddIngredients();


                    mealList.add(addMeal);
                    System.out.println();
                    System.out.println("The meal has been added!");
                    try {
                        meal.addMealAndIngriedientsForDB(addMeal.getSelectMealCategory(), addMeal.getMealName(), addMeal.getIngredients());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("show"):
                    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
                    boolean loopShow = true;
                    while(loopShow) {
                        String chooseCategory = scanner.nextLine().toLowerCase();
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
                                        meal.showMealAndIngredientsForCategory(chooseCategory);
                                        loopShow = false;
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            default:
                                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                        }
                    }
                    break;
                case("plan"):
                    try {
                        if (dbCon.checkEmptyTable()) {
                            System.out.println();
                            plan.addPlanForAllDay();
                        } else {
                            System.out.println("First add meals in all category.");
                            dbCon.whichCategoryEmpty();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case("save"):
                    try {
                        fileOperation = new FileOperation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case("delete plan"):
                    dbCon.cleanTablePlan();
                    break;
                case ("exit"):
                    System.out.println("Bye!");
                    loop = false;
            }
        }
        try {
            dbCon.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
