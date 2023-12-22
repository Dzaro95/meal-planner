package com.dzaro;

public class PlanInterface {
    DateBaseCon dbCon = new DateBaseCon();
    SetPlan setPlan = new SetPlan();
    PlanDAO planDAO = new PlanDAO();
    public void start() {
        try {
            if (dbCon.checkEmptyTable()) {
                System.out.println();
                setPlan.addPlanForAllDay();
            } else {
                System.out.println("First add meals in all category.");
                dbCon.whichCategoryEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
