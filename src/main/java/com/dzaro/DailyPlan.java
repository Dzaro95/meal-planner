package com.dzaro;

public class DailyPlan {

    String category;
    String meal;

    public DailyPlan(String category, String meal) {
        this.category = category;
        this.meal = meal;
    }

    public String getCategory() {
        return category;
    }

    public String getMeal() {
        return meal;
    }
}
