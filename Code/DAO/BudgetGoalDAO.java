package DAO;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.BudgetGoal;
import Model.UserSession;


 public class BudgetGoalDAO{

    public static BudgetGoal currentBudget; // this is instance of a savings goal which will be updating in the dashboard
    DAO connection = new DAO();

    //get the current savings goal
    public static BudgetGoal getCurrentBudgetGoal(){
        return currentBudget;
    }

    //set the current savings goal
    public static void setCurrentBudgetGoal(BudgetGoal budget) {
        currentBudget = budget;
    }

    // The function Insert Goal into the Database table userGoal
    public void addBudgetIntoDatabase(BudgetGoal budget){
        String sql = "INSERT INTO budgetgoals (userId, category, budgetAmount, startDate, endDate, notificationsEnabled) VALUES (?, ?, ?, ?, ?, ?)";

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        try (Connection conn = connection.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, current_user_id);
            stmt.setString(2, budget.getCategory());
            stmt.setDouble(3, budget.getBudgetAmount());
            stmt.setDate(4, java.sql.Date.valueOf(budget.getStartDate())); 
            stmt.setDate(5, java.sql.Date.valueOf(budget.getEndDate()));   
            stmt.setBoolean(6, budget.getNotificationsEnabled());

            stmt.executeUpdate();
            System.out.println("Added into table successfully...");
        } 
        catch (SQLException e) {
            System.out.println("Failed add to table...");
            e.printStackTrace();
        };
    }
   
    // The function retrives the saving goals and returns saving goal model <List> //highest to lowest return (latest value) 0,1
    public BudgetGoal getBudgetGoal(){
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Budget goal instances 
        BudgetGoal budgetGoals = new BudgetGoal();

        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY budgetId DESC LIMIT 1"; // Example query

        try(Connection conn = connection.get_Connection();){

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                BudgetGoal currBudget = new BudgetGoal();

                currBudget.setGoalUserId(current_user_id);
                currBudget.setCategory(rs.getString("category"));
                currBudget.setBudgetAmount(rs.getDouble("budgetAmount"));
                currBudget.setStartDate(rs.getDate("startDate").toLocalDate());
                currBudget.setEndDate(rs.getDate("endDate").toLocalDate());
                currBudget.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        };
        return budgetGoals;
    }
}

 
