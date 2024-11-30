package Model;

//new changes 

public class SavingsGoal {
    private String name;
    private String deadline;
    private double startingAmount;
    private double targetAmount;
    private boolean notificationsEnabled;
    private UserModel appUser;
    private int userId;
    

    //Constructor
    public SavingsGoal(String name, double targetAmount, String deadline, double startingAmount, boolean notificationsEnabled, UserModel appUser){
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
    public SavingsGoal(String name, double targetAmount, String deadline, double startingAmount, boolean notificationsEnabled){
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.startingAmount = startingAmount;
        this.notificationsEnabled = notificationsEnabled;
    }

    // Empty Constructor 
    public SavingsGoal(){
        
    }

    public int getGoalUserId(){
        return userId;
    }

    public void setGoalUserId(int id){
        this.userId = id;
    }

    public UserModel getUser(){
        return appUser;
    }

    public void setName(String savingsName){
        this.name = savingsName;
    }

    public void setTargetAmount(double targetAmount){
        this.targetAmount = targetAmount;
    }

    public void setDeadline(String deadline){
        this.deadline = deadline;
    } 

    public void setStartingAmount(double startingAmount){
        this.startingAmount = startingAmount;
    } 

    public void setNotificationsEnabled(boolean notificationOn){
        this.notificationsEnabled = notificationOn;
    }

    public String getName(){
        return name;
       }
    public double getTargetAmount(){
        return targetAmount;
    }

    public String getDeadLine(){
        return deadline;
    }
    public double getStartingAmount(){
        return startingAmount;
    }

    public boolean getNotificationsEnabled(){
        return notificationsEnabled;
    }
}

//based on the user and pass maps the user call to a module and returns the module