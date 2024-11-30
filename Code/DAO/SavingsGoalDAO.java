package DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import Model.SavingsGoal;
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
   
}

 
