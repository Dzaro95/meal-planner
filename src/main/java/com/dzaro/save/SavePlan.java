package com.dzaro.save;

import com.dzaro.plan.DailyPlan;
import com.dzaro.dateBase.PlanDAO;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavePlan {
    PlanDAO planDAO = new PlanDAO();
    private Map<DayOfWeek, List<DailyPlan>> savePlan = new HashMap<>();


    public Map<DayOfWeek, List<DailyPlan>> getSavePlan() {
        try {
            for (DayOfWeek day : DayOfWeek.values()) {
                this.savePlan.put(day, planDAO.getPlanForDay(day));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return this.savePlan;
    }
}
