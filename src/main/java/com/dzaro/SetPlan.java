package com.dzaro;

import java.sql.SQLException;
import java.time.DayOfWeek;

public class SetPlan {

    PlanDAO planDAO = new PlanDAO();
    MealDAO mealDAO = new MealDAO();
    UserAnswer userAnswer = new UserAnswer();
    DateBaseCon dateBaseCon = new DateBaseCon();
    public void addPlanForAllDay() throws SQLException {
        dateBaseCon.cleanTablePlan();
        for (DayOfWeek day : DayOfWeek.values()) {

            System.out.println(UpperFirstLetter.upper(day.toString()));
            for(Category category : Category.values()) {
                collectCategoryName(day, category.toString());
            }
            System.out.println("Yeah! We planned the meals for " + UpperFirstLetter.upper(day.toString()) + ".");
            System.out.println();
        }
        planDAO.showPlannedPlan();

    }
    public void collectCategoryName(DayOfWeek day,String category) throws SQLException {
        System.out.println("Choose the " +category.toLowerCase() + " for " + UpperFirstLetter.upper(day.toString()) +
                " from the list above:");
        while(true) {

            mealDAO.showMeals(category.toString().toLowerCase());

            String mealOption = userAnswer.userAnswerString();
            if (dateBaseCon.checkCorrectCategoryAndMeal(category.toString().toLowerCase(), mealOption)) {
                planDAO.insertIntoPlan(day.ordinal(), userAnswer.collectPlan(category,mealOption));
                break;
            } else {
                System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
            }
        }
    }
}
