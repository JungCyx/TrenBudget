package edu.csusm.Model;

//new changes 
public class SavingsGoal implements emaptyIF{

    private String name;
    private String deadline;
    private Double startingAmount;
    private Double targetAmount;
    private boolean notificationsEnabled;
    private UserModel appUser;
    private int userId;

    //Constructor
    public SavingsGoal(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled, UserModel appUser) {
        this.appUser = appUser;
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.startingAmount = startingAmount;
        this.notificationsEnabled = notificationsEnabled;

        // Get the user id from the user model
        this.userId = this.appUser.getId();
    }

    //Constructor with no user model
    public SavingsGoal(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.startingAmount = startingAmount;
        this.notificationsEnabled = notificationsEnabled;
    }

    // Empty Constructor 
    public SavingsGoal() {

    }

    public int getGoalUserId() {
        return userId;
    }

    public void setGoalUserId(int id) {
        this.userId = id;
    }

    public UserModel getUser() {
        return appUser;
    }

    public void setName(String savingsName) {
        this.name = savingsName;
    }

    public void setTargetAmount(Double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setStartingAmount(Double startingAmount) {
        this.startingAmount = startingAmount;
    }

    public void setNotificationsEnabled(boolean notificationOn) {
        this.notificationsEnabled = notificationOn;
    }

    public String getName() {
        return name;
    }

    public Double getTargetAmount() {
        return targetAmount;
    }

    public String getDeadLine() {
        return deadline;
    }

    public Double getStartingAmount() {
        return startingAmount;
    }

    public boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    @Override
    public emaptyIF getInstance() {
        return this;
    }

    public String emailContant(){

        String emailContantString = String.format("A new saving goal has been created. Titel: %s DeadLine: %s Starting amount: %.2f and Target amount: %.2f",
        this.name,
        this.deadline,
        this.startingAmount,
        this.targetAmount
        );

        return emailContantString;
    } 
}

