package com.dzaro;

import java.util.List;

public class Meal {

    private final Integer id;
    private final String name;
    private final String category;
    private final List<String> ingredients;

    // Ten konstruktor używasz kiedy pobierasz posiłek z bazy danych
    Meal(
            Integer id,
            String name,
            String category,
            List<String> ingredients
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
    }

    // Tan konstruktor używasz kiedy tworzysz nowy posiłek
    Meal(
            String name,
          String category,
          List<String> ingredients
    ) {
        this(null, name, category, ingredients);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}