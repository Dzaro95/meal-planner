package com.dzaro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plan {

    private Map<DayOfWeek, DailyPlan> plan = new HashMap<>();

    public Plan(DayOfWeek day, DailyPlan dailyPlan) {

    }
/*
    public void addDailyPlan(DayOfWeek day, DailyPlan dailyPlan) {
        plan.put(dayOfWeek,dailyPlan);
    }

 */

    public Map<DayOfWeek, DailyPlan> getPlan() {
        return plan;
    }
}

// PlanDAO - opracje na obiekcie WeeklyPlan
