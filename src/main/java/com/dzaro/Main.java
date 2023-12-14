package com.dzaro;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws SQLException {

        // Stworzenie obiektu dostępu do bazy
        // Stworzenie wszystkich wymaganych obiektów na początku programu
        // Try-Catch i obsługa błędów

        /*
        IngredientsSaver IngredientsSaver = new IngredientsSaver(dbCon);
        IngredientsSaver.save();
        */

        Add addMeal;
        DateBaseCon dbCon = new DateBaseCon();
        //dbCon.test();
        FileOperation fileOperation;
        Scanner scanner = new Scanner(System.in);
        List<Object> meal = new ArrayList<>();
        boolean loop = true;
        dbCon.createNewTable();
        while(loop) {
            System.out.println("What would you like to do (add, show, plan, save, exit)?");
            String choose = scanner.nextLine();

            switch(choose){
                case ("add"):
                    addMeal = new Add();
                    addMeal.instructionAddMealCategory();
                    addMeal.instructionAddMeal();
                    addMeal.instructionAddIngredients();


                    meal.add(addMeal);
                    System.out.println("The meal has been added!");
                    dbCon.addValue(addMeal.getSelectMealCategory(), addMeal.getMealName(), addMeal.getIngredients());
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
                                if (dbCon.checkEmpty(chooseCategory)) {
                                    System.out.println("No meals found.");
                                    loopShow = false;
                                    break;
                                } else {
                                    System.out.println();
                                    //dbCon.showSQL();
                                    dbCon.showSQL(chooseCategory);
                                    //meal.forEach(System.out::println);
                                    loopShow = false;
                                    break;
                                }
                            default:
                                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                        }
                    }


                    break;
                case("plan"):
                    dbCon.addPlan();
                    break;
                case("save"):
                    fileOperation = new FileOperation();
                    break;
                case ("exit"):
                    System.out.println("Bye!");
                    loop = false;
            }
        }
        dbCon.closeConnection();
    }
}