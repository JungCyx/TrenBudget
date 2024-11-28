package Model;
//new changes 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SavingsGoal {
    private String name;
    private double targetAmount;
    private String deadline;
    private double startingAmount;
    private boolean notificationsEnabled;

    //Constructor
    public SavingsGoal(String name, double targetAmount, String deadline, double startingAmount, boolean notificationsEnabled) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.startingAmount = startingAmount;
        this.notificationsEnabled = notificationsEnabled;
    }

    //getters 
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
