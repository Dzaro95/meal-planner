package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Meal extends DateBaseCon{
    int meal_id;

    public Meal() {
        try {
            this.meal_id = getMeal_id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        System.out.println(meal_id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(mealInsert)) {
            preparedStatement.setInt(1, this.meal_id);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, meal);
            preparedStatement.executeUpdate();
        }
        System.out.println(this.meal_id);
    }

    public void addIngredients(ArrayList<String> array) throws SQLException {
        String ingredientsInput = "INSERT INTO ingredients (meal_id, ingredient_id,ingredient) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(ingredientsInput)) {
            int ingredientsId = 1;
            for (String s : array) {
                preparedStatement.setInt(1, this.meal_id);
                preparedStatement.setInt(2, ingredientsId++);
                preparedStatement.setString(3, s);
                preparedStatement.executeUpdate();
            }
        }
        System.out.println(this.meal_id);
    }

    public void addMealAndIngriedientsForDB(String category, String meal, ArrayList<String> array) {
        try {
            addMeal(category, meal);
            addIngredients(array);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        this.meal_id++;

    }
    public void showNameMealForId(int id) throws SQLException {
        ResultSet mealRS = statement.executeQuery("SELECT * FROM meals WHERE meal_id = " + id);
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );
        while (mealRS.next()) {
            System.out.println("name: " + mealRS.getString("meal"));
        }
    }
    public void showIngredientsForId(int id) throws SQLException {
        ResultSet ingredientsRS = statement.executeQuery("SELECT * FROM ingredients WHERE meal_id = " + id);
        System.out.println("Ingredients:");
        while (ingredientsRS.next()) {
            System.out.println(ingredientsRS.getString("ingredient"));
        }
    }

    public void showMealAndIngredientsForCategory(String category) throws SQLException {

        ArrayList<Integer> lisIdInCategory = listIndexInTable(category);
        System.out.println("Category: " + category);
        System.out.println();
        for(int id : lisIdInCategory) {
            showNameMealForId(id);
            showIngredientsForId(id);
            System.out.println();
        }

    }

    public ArrayList<String> allIngredientsForMeal(String meal) throws SQLException {
        ArrayList<String> ingredientsList = new ArrayList<>();
        ResultSet mealRS = statement.executeQuery("SELECT * FROM meals WHERE meal = '" + meal + "'");
        int id = 0;
        while (mealRS.next()) {
            id = mealRS.getInt("meal_id");
        }
        ResultSet ingredientsRS = statement.executeQuery("SELECT ingredient FROM ingredients WHERE meal_id = " + id);
        while (ingredientsRS.next()) {
            String list[] = ingredientsRS.getString("ingredient").split(",");
            for (int k = 0; k < list.length; k++) {

                ingredientsList.add(list[k]);
            }
        }
        return ingredientsList;
    }
    public HashMap<String, Integer > showIngredients() throws SQLException {

        HashMap<String, Integer> ingredientsMap = new HashMap<>();
        ArrayList<String> mealList = allMealFromPlanInList();
        ArrayList<String> ingredientsList = new ArrayList<>();
        for (String meal : mealList) {
            ingredientsList = allIngredientsForMeal(meal);
        }
        for(String ingredients : ingredientsList) {
            if(ingredientsMap.containsKey(ingredients)) {
                int value = ingredientsMap.get(ingredients);
                ingredientsMap.put(ingredients,++value);

            } else {
                ingredientsMap.put(ingredients,1);
            }
        }
        return ingredientsMap;
    }





}
