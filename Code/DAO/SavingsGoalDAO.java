package DAO;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import Model.SavingsGoal;
import View.SavingsGUI;



 public class SavingsGoalDAO{

    public static SavingsGoal currentGoal; // this is instance of a savings goal which will be updating in the dashboard

    //get the current savings goal
    public static SavingsGoal getCurrentGoal(){
        return currentGoal;
    }

    //set the current savings goal
    public static void setCurrentSavingsGoal(SavingsGoal goal) {
         currentGoal = goal;
        
    }
   
}

 
