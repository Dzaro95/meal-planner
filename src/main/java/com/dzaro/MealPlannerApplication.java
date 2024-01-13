package com.dzaro;

import com.dzaro.add.AddMealUseCase;
import com.dzaro.add.SetMealUseCase;
import com.dzaro.dateBase.DateBaseCon;
import com.dzaro.dateBase.MealDAO;
import com.dzaro.plan.Plan;
import com.dzaro.save.FileOperation;
import com.dzaro.show.ShowCategory;

public class MealPlannerApplication {
    public static void startProgram() {
        AddMealUseCase addMealUseCase = new AddMealUseCase();
        SetMealUseCase addMeal;
        ShowCategory showCategory = new ShowCategory();
        UserInputHandler userInputHandler = new UserInputHandler();
        MealDAO mealDAO = new MealDAO();
        Plan plan = new Plan();
        DateBaseCon dbCon = new DateBaseCon();
        FileOperation fileOperation;
        boolean loop = true;

        try {
            dbCon.startProgramTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (loop) {
            System.out.println("What would you like to do (add, show, plan, save, delete plan, exit)?");
            String choose = userInputHandler.userAnswerString();
            switch (choose) {
                case ("add"):
                    addMealUseCase.start();
                    break;
                case ("show"):
                    showCategory.start();
                    break;
                case ("plan"):
                    plan.start();
                    break;
                case ("save"):
                    try {
                        fileOperation = new FileOperation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("delete plan"):
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
