package com.dzaro.save;

import com.dzaro.*;
import com.dzaro.dateBase.DateBaseCon;
import com.dzaro.dateBase.MealDAO;
import com.dzaro.dateBase.PlanDAO;
import com.dzaro.plan.DailyPlan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;


public class FileOperation extends DateBaseCon {
    MealDAO mealDAO = new MealDAO();
    PlanDAO planDAO = new PlanDAO();
    WeeklyPlan weeklyPlan = new WeeklyPlan();
    UserInputHandler userInputHandler = new UserInputHandler();
    //String filePath;
    String fileName;


    public FileOperation() throws SQLException {
        if(checkEmptyPlan()) {
            System.out.println("Unable to save. Plan your meals first.");
        } else {
            System.out.println("Input a filename:");
            String fileName = userInputHandler.userAnswerString();
            createFile(fileName);
            savePlanFile();
        }
    }
    /*
    public boolean checkPlan() throws SQLException {
        return checkEmptyPlan();
    }

     */
    public void createFile(String fileName) {
        try {
            //filePath = "C:\\Users\\dom\\Desktop\\Java\\Ingredients\\" + fileName;
            this.fileName = fileName;
            File createFile = new File(fileName);
            if (createFile.createNewFile()) {
                // System.out.println("Created!");
            } else {
                //System.out.println("File already exists.");
            }
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void savePlanFile() throws SQLException {
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            Map<DayOfWeek, List<DailyPlan>> fullPlan = weeklyPlan.getSavePlan();
            for (DayOfWeek day : DayOfWeek.values()) {
                printWriter.println(UpperFirstLetter.upper(day.toString()));
                printWriter.println();
                List<DailyPlan> plan = fullPlan.get(day);
                   for (DailyPlan plan1 : plan) {
                       printWriter.println("Category: " + plan1.getCategory());
                       printWriter.println("Meal: " + plan1.getMeal());
                       printWriter.println("Ingredients: ");
                       mealDAO.allIngredientsForMeal(plan1.getMeal()).forEach(ingriedient -> printWriter.println(ingriedient));
                       printWriter.println();
                   }
            }

            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    public void saveIngredients() throws SQLException {
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            Map<String, Integer> ingredients = meal.showIngredients();
            for (String key : ingredients.keySet()) {
                if(ingredients.get(key) > 1) {
                    printWriter.println(key + " x" + ingredients.get(key));
                    //System.out.println(key + " x" + ingredients.get(key));
                } else {
                    printWriter.println(key);
                }
            }

            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


 */

}