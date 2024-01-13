package com.dzaro.dateBase;

import com.dzaro.Category;
import com.dzaro.UserInputHandler;

import java.sql.*;
import java.util.*;


public class DateBaseCon {
    private static String sql = "mealTest";
    private static final String DB_URL = "jdbc:postgresql:" + sql; // main db = meals_db    test db = mealTest : copyMeals_db
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    int meal_id;

    Connection connection;
    Statement statement;
    private UserInputHandler userInputHandler = new UserInputHandler();

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
    public void createTables() {
        try {
            statement.executeUpdate("CREATE TABLE meals (" +
                    "meal_id serial NOT NULL PRIMARY KEY," +
                    "category varchar(100) NOT NULL," +
                    "meal varchar(100) NOT NULL)"
            );

            statement.executeUpdate("CREATE TABLE ingredients (" +
                    "meal_id integer NOT NULL," +
                    "ingredient_id integer NOT NULL," +
                    "ingredient varchar(100) NOT NULL)"
            );

            statement.executeUpdate("CREATE TABLE plan (" +
                    "plan_id integer NOT NULL," +
                    "category varchar(100) NOT NULL," +
                    "meal_option varchar(100) NOT NULL)"
            );
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

    public int getMeal_id() throws SQLException{
        int mealIDSelect = 0;
        ResultSet mealID = statement.executeQuery("SELECT meal_id FROM meals");
        while (mealID.next()) {
            mealIDSelect = mealID.getInt("meal_id");
        }
        return ++mealIDSelect;

    }

    public void startProgramTable() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        // SQL - AUTO_INCREMENT, PRIMARY KEY, NOT NULL
        ResultSet resultset = metaData.getTables(null, null, "meals",null);

        // checking if ResultSet is empty
        if (resultset.next() == false) {
            deleteAllTable();
            createTables();
            this.meal_id = 1;
        } else {
            int mealIDSelect = 0;
            ResultSet mealID = statement.executeQuery("SELECT meal_id FROM meals");
            while (mealID.next()) {
                mealIDSelect = mealID.getInt("meal_id");
            }
            this.meal_id = ++mealIDSelect;
        }
    }

    public int generatedID() throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to insert meal, no ID obtained.");
            }
        }

    }
    // Przy AUTO_INCREMENT pominąć dodawanie "meal_id", wartość zwiększy się automatycznie

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

    public void whichCategoryEmpty() throws SQLException{
        ArrayList<String> categoryEmpty = new ArrayList<>();
        for (Category category : Category.values()) {
            ResultSet mealID = statement.executeQuery("SELECT meal_id " +
                    "FROM meals WHERE category = '" + category.toString().toLowerCase() + "'");
            if (!mealID.next()) {
                categoryEmpty.add(category.toString().toLowerCase());
            }
        }
        System.out.println("Empty category: ");
        categoryEmpty.forEach(category -> System.out.println(category));
    }




    public boolean checkCorrectCategoryAndMeal(String category, String meal ) throws SQLException {

        ResultSet mealCheck = statement.executeQuery("SELECT * FROM meals " +
                "WHERE category ='"+ category+ "' AND meal ='" + meal + "'");
        return mealCheck.next();
    }


    public ArrayList<Integer> listIdInTableForCategory(String category) throws SQLException {
        ArrayList<Integer> listID = new ArrayList<>();
        ResultSet mealID = statement.executeQuery("SELECT meal_id FROM meals " +
                "WHERE category = '" + category + "'");
        while (mealID.next()) {
            listID.add(mealID.getInt("meal_id"));
        }

        return listID;
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

    public boolean checkEmptyPlan() throws SQLException {

        ResultSet mealID = statement.executeQuery("SELECT plan_id FROM plan");
        return !mealID.next();
    }


    public void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }


}






