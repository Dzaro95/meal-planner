package com.dzaro.dateBase;

import com.dzaro.*;
import com.dzaro.plan.DailyPlan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class PlanDAO extends DateBaseCon {
    MealDAO mealDAO = new MealDAO();
    UserInputHandler userInputHandler = new UserInputHandler();
    public void showPlannedPlan() throws SQLException {
        ResultSet mealRS = statement.executeQuery("SELECT * FROM plan");
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );
        while (mealRS.next()) {
            String category = mealRS.getString("category");
            // System.out.println(dayOfTheWeek.get(mealRS.getInt("meal_id")));
            if (category.equalsIgnoreCase("breakfast")) {
                System.out.println(UpperFirstLetter.upper(DayOfWeek.of(mealRS.getInt("plan_id") + 1).toString()));
            }
            System.out.print(UpperFirstLetter.upper(category) + ": ");
            System.out.println(mealRS.getString("meal_option"));

            if (category.equalsIgnoreCase("dinner")) {
                System.out.println();
            }
        }
    }
    public  List<DailyPlan> getPlanForDay(DayOfWeek day) throws SQLException {
        List<DailyPlan> dailyList = new ArrayList<>();
        int id = day.getValue() - 1;
        ResultSet mealRS = statement.executeQuery("SELECT * FROM plan WHERE plan_id = " + id);
        while (mealRS.next()) {
            String category = mealRS.getString("category");
            String meal = mealRS.getString("meal_option");
            dailyList.add(userInputHandler.setSavePlan(category,meal));
        }

        return dailyList;
    }

    public void insertIntoPlan(int id, DailyPlan dailyplan) throws SQLException {
        String planInsert = "INSERT INTO plan (plan_id, category, meal_option) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(planInsert)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, dailyplan.getCategory());
            preparedStatement.setString(3, dailyplan.getMeal());
            preparedStatement.executeUpdate();
        }
    }
    public ArrayList<String> allMealFromPlanInList() throws SQLException{
        ArrayList<String> allMealFromPlanList = new ArrayList<>();
        ResultSet meal_option = statement.executeQuery("SELECT meal_option FROM plan");
        while (meal_option.next()) {
            String meal = meal_option.getString("meal_option");
            allMealFromPlanList.add(meal);
        }
        return allMealFromPlanList;
    }
}

