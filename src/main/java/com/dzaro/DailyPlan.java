package com.dzaro;

public class DailyPlan {

    String category;
    String meal_option;

    public DailyPlan(String category, String meal) {
        this.category = category;
        this.meal_option = meal;
    }

    public String getCategory() {
        return category;
    }

    public String getMeal() {
        return meal_option;
    }
}
