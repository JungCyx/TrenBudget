package edu.csusm.Factory;
import edu.csusm.Model.BudgetGoal;
import edu.csusm.Model.UserSession;
import edu.csusm.Model.emaptyIF;

public class BudgetGoalFactory implements SavingsFactory {
    private String category;
    private double budgetAmount;
    private String startDate;
    private String endDate;
    private boolean notificationsEnabled;

    public BudgetGoalFactory(String category, double budgetAmount, String startDate, String endDate, boolean notificationsEnabled) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationsEnabled = notificationsEnabled;
    }

    //productIF
    @Override
    public emaptyIF create() {
        return new BudgetGoal(category, budgetAmount, startDate, endDate, notificationsEnabled, UserSession.getInstance().getCurrentUser());
    }
}
