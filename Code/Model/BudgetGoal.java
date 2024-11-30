package Model;

import java.time.LocalDate;
import java.time.Period;

public class BudgetGoal {
    private String category;
    private double budgetAmount;
    private LocalDate startDate; // Start date of the budget goal
    private LocalDate endDate;   // End date of the budget goal
    private boolean notificationsEnabled;
    private UserModel appUser;
    private int userId;

    // Constructor with user model
    public BudgetGoal(String category, double budgetAmount, LocalDate startDate, LocalDate endDate, boolean notificationsEnabled, UserModel appUser) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationsEnabled = notificationsEnabled;
        this.appUser = appUser;
        this.userId = this.appUser.getId(); // Get user ID from the user model
    }

    // Constructor without user model
    public BudgetGoal(String category, double budgetAmount, LocalDate startDate, LocalDate endDate, boolean notificationsEnabled) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationsEnabled = notificationsEnabled;
    }

    // Empty Constructor
    public BudgetGoal() {
    }

    // Getters and Setters
    public int getGoalUserId() {
        return userId;
    }

    public void setGoalUserId(int id) {
        this.userId = id;
    }

    public UserModel getUser() {
        return appUser;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getCategory() {
        return category;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    // Calculate duration in days
    public int getDurationInDays() {
        if (startDate != null && endDate != null) {
            return Period.between(startDate, endDate).getDays();
        }
        return 0;
    }
}
