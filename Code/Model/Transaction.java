package Model;

/*
 User creates a transaction by filling out the required fields
 The transaction is saved and based on the transaction type the amount is subtracted from or added to the account balance 
 The user should be able to see previous transactions and edit/delete them  
 This should also be reflected in the dashboard in the appropriate section 
 */

public class Transaction{
    
    private String category; //deposit or withdrawal (if deposit +, if withdrawal +)
    private double amount;
    private boolean notificationsEnabled;
    private UserModel appUser;
    private int userId;


    //Transaction Construcor with User
    public Transaction(String category, double amount, boolean notificationsEnabled, UserModel appUser){
        this.category = category;
        this.amount = amount;
        this.notificationsEnabled = notificationsEnabled;
        this.appUser = appUser;
        this.userId = this.appUser.getId(); // Get user ID from the user model
    }

    //Transaction Construcor without User
    public Transaction(String category, double amount, boolean notificationsEnabled){
        this.category = category;
        this.amount = amount;
        this.notificationsEnabled = notificationsEnabled;
    }

    //Empty Transaction Constructor
    public Transaction(){

    }

    //Getters & Setters
    public int getGoalUserId() {
        return userId;
    }

    public void setGoalUserId(int id) {
        this.userId = id;
    }

    public UserModel getUser() {
        return appUser;
    }

    public void setCategory(String category){
        this.category = category; 
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled){
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getCategory(){
        return category;
    }

    public double getAmount(){
        return amount;
    }

    public boolean getNotificationsEnabled(){
        return notificationsEnabled;
    }




}