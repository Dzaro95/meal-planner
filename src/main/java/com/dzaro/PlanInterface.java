package com.dzaro;

public class PlanInterface {
    DateBaseCon dbCon;
    PlanDAO planDAO = new PlanDAO();
    public void start() {
        try {
            if (dbCon.checkEmptyTable()) {
                System.out.println();
               // planDAO.addPlanForAllDay();
            } else {
                System.out.println("First add meals in all category.");
                dbCon.whichCategoryEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
