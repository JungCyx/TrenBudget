package edu.csusm.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class BudgetGoal {

    private String category;
    private double budgetAmount;
    private LocalDate startDate; // Start date of the budget goal
    private LocalDate endDate;   // End date of the budget goal
    private boolean notificationsEnabled;
    private UserModel appUser;
    private int id;
    private int userId;

    // Constructor with user model
    public BudgetGoal(String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled, UserModel appUser) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.notificationsEnabled = notificationsEnabled;
        this.appUser = appUser;
        this.userId = this.appUser.getId(); // Get user ID from the user model

        // Parse the string inputs to LocalDate
        this.startDate = parseDate(startDateString);
        this.endDate = parseDate(endDateString);
    }

    // Constructor without user model
    public BudgetGoal(String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled) {
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.notificationsEnabled = notificationsEnabled;

        // Parse the string inputs to LocalDate
        this.startDate = parseDate(startDateString);
        this.endDate = parseDate(endDateString);
    }

    // Constructor without user model
    public BudgetGoal(int id, String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled) {
        this.id = id;
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.notificationsEnabled = notificationsEnabled;

        // Parse the string inputs to LocalDate
        this.startDate = parseDate(startDateString);
        this.endDate = parseDate(endDateString);
    }

    // Empty Constructor
    public BudgetGoal(String category2, double totalBudget) {
    }

    public BudgetGoal() {
        //TODO Auto-generated constructor stub
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Parse the date string to LocalDate
    private LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Format expected: "2024-01-01"
        return LocalDate.parse(dateString, formatter);
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
        this.startDate = startDate;  // Parse the new date string
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;  // Parse the new date string
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

    // Calculates duration of BudgetGoal
    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
}
