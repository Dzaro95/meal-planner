package com.dzaro;

import java.sql.*;
import java.util.*;


class DateBaseCon {

    // Poczyta o static i jak działa w klasach i obiektach

    private static final String DB_URL = "jdbc:postgresql:mealTest";
    private static final String USER = "postgres";
    private static final String PASS = "1111";

    private int meal_id = 0;
    private Connection connection;
    private Statement statement;


    enum DayOfWeek {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
    }

    public void test() {
        for (DayOfWeek day : DayOfWeek.values()) {
            System.out.println("id = " + day.ordinal() + " name = " + day);

        }
    }
    private final List<String> dayOfTheWeek = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    private final List<String> category = List.of("breakfast", "lunch" ,"dinner");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createAllTable() {
        try {
            statement.executeUpdate("CREATE TABLE meals (" +
                    "meal_id integer ," + // meal_id INTEGER NOT NULL AUTO_INCREMENT
                    "category varchar(100)," +
                    "meal varchar(100))"
            );

            statement.executeUpdate("CREATE TABLE ingredients (" +
                    "meal_id  integer," +
                    "ingredient_id  integer," +
                    "ingredient varchar(100))"
            );

            statement.executeUpdate("CREATE TABLE plan (" +
                    "meal_id  integer," +
                    "category varchar(100)," +
                    "meal_option  varchar(100))"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void startProgramTable() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        // DROP Table na początku
        // Podział na mniejsze funkcje które robią tylko jedną rzecz
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

    public void addValue(String category, String meal, ArrayList<String> array) throws SQLException {
        //System.out.println("mealID: " + meal_id);

        //System.out.println(meal_id);

        // Przy AUTO_INCREMENT pominąć dodawanie "meal_id", wartość zwiększy się automatycznie
        String mealInsert = "INSERT INTO meals (meal_id, category, meal) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(mealInsert)) {
            preparedStatement.setInt(1, meal_id);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, meal);
            preparedStatement.executeUpdate();
        }

        // SELECT meal_id FROM meals WHERE meal = "string"


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
        meal_id++;

    }

    // Inna nazwa, nazwa musi opisywać co robi metoda np. hasCategoryMeals(category)
    public boolean checkEmpty(String category) throws SQLException {
        int mealIDSelect = 0;

        // nazwa mealIdResult albo podobna
        ResultSet mealID = statement.executeQuery("SELECT meal_id " +
                "FROM meals WHERE category = '" + category + "'");

       // return !mealID.next();

        while (mealID.next()) {

            if (mealID.getInt("meal_id") > mealIDSelect) {
                mealIDSelect = mealID.getInt("meal_id");
            }
        }

        // System.out.println("meal id = " + mealIDSelect);
        return mealIDSelect < 1;
    }



    public boolean checkCategoryAndMeal(String category, String meal ) throws SQLException {
        int mealIDSelect = 0;
        ResultSet mealCheck = statement.executeQuery("SELECT * FROM meals " +
                "WHERE category ='"+ category+ "' AND meal ='" + meal + "'");
        while (mealCheck.next()) {
            mealIDSelect = mealCheck.getInt("meal_id");

        }

        return mealIDSelect > 0;
    }

    // Najpierw zbierasz dane od użytkownika do obiekty WeeklyPlan a na koncu przekazujesz go do metody która zapisze go w bazie;

  /*
   class WeeklyPlan {
        private List<DayPlan> dayPlans;
    }

    class DayPlan {
        private DayOfWeek day;
        private Category category;
        private Meal meal;
    }

    public void savePlan(WeeklyPlan weeklyPlan) {
        for (DayPlan dayPlan : weeklyPlan.dayPlans) {
            dayPlan.day;
            dayPlan.category;
            dayPlan.meal
        }
    }
    */


    public void addPlan() throws SQLException {
        // Rozbicie dużej metody

        Scanner scanner = new Scanner(System.in);
        statement.executeUpdate("DROP TABLE if exists plan");
        statement.executeUpdate("CREATE TABLE plan (" +
                "meal_id integer," +
                "category varchar(100)," +
                "meal_option varchar(100))"
        );

        for(int i = 0; i < dayOfTheWeek.size(); i++) {

            System.out.println(dayOfTheWeek.get(i));
            for(int j = 0; j < category.size(); j++) {
                int first = 0;

                // Postaraj się pozbyć nieskończonej pętli while(true)
                while (true) {

                    ResultSet mealRS = statement.executeQuery("SELECT * FROM meals " +
                            "WHERE category ='" + category.get(j) + "'" +
                            "ORDER BY meal");
                    // Read the result set
                    //System.out.println("listID = " + listID.get(i) );
                    while (mealRS.next()) {
                        System.out.println(mealRS.getString("meal"));
                    }
                    if (first == 0) {
                        System.out.println("Choose the " + category.get(j) + " for " + dayOfTheWeek.get(i) +
                                " from the list above:");
                        first++;
                    }

                    // To powinno być coś oddzielnego od SQLa
                    String chooseMeal = scanner.nextLine();

                    if (checkCategoryAndMeal(category.get(j), chooseMeal)) {
                        // w tabeli plan zamiast meal_id dać plan_id albo po prostu id
                        String planInsert = "INSERT INTO plan (meal_id, category, meal_option) " +
                                "VALUES (?, ?, ?)";
                        try (PreparedStatement preparedStatement = connection.prepareStatement(planInsert)) {
                            preparedStatement.setInt(1, i);
                            preparedStatement.setString(2,category.get(j));
                            preparedStatement.setString(3, chooseMeal);
                            preparedStatement.executeUpdate();
                        }
                        first = 0;
                        break;
                    } else {
                        System.out.println("This meal doesn’t exist. Choose a meal from the list above.");

                    }
                }
            }
            System.out.println("Yeah! We planned the meals for " + dayOfTheWeek.get(i) + ".");
            System.out.println();
        }
        ResultSet mealRS = statement.executeQuery("SELECT * FROM plan");
        // Read the result set
        //System.out.println("listID = " + listID.get(i) );


        while (mealRS.next()) {
            String category = mealRS.getString("category");
            // System.out.println(dayOfTheWeek.get(mealRS.getInt("meal_id")));
            if (category.equalsIgnoreCase("breakfast")) {
                System.out.println(dayOfTheWeek.get(mealRS.getInt("meal_id")));
            }
            System.out.print(category.substring(0,1).toUpperCase() + category.substring(1) + ": ");
            System.out.println(mealRS.getString("meal_option"));

            if (category.equalsIgnoreCase("dinner")) {
                System.out.println();
            }
        }
    }

    public boolean checkEmptyPlan() throws SQLException {
        int mealIDSelect = 0;
        ResultSet mealID = statement.executeQuery("SELECT meal_id FROM plan");
        while (mealID.next()) {
            mealIDSelect = mealID.getInt("meal_id");

        }

        return mealIDSelect < 1;
    }


    public void showSQL(String category) throws SQLException {

        ArrayList<Integer> listID = new ArrayList<>();
        ResultSet mealID = statement.executeQuery("SELECT * FROM meals " +
                "WHERE category = '" + category + "'");
        while (mealID.next()) {
            listID.add(mealID.getInt("meal_id"));
        }
        int mealIDSize = listID.size();
        int i = 0;
        System.out.println("Category: " + category);
        while (i < mealIDSize) {
            ResultSet mealRS = statement.executeQuery("SELECT * FROM meals WHERE meal_id = " + listID.get(i));
            // Read the result set
            //System.out.println("listID = " + listID.get(i) );
            while (mealRS.next()) {
                System.out.println("name: " + mealRS.getString("meal"));
            }

            ResultSet ingredientsRS = statement.executeQuery("SELECT * FROM ingredients WHERE meal_id = " + listID.get(i));
            System.out.println("Ingredients:");
            while (ingredientsRS.next()) {
                System.out.println(ingredientsRS.getString("ingredient"));
            }
            System.out.println();
            i++;
        }

    }
    public HashMap<String, Integer > showIngredients() throws SQLException {

        HashMap<String, Integer> ingredientsMap = new HashMap<>();
        ResultSet meal_option = statement.executeQuery("SELECT meal_option FROM plan");
        List<String> mealList = new ArrayList<>();
        List<String> ingredientList = new ArrayList<>();
        while (meal_option.next()) {
            String meal = meal_option.getString("meal_option");
            mealList.add(meal);
        }
        for (String meal : mealList) {
            ResultSet mealRS = statement.executeQuery("SELECT * FROM meals WHERE meal = '" + meal + "'");
            // Read the result set
            //System.out.println("listID = " + listID.get(i) );
            int i = 0;
            while (mealRS.next()) {
                i = mealRS.getInt("meal_id");
            }
            ResultSet ingredientsRS = statement.executeQuery("SELECT ingredient FROM ingredients WHERE meal_id = " + i);
            //System.out.println("Ingredients:");
            while (ingredientsRS.next()) {
                String list[] = ingredientsRS.getString("ingredient").split(",");
                for (int k = 0; k < list.length; k++) {

                    ingredientList.add(list[k]);
                }
                //ingredientList.add(Arrays.toString(list));
                //System.out.println(ingredientsRS.getString("ingredient"));
            }
        }
        for(String ingredients : ingredientList) {
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






