package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Data Access Object
// Inserty, Selecty itp.
public class MealDAO extends DateBaseCon {
    Meal meal;
   // int meal_id;

    public MealDAO() {
       /* try {
            this.meal_id = getMeal_id();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    private List<Meal> mealFromDateBase = new ArrayList<>();

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

    public void addMeal(String category, String meal, List<String> ingredients) throws SQLException {
        final int mealId = addMealAndReturnId(category, meal);
        addIngredients(mealId, ingredients);
    }

    private int addMealAndReturnId(String category, String meal) throws SQLException {
        String mealInsert = "INSERT INTO meals (category, meal) VALUES (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(mealInsert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category);
            preparedStatement.setString(2, meal);
            preparedStatement.executeUpdate();

            try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert meal");
                }
            }
        }
    }

    private void addIngredients(int mealId, List<String> array) throws SQLException {
        String ingredientsInput = "INSERT INTO ingredients (meal_id, ingredient_id,ingredient) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(ingredientsInput)) {
            int ingredientsId = 1;
            for (String s : array) {
                preparedStatement.setInt(1, mealId);
                preparedStatement.setInt(2, ingredientsId++);
                preparedStatement.setString(3, s);
                preparedStatement.executeUpdate();
            }
        }
        //System.out.println(this.meal_id);
    }

    public void addMealAndIngriedientsForDB(Meal meal) {
        try {
            addMeal(meal.getCategory(), meal.getNameMeal(), meal.getIngredients());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

   /* public Meal getMealById(int id) {

    }*/

       /* public Meal getMealByCategory(String category) {

    }*/

    /***
     * w main
     * MealPrinter mealPrinter = new MealPrinter();
     * MealDAO mealDAO = new MealDAO();
     *
     * String category = userAnswer.getCategoryFromUser() ;
     * List<Meal>> meals = mealDAO.getMealsByCategory(category);
     * mealPrinter.printMeals(meals);
     *
     *
     */


    // Osobna klasa np. MealPrinter etc. MealPrinter::printMeal(Meal meal), printPlan(Plan plan)

    public String showNameMealForId(int id) throws SQLException {
        ResultSet mealRS = statement.executeQuery("SELECT * FROM meals WHERE meal_id = " + id);
        String nameMeal = null;
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );
        while (mealRS.next()) {
            nameMeal = mealRS.getString("meal");
        }
        return nameMeal;
    }

    public List<String> getIngredientsListForId(int id) throws SQLException {
        List<String> ingredientsList = new ArrayList<>();
        ResultSet ingredientsRS = statement.executeQuery("SELECT * FROM ingredients WHERE meal_id = " + id);
        while (ingredientsRS.next()) {
            ingredientsList.add(ingredientsRS.getString("ingredient"));
        }
        return ingredientsList;
    }

    public List<Meal> getMealAndIngredientsForCategory(String category) throws SQLException {
        List<Meal> getMealList = new ArrayList<>();
        List<Integer> listId = listIdInTableForCategory(category);
        System.out.println();
        for(int id : listId) {
            getMealList.add(new Meal(id,category,showNameMealForId(id),getIngredientsListForId(id)));
        }
        return getMealList;

    }

    public List<String> allIngredientsForMeal(String meal) throws SQLException {
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

}
