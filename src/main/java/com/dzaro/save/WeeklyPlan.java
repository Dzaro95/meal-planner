package com.dzaro.save;

import com.dzaro.plan.DailyPlan;
import com.dzaro.dateBase.PlanDAO;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyPlan {
    PlanDAO planDAO = new PlanDAO();
    private final Map<DayOfWeek, DailyPlan> weeklyPlan = new HashMap<>();


    public Map<DayOfWeek, List<DailyPlan>> getSavePlan() {
        try {
            for (DayOfWeek day : DayOfWeek.values()) {
                this.weeklyPlan.put(day, planDAO.getPlanForDay(day));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return this.weeklyPlan;
    }
}
