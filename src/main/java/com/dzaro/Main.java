package com.dzaro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args)  {

        // Stworzenie obiektu dostępu do bazy
        // Stworzenie wszystkich wymaganych obiektów na początku programu
        // Try-Catch i obsługa błędów

        /*
        IngredientsSaver IngredientsSaver = new IngredientsSaver(dbCon);
        IngredientsSaver.save();
        */

        Add addMeal;
        DateBaseCon dbCon = new DateBaseCon();
        dbCon.test();
        FileOperation fileOperation;
        Scanner scanner = new Scanner(System.in);
        List<Object> meal = new ArrayList<>();
        boolean loop = true;
        try {
            dbCon.startProgramTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while(loop) {
            System.out.println("What would you like to do (add, show, plan, save, delete, exit)?");
            String choose = scanner.nextLine();

            switch(choose){
                case ("add"):
                    addMeal = new Add();
                    addMeal.instructionAddMealCategory();
                    addMeal.instructionAddMeal();
                    addMeal.instructionAddIngredients();


                    meal.add(addMeal);
                    System.out.println("The meal has been added!");
                    try {
                        dbCon.addValue(addMeal.getSelectMealCategory(), addMeal.getMealName(), addMeal.getIngredients());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("show"):
                    System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
                    boolean loopShow = true;
                    while(loopShow) {
                        String chooseCategory = scanner.nextLine();
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
                                        dbCon.showSQL(chooseCategory);
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
                            dbCon.addPlan();
                        } else {
                            System.out.println("First add meals in all category.");
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
                case("delete"):
                    dbCon.deleteAllTable();
                    dbCon.createAllTable();
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