package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class PlanDAO extends DateBaseCon {
    MealDAO mealDAO = new MealDAO();
    UserAnswer userAnswer = new UserAnswer();
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



    public void insertIntoPlan(int id, DailyPlan dailyplan) throws SQLException {
        String planInsert = "INSERT INTO plan (plan_id, category, meal_option) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(planInsert)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, dailyplan.category);
            preparedStatement.setString(3, dailyplan.meal_option);
            preparedStatement.executeUpdate();
        }
    }










}

