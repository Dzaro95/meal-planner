package com.dzaro;

import java.util.List;

public class DailyPlan {

    String category;
    String meal_option;
    List<String> ingredients;

    public DailyPlan(String category, String meal) {
        this.category = category;
        this.meal_option = meal;
    }

    public DailyPlan(String category, String meal_option, List<String> ingredients) {
        this.category = category;
        this.meal_option = meal_option;
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public String getMeal() {
        return meal_option;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
