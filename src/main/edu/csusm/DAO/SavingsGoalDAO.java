package edu.csusm.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.UserSession;

public class SavingsGoalDAO {

    public static SavingsGoal currentGoal; // this is instance of a savings goal which will be updating in the dashboard
    DAO connection = new DAO();


    //get the current savings goal
    public static SavingsGoal getCurrentGoal(){
        return currentGoal;
    }

    //set the current savings goal
    public static void setCurrentSavingsGoal(SavingsGoal goal) {
         currentGoal = goal;
    }

    // The function Insert Goal into the Database table userGoal
    public void addGoalIntoDatabase(SavingsGoal goal){
        String sql = "INSERT INTO usergoals (userId, goalName, targetAmount, deadline, startingAmount, notificationsEnabled) VALUES (?, ?, ?, ?, ?, ?)";

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        try (Connection conn = connection.get_Connection();) {

            PreparedStatement stmt = conn.prepareStatement(sql);
                
            stmt.setInt(1, current_user_id);
            stmt.setString(2, goal.getName());
            stmt.setDouble(3, goal.getTargetAmount());
            stmt.setString(4, goal.getDeadLine());       
            stmt.setDouble(5, goal.getStartingAmount());
            stmt.setBoolean(6, goal.getNotificationsEnabled());

            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Added into table successfully...");
    
        } catch (SQLException e) {
            System.out.println("Failed add to table...");
            e.printStackTrace();
        };
    }
   
    // The function retrives the saving goals and returns saving goal model <List> //highest to lowest return (latest value) 0,1
    public ArrayList<SavingsGoal> getSavingsGoalsList(){
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Initialize an empty list to store the user's savings goals
        ArrayList<SavingsGoal> savingsGoals = new ArrayList<>(); 
        
        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM usergoals WHERE userId = ? ORDER BY goalId DESC"; // Example query

        try(Connection conn = connection.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                SavingsGoal currGoal = new SavingsGoal();

                currGoal.setGoalUserId(current_user_id);
                currGoal.setName(rs.getString("goalName"));
                currGoal.setTargetAmount(rs.getDouble("targetAmount"));
                currGoal.setDeadline(rs.getString("deadline"));
                currGoal.setStartingAmount(rs.getDouble("startingAmount"));
                currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));

                savingsGoals.add(currGoal);
            }
        
            conn.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        };
        return savingsGoals;
    }


    // The function retrives and return the latest saving goal
    public SavingsGoal getSavingsGoal(){

        SavingsGoal currGoal = null;

        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
        
        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM usergoals WHERE userId = ? ORDER BY goalId DESC LIMIT 1"; // Example query

        try(Connection conn = connection.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                currGoal = new SavingsGoal();

                currGoal.setGoalUserId(current_user_id);
                currGoal.setName(rs.getString("goalName"));
                currGoal.setTargetAmount(rs.getDouble("targetAmount"));
                currGoal.setDeadline(rs.getString("deadline"));
                currGoal.setStartingAmount(rs.getDouble("startingAmount"));
                currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        }
        return currGoal;
    }

    // New method to update an existing savings goal in the database
    public void updateSavingsGoal(SavingsGoal goal) {
        String sql = "UPDATE usergoals SET goalName = ?, targetAmount = ?, deadline = ?, startingAmount = ?, notificationsEnabled = ? " +
                     "WHERE userId = ? AND goalName = ?";
    
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
        
        try (Connection conn = connection.get_Connection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Set parameters for the update query
            stmt.setString(1, goal.getName());
            stmt.setDouble(2, goal.getTargetAmount());
            stmt.setString(3, goal.getDeadLine());
            stmt.setDouble(4, goal.getStartingAmount());
            stmt.setBoolean(5, goal.getNotificationsEnabled());
            stmt.setInt(6, current_user_id);
            stmt.setString(7, goal.getName()); // Using name as the identifier for existing goal
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Savings goal updated successfully...");
            } else {
                System.out.println("No savings goal was updated. Goal might not exist.");
            }
            
            conn.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Failed to update savings goal...");
            e.printStackTrace();
        }
    }
}
 
