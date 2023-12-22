package com.dzaro;

public class MainInterface {
    public static void startProgram() {
        AddMealInterface addMealInterface = new AddMealInterface();
        SetMealInterface addMeal;
        ShowCategoryInterface showCategoryInterface = new ShowCategoryInterface();
        UserAnswer userAnswer = new UserAnswer();
        MealDAO mealDAO = new MealDAO();
        PlanInterface planInterface = new PlanInterface();
        Plan plan;
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
            String choose = userAnswer.userAnswerString();
            switch (choose) {
                case ("add"):
                    addMealInterface.start();
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
