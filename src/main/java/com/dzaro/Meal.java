package com.dzaro;

import java.util.List;

public class Meal {

    private final Integer id;
    private final String category;
    private final String nameMeal;
    private final List<String> ingredients;

    // Ten konstruktor używasz kiedy pobierasz posiłek z bazy danych
    public Meal(
            Integer id,
            String category,
            String name,
            List<String> ingredients
    ) {
        this.id = id;
        this.category = category;
        this.nameMeal = name;
        this.ingredients = ingredients;
    }

    // Tan konstruktor używasz kiedy tworzysz nowy posiłek
    public Meal(
            String category,
            String name,
            List<String> ingredients
    ) {
        this(null, category, name, ingredients);
    }

    public Integer getId() {
        return id;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}