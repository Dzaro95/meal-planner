package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Meal extends DateBaseCon{
    private ArrayList<String> ingredients = new ArrayList<>();

    public void showMeals(String category) throws SQLException {
        ResultSet mealRS = statement.executeQuery("SELECT * FROM meals " +
                "WHERE category ='" + category + "'" +
                "ORDER BY meal");
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );
        while (mealRS.next()) {
            System.out.println(mealRS.getString("meal"));
        }

    }

    public void addMeal(String category, String meal) throws SQLException {
        String mealInsert = "INSERT INTO meals (meal_id, category, meal) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(mealInsert)) {
            preparedStatement.setInt(1, meal_id);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, meal);
            preparedStatement.executeUpdate();
        }
        System.out.println(meal_id);
    }

    public void addIngredients(ArrayList<String> array) throws SQLException {
        String ingredientsInput = "INSERT INTO ingredients (meal_id, ingredient_id,ingredient) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(ingredientsInput)) {
            int ingredientsId = 1;
            for (String s : array) {
                preparedStatement.setInt(1, meal_id);
                preparedStatement.setInt(2, ingredientsId++);
                preparedStatement.setString(3, s);
                preparedStatement.executeUpdate();
            }
        }
        System.out.println(meal_id);
    }





}
