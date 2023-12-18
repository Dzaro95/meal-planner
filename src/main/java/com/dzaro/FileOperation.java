package com.dzaro;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;


public class FileOperation extends DateBaseCon  {
    Scanner scanner = new Scanner(System.in);
    //String filePath;
    String fileName;


    public FileOperation() throws SQLException {
        if(checkEmptyPlan()) {
            System.out.println("Unable to save. Plan your meals first.");
        } else {
            System.out.println("Input a filename:");
            String fileName = scanner.nextLine();
            createFile(fileName);
            saveIngredients();
        }
    }
    /*
    public boolean checkPlan() throws SQLException {
        return checkEmptyPlan();
    }

     */
    public void createFile(String fileName) {
        try {
            //filePath = "C:\\Users\\dom\\Desktop\\Java\\Ingredients\\" + fileName;
            this.fileName = fileName;
            File createFile = new File(fileName);
            if (createFile.createNewFile()) {
                // System.out.println("Created!");
            } else {
                //System.out.println("File already exists.");
            }
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveIngredients() throws SQLException {
        File file = new File(fileName);
        try (PrintWriter printWriter = new PrintWriter(file)) {

            HashMap<String, Integer> ingredients = showIngredients();
            for (String key : ingredients.keySet()) {
                if(ingredients.get(key) > 1) {
                    printWriter.println(key + " x" + ingredients.get(key));
                    //System.out.println(key + " x" + ingredients.get(key));
                } else {
                    printWriter.println(key);
                }
            }

            System.out.println("Saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}