package com.dzaro;

import java.sql.*;
import java.time.DayOfWeek;
import java.util.*;


class DateBaseCon {

    private static final String DB_URL = "jdbc:postgresql:mealTest"; // main db = meals_db    test db = mealTest : copyMeals_db
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private int meal_id = 0;
    private Connection connection;
    private Statement statement;
    private UserAnswer userAnswer = new UserAnswer();

    public void test() {
        System.out.println(DayOfWeek.of(1));
    }

    public DateBaseCon()  {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(true);
            statement = connection.createStatement();
        } catch (SQLException exception) {
            System.out.println("Wystąpił problem z połączeniem do bazy danych. Błąd: " + exception.getMessage() );
        }
    }
    public void deleteAllTable() {
        try{
            statement.executeUpdate("DROP TABLE if exists meals");
            statement.executeUpdate("DROP TABLE if exists ingredients");
            statement.executeUpdate("DROP TABLE if exists plan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void cleanTablePlan() {
        try{
            statement.executeUpdate("DROP TABLE if exists plan");
            statement.executeUpdate("CREATE TABLE plan (" +
                    "plan_id SERIAL NOT NULL," +
                    "category varchar(100) NOT NULL," +
                    "meal_option varchar(100) NOT NULL )"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAllTable() {
        try {
            statement.executeUpdate("CREATE TABLE meals (" +
                    "meal_id integer SERIAL NOT NULL PRIMARY KEY," + // meal_id INTEGER NOT NULL AUTO_INCREMENT
                    "category varchar(100) NOT NULL," +
                    "meal varchar(100) NOT NULL)"
            );

            statement.executeUpdate("CREATE TABLE ingredients (" +
                    "meal_id integer SERIAL NOT NULL PRIMARY KEY," +
                    "ingredient_id integer NOT NULL," +
                    "ingredient varchar(100) NOT NULL)"
            );

            statement.executeUpdate("CREATE TABLE plan (" +
                    "plan_id integer SERIAL NOT NULL," +
                    "category varchar(100) NOT NULL," +
                    "meal_option varchar(100) NOT NULL)"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void startProgramTable() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        // SQL - AUTO_INCREMENT, PRIMARY KEY, NOT NULL
        ResultSet resultset = metaData.getTables(null, null, "meals",null);

        // checking if ResultSet is empty
        if (resultset.next() == false) {

            createAllTable();
            meal_id = 1;
        } else {
            int mealIDSelect = 0;
            ResultSet mealID = statement.executeQuery("SELECT meal_id FROM meals");
            while (mealID.next()) {
                mealIDSelect = mealID.getInt("meal_id");
            }
            meal_id = ++mealIDSelect;
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
    // Przy AUTO_INCREMENT pominąć dodawanie "meal_id", wartość zwiększy się automatycznie
    public void addValue(String category, String meal, ArrayList<String> array) {
        try {
            addMeal(category, meal);
            addIngredients(array);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        meal_id++;

    }

    // Inna nazwa, nazwa musi opisywać co robi metoda np. hasCategoryMeals(category)
    public boolean checkEmptyTable(String category) throws SQLException {

        ResultSet mealID = statement.executeQuery("SELECT meal_id " +
                "FROM meals WHERE category = '" + category + "'");
        return !mealID.next();
    }
    public boolean checkEmptyTable() throws SQLException {
        boolean check = true;
        for (Category category : Category.values()) {
            ResultSet mealID = statement.executeQuery("SELECT meal_id " +
                    "FROM meals WHERE category = '" + category.toString().toLowerCase() + "'");
            if (!mealID.next()) {
                check = false;
            }
        }
        //System.out.println("zwracam: " + check);
        return check;
    }




    public boolean checkCategoryAndMeal(String category, String meal ) throws SQLException {

        ResultSet mealCheck = statement.executeQuery("SELECT * FROM meals " +
                "WHERE category ='"+ category+ "' AND meal ='" + meal + "'");
        return mealCheck.next();
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
    public void showPlannedPlan() throws SQLException{
        ResultSet mealRS = statement.executeQuery("SELECT * FROM plan");
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );


        while (mealRS.next()) {
            String category = mealRS.getString("category");
            // System.out.println(dayOfTheWeek.get(mealRS.getInt("meal_id")));
            if (category.equalsIgnoreCase("breakfast")) {
                System.out.println(UpperFirstLetter.upper(DayOfWeek.of(mealRS.getInt("meal_id")).toString()));
            }
            System.out.print(UpperFirstLetter.upper(category) + ": ");
            System.out.println(mealRS.getString("meal_option"));

            if (category.equalsIgnoreCase("dinner")) {
                System.out.println();
            }
        }
    }

    public void addPlan() throws SQLException {
        // Rozbicie dużej metody
        cleanTablePlan();
        for (DayOfWeek day : DayOfWeek.values()) {

            System.out.println(UpperFirstLetter.upper(day.toString()));
            for(Category category : Category.values()) {
                int first = 0;
                // Postaraj się pozbyć nieskończonej pętli while(true)
                while (true){ // jeśli usunę while to podczas wprowadzenia złej nazwy potrawy pętla idzie dalej
                        showMeals(category.toString().toLowerCase());
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

    public boolean checkEmptyPlan() throws SQLException {

        ResultSet mealID = statement.executeQuery("SELECT plan_id FROM plan");
        return !mealID.next();
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

    public ArrayList<Integer> listIndexInTable(String category) throws SQLException {
        ArrayList<Integer> listID = new ArrayList<>();
        ResultSet mealID = statement.executeQuery("SELECT meal_id FROM meals " +
                "WHERE category = '" + category + "'");
        while (mealID.next()) {
            listID.add(mealID.getInt("meal_id"));
        }

        return listID;
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

    public ArrayList<String> allMealFromPlanInList() throws SQLException{
        ArrayList<String> allMealFromPlanList = new ArrayList<>();
        ResultSet meal_option = statement.executeQuery("SELECT meal_option FROM plan");
        while (meal_option.next()) {
            String meal = meal_option.getString("meal_option");
           allMealFromPlanList.add(meal);
        }
        return allMealFromPlanList;
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
    public void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }


}






