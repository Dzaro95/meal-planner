package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;

public class Plan extends DateBaseCon {
    Meal meal = new Meal();
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

    public void addPlanForAllDay() throws SQLException {
        // Rozbicie dużej metody
        cleanTablePlan();
        for (DayOfWeek day : DayOfWeek.values()) {

            System.out.println(UpperFirstLetter.upper(day.toString()));
            for(Category category : Category.values()) {
                int first = 0;
                // Postaraj się pozbyć nieskończonej pętli while(true)
                while (true){ // jeśli usunę while to podczas wprowadzenia złej nazwy potrawy pętla idzie dalej
                    meal.showMeals(category.toString().toLowerCase());
                    if (first == 0) {
                        System.out.println("Choose the " +category.toString().toLowerCase() + " for " + UpperFirstLetter.upper(day.toString()) +
                                " from the list above:");
                        first++;
                    }
                    String chooseMeal = userAnswer.userAnswerString();
                    if (checkCategoryAndMeal(category.toString().toLowerCase(), chooseMeal)) {
                        insertIntoPlan(day.ordinal(), category.toString(), chooseMeal);
                        first = 0;
                        break;
                    } else {
                        System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                    }
                }
            }
            System.out.println("Yeah! We planned the meals for " + UpperFirstLetter.upper(day.toString()) + ".");
            System.out.println();
        }
        showPlannedPlan();

    }



    public void insertIntoPlan(int id, String category, String chooseMeal) throws SQLException {
        String planInsert = "INSERT INTO plan (plan_id, category, meal_option) " +
                "VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(planInsert)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,category.toString());
            preparedStatement.setString(3, chooseMeal);
            preparedStatement.executeUpdate();
        }

    }

}
