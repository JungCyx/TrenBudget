package DAO;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.SavingsGoal;
import Model.UserModel;
import Model.UserSession;


 public class SavingsGoalDAO{

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
            stmt.setFloat(3, goal.getTargetAmount());
            stmt.setString(4, goal.getDeadLine());
            stmt.setFloat(5, goal.getStartingAmount());
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
   
    // The function retrives the saving goals and returns saving goal model <List> //highest to lowest return (latest value) 0,1,2
    public ArrayList<SavingsGoal> getSavingsGoalsList(){
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Initialize an empty list to store the user's savings goals
        ArrayList<SavingsGoal> savingsGoals = new ArrayList<>(); 
        
        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM usergoals WHERE userid = ? ORDER BY id DESC"; // Example query

        try(Connection conn = connection.get_Connection();){

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                SavingsGoal currGoal = new SavingsGoal();

                currGoal.setGoalUserId(current_user_id);
                currGoal.setName(rs.getString("goalName"));
                currGoal.setTargetAmount(rs.getFloat("targetAmount"));
                currGoal.setDeadline(rs.getString("deadline"));
                currGoal.setStartingAmount(rs.getFloat("startingAmount"));
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

    // The function retrieves and returns the latest savings goal
public SavingsGoal getSavingsGoal() {
    SavingsGoal currGoal = null; // Initialize to null to handle cases where no goal is found

    // Get the current user
    UserModel currentUser = UserSession.getInstance().getCurrentUser();

    // Check if the user is logged in
    if (currentUser == null) {
        System.out.println("No user is currently logged in. Cannot retrieve savings goal.");
        return null; // Return null or throw an exception based on your application's needs
    }

    int current_user_id = currentUser.getId(); // Get the current user's ID

    // Query the database to get the savings goal for the current user
    String sql = "SELECT * FROM usergoals WHERE userId = ? ORDER BY id DESC LIMIT 1"; // Ensure the query filters by userId

    try (Connection conn = connection.get_Connection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, current_user_id); // Set the parameter for userId

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) { // Use if instead of while since LIMIT 1 returns a single row
            currGoal = new SavingsGoal();
            currGoal.setGoalUserId(current_user_id);
            currGoal.setName(rs.getString("goalName"));
            currGoal.setTargetAmount(rs.getFloat("targetAmount"));
            currGoal.setDeadline(rs.getString("deadline"));
            currGoal.setStartingAmount(rs.getFloat("startingAmount"));
            currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
        }

        // Close resources (try-with-resources automatically handles this)
        rs.close();

    } catch (SQLException e) {
        System.out.println("Failed to retrieve savings goal!");
        e.printStackTrace();
    }

    return currGoal; // Returns null if no goal is found or if an error occurred
}


}

 
