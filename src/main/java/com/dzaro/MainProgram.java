package com.dzaro;

import com.dzaro.add.AddMealUseCase;
import com.dzaro.add.SetMealUseCase;
import com.dzaro.dateBase.DateBaseCon;
import com.dzaro.dateBase.MealDAO;
import com.dzaro.plan.PlanInterface;
import com.dzaro.save.FileOperation;
import com.dzaro.show.ShowCategoryInterface;

public class MainProgram {
    public static void startProgram() {
        AddMealUseCase addMealUseCase = new AddMealUseCase();
        SetMealUseCase addMeal;
        ShowCategoryInterface showCategoryInterface = new ShowCategoryInterface();
        UserInputHandler userInputHandler = new UserInputHandler();
        MealDAO mealDAO = new MealDAO();
        PlanInterface planInterface = new PlanInterface();
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
                    showCategoryInterface.start();
                    break;
                case ("plan"):
                    planInterface.start();
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
