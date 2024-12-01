package DAO;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.BudgetGoal;
import Model.UserSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;


 public class BudgetGoalDAO{

    public static BudgetGoal currentGoal; // this is instance of a savings goal which will be updating in the dashboard
    DAO connection = new DAO();


    //get the current savings goal
    public static BudgetGoal getCurrentGoal(){
        return currentGoal;
    }

    //set the current savings goal
    public static void setCurrentBudgetGoal(BudgetGoal goal) {
         currentGoal = goal;
    }

    // The function Insert Goal into the Database table userGoal
    public void addBudgetIntoDatabase(BudgetGoal goal){ 
        String sql = "INSERT INTO budgetgoals (userId, category, budgetAmount, startDate, endDate, notificationsEnabled) VALUES (?, ?, ?, ?, ?, ?)";

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        try (Connection conn = connection.get_Connection();) {

            PreparedStatement stmt = conn.prepareStatement(sql);
                
            stmt.setInt(1, current_user_id);
            stmt.setString(2, goal.getCategory());
            stmt.setDate(3, java.sql.Date.valueOf(goal.getStartDate()));
            stmt.setDate(4, java.sql.Date.valueOf(goal.getEndDate()));   
            stmt.setDouble(5, goal.getBudgetAmount());
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
    public ArrayList<BudgetGoal> getBudgetList(){
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Initialize an empty list to store the user's savings goals
        ArrayList<BudgetGoal> budgetGoals = new ArrayList<>(); 
        
        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY id DESC"; // Example query

        try(Connection conn = connection.get_Connection();){

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){

                BudgetGoal currGoal = new BudgetGoal(); 

                currGoal.setGoalUserId(current_user_id);
                currGoal.setCategory(rs.getString("category"));
                currGoal.setStartDate(rs.getString("startDate"));
                currGoal.setEndDate(rs.getString("endDate"));
                currGoal.setBudgetAmount(rs.getDouble("budgetAmount"));
                currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));

                budgetGoals.add(currGoal);
            }
            conn.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        };
        return budgetGoals;
    }

    // The function retrives and return the latest saving goal
    public BudgetGoal getBudget(){

        BudgetGoal currGoal = new BudgetGoal();

        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
        
        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY budgetid DESC LIMIT 1"; // Example query

        try(Connection conn = connection.get_Connection();){

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){ 

                currGoal.setGoalUserId(current_user_id);
                currGoal.setCategory(rs.getString("category"));
                currGoal.setStartDate(rs.getString("startDate"));
                currGoal.setEndDate(rs.getString("endDate"));
                currGoal.setBudgetAmount(rs.getDouble("budgetAmount"));
                currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            }
            conn.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        };
        
        return currGoal;
    } 
    }