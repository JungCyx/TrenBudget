package edu.csusm.Factory;
import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.UserSession;
import edu.csusm.Model.emaptyIF;

public class SavingsGoalFactory implements SavingsFactory {
    private String name;
    private Double targetAmount;
    private String deadline;
    private Double startingAmount;
    private boolean notificationsEnabled;

    public SavingsGoalFactory(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.startingAmount = startingAmount;
        this.notificationsEnabled = notificationsEnabled;
    }

    @Override
    public emaptyIF create() {
        return new SavingsGoal(name, targetAmount, deadline, startingAmount, notificationsEnabled, UserSession.getInstance().getCurrentUser());
    }
}