package DAO;

import Model.BudgetGoal;
import Model.Transaction;
import Model.UserSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BudgetGoalDAO {

    public static BudgetGoal currentBudget; // this is instance of a savings goal which will be updating in the dashboard
    DAO connection = new DAO();

    //get the current savings goal
    public static BudgetGoal getCurrentBudgetGoal() {
        return currentBudget;
    }

    //set the current savings goal
    public static void setCurrentBudgetGoal(BudgetGoal budget) {
        currentBudget = budget;
    }

    // The function Insert Goal into the Database table userGoal
    public void addBudgetIntoDatabase(BudgetGoal budget) {
        String sql = "INSERT INTO budgetgoals (userId, category, budgetAmount, startDate, endDate, notificationsEnabled) VALUES (?, ?, ?, ?, ?, ?)";

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        try (Connection conn = connection.get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, current_user_id);
            stmt.setString(2, budget.getCategory());
            stmt.setDouble(3, budget.getBudgetAmount());
            stmt.setDate(4, java.sql.Date.valueOf(budget.getStartDate()));
            stmt.setDate(5, java.sql.Date.valueOf(budget.getEndDate()));
            stmt.setBoolean(6, budget.getNotificationsEnabled());

            stmt.executeUpdate();
            System.out.println("Added into table successfully...");
        } catch (SQLException e) {
            System.out.println("Failed add to table...");
            e.printStackTrace();
        };
    }

    // The function retrives the saving goals and returns saving goal model <List> //highest to lowest return (latest value) 0,1
    public BudgetGoal getBudgetGoal() {
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Budget goal instances 
        BudgetGoal budgetGoals = null;

        // Query the db to get the saving goal for the current user 
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY budgetId DESC LIMIT 1"; // Example query

        try (Connection conn = connection.get_Connection();) {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                budgetGoals = new BudgetGoal();

                budgetGoals.setGoalUserId(current_user_id);
                budgetGoals.setCategory(rs.getString("category"));
                budgetGoals.setBudgetAmount(rs.getDouble("budgetAmount"));
                budgetGoals.setStartDate(rs.getDate("startDate").toLocalDate());
                budgetGoals.setEndDate(rs.getDate("endDate").toLocalDate());
                budgetGoals.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrive goals!!!");
            e.printStackTrace();
        };
        return budgetGoals;
    }

    // The function retrieves all saving goals and returns a list of BudgetGoal objects
    public List<BudgetGoal> getAllBudgetGoals() {
        List<BudgetGoal> budgetGoalsList = new ArrayList<>();

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Query to get all the budget goals for the current user
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY budgetId DESC"; // Retrieves all goals, ordered by budgetId

        try (Connection conn = connection.get_Connection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, current_user_id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                BudgetGoal budgetGoal = new BudgetGoal();

                budgetGoal.setGoalUserId(current_user_id);
                budgetGoal.setCategory(rs.getString("category"));
                budgetGoal.setBudgetAmount(rs.getDouble("budgetAmount"));
                budgetGoal.setStartDate(rs.getDate("startDate").toLocalDate());
                budgetGoal.setEndDate(rs.getDate("endDate").toLocalDate());
                budgetGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));

                budgetGoalsList.add(budgetGoal);
            }
            rs.close();

        } catch (SQLException e) {
            System.out.println("Failed to retrieve goals!!!");
            e.printStackTrace();
        }
        return budgetGoalsList;
    }

    public void removeBudgetByName(String budgetCategory) {
        String sql = "DELETE FROM budgetgoals WHERE category = ?";

        try (Connection conn = connection.get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the category parameter to delete the correct budget
            stmt.setString(1, budgetCategory);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Budget removed successfully.");
            } else {
                System.out.println("No budget found with that name.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to remove budget...");
            e.printStackTrace();
        }
    }

    //get the budget goals category for bar chart
    public ArrayList<BudgetGoal> getBudgetGoalsByCategory(){
        ArrayList<BudgetGoal> budgetGoalsList = new ArrayList<>();

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Query to get all the budget goals for the current user
        String sql = "SELECT * FROM budgetgoals WHERE userId = ? ORDER BY category DESC LIMIT 10"; // Retrieves all goals, ordered by budgetId

        try (Connection conn = connection.get_Connection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, current_user_id);

            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    BudgetGoal budgetGoal = new BudgetGoal();

                budgetGoal.setGoalUserId(current_user_id);
                budgetGoal.setCategory(rs.getString("category"));
                budgetGoal.setBudgetAmount(rs.getDouble("budgetAmount"));
                budgetGoal.setStartDate(rs.getDate("startDate").toLocalDate());
                budgetGoal.setEndDate(rs.getDate("endDate").toLocalDate());
                budgetGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));

                budgetGoalsList.add(budgetGoal);

                }
                rs.close();
            }
         catch (SQLException e) {
            System.out.println("Failed to retrieve goals!!!");
            e.printStackTrace();
        }
        return budgetGoalsList;
        
    }

}
