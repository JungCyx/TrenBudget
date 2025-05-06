package edu.csusm.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.csusm.DAO.DAO;
import edu.csusm.Factory.BudgetGoalFactory;
import edu.csusm.Factory.SavingsFactory;
import edu.csusm.Factory.SavingsGoalFactory;
import edu.csusm.Factory.TransactionFactory;
import edu.csusm.Model.BudgetGoal;
import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.Transaction;
import edu.csusm.Model.UserModel;
import edu.csusm.Model.UserSession;


public class UserController {

    DAO connection = new DAO();
    

    public UserController() {}

    public boolean authenticateUser(String username, String password) {
        // SQL query to find the user by username and password
        String sql = "SELECT * FROM appuser WHERE userName = ? AND password = ?";

        try (Connection conn = connection.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the query
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query and check if the result exists
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {

                    // Create the User Instences
                    createUserInstences(rs);

                    // User found, return true
                    System.out.println("User authenticated successfully.");
                    return true;
                } else {
                    // No matching user found
                    System.out.println("Invalid username or password.");
                    return false;
                }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error searching for a user...");
        }

        return false;
    }

    // create an instaences of the logged usere
    public void createUserInstences(ResultSet rs) throws SQLException{

        UserModel current_user = mapDB_toUser(rs);

        UserSession.getInstance().setCurrentUser(current_user);
    }

    // mapps Database respones to User Model
    public UserModel mapDB_toUser(ResultSet rs) throws SQLException{
        // create new user
        UserModel current_user = new UserModel();

        // mapp user model to DB reponse rs
        current_user.setId(rs.getInt("id"));
        current_user.setUserName(rs.getString("userName")); 
        current_user.setFirstName(rs.getString("firstName")); 
        current_user.setLastName(rs.getString("lastName"));
        current_user.setEmail(rs.getString("email"));
        current_user.setPassword(rs.getString("password"));

        return current_user;
    }

    // The function maps the user input filed to the userModel and return the mapped object of user
    public UserModel mapUser(String userName, String firstName, String lastName, String email, String password){
        return new UserModel(userName, firstName, lastName, email, password );
    }

    public BudgetGoal mapBudgetGoal(String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled){
        return new BudgetGoal(category, budgetAmount, startDateString,endDateString,notificationsEnabled);
    }

     //Function to map the Transaction input into Transaction model and return it
     public Transaction mapTransaction(String type, String category, double amount, boolean notificationsEnabled){
        return new Transaction(type, category, amount, notificationsEnabled);
    }

    //Refactored Portion for Factory 
    public Transaction mapTransactionWithFactory(String type, String category, double amount, boolean notificationsEnabled) {
        SavingsFactory factory = new TransactionFactory(type, category, amount, notificationsEnabled);
        return (Transaction) factory.create();
    }

    public SavingsGoal mapSavingGoalWithFactory(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled) {
        SavingsFactory factory = new SavingsGoalFactory(name, targetAmount, deadline, startingAmount, notificationsEnabled);
        return (SavingsGoal) factory.create();
    }

    public BudgetGoal mapBudgetGoalWithFactory(String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled) {
        SavingsFactory factory = new BudgetGoalFactory(category, budgetAmount, startDateString, endDateString, notificationsEnabled);
        return (BudgetGoal) factory.create();
    }
    
    /* 
    public BudgetGoal mapBudgetGoalwithUser(String category, double budgetAmount, String startDateString, String endDateString, boolean notificationsEnabled, UserModel user){
        return new BudgetGoal(category, budgetAmount, startDateString,endDateString,notificationsEnabled, user);
    } */

    /* 
    public SavingsGoal mapSavingGoalwithUser(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled, UserModel appUser){
        return new SavingsGoal(name, targetAmount, deadline, startingAmount, notificationsEnabled, appUser);
    }*/

    /* 
    public Transaction mapTransactionWithUser(String type, String category, double amount, boolean notificationsEnabled, UserModel user){
        return new Transaction(type, category, amount, notificationsEnabled, user);
    }*/

    public SavingsGoal mapSavingGoal(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled){
        return new SavingsGoal(name, targetAmount, deadline, startingAmount, notificationsEnabled);
    }

    public SavingsGoal mapSavingGoalwithUser(String name, Double targetAmount, String deadline, Double startingAmount, boolean notificationsEnabled, UserModel appUser){
        return new SavingsGoal(name, targetAmount, deadline, startingAmount, notificationsEnabled, appUser);
    }

    // The Function return the current logged in user model
    public UserModel getUser(){
        return UserSession.getInstance().getCurrentUser();
    } 

   // The Function adds a new user to the data base 
   public void addUserToDataBase(UserModel user){
    String sql = "INSERT INTO appuser (userName, firstName, lastName, email, password) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection conn = connection.get_Connection();) {

        PreparedStatement stmt = conn.prepareStatement(sql);
            
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getFirstName());
        stmt.setString(3, user.getLastName());
        stmt.setString(4, user.getEmail());
        stmt.setString(5, user.getPassword());

        stmt.executeUpdate();
        conn.close();
        stmt.close();
        System.out.println("Added into Table successfully...");
  
      } catch (SQLException e) {
        System.out.println("Faild add to table...");
          e.printStackTrace();
      };
   }

}
