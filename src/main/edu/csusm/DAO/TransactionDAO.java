package edu.csusm.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.csusm.Model.Transaction;
import edu.csusm.Model.UserSession;

public class TransactionDAO{

    public static Transaction currentGoal; // this is instance of a savings goal which will be updating in the dashboard
    DAO connection = new DAO();


    //get the current savings goal
    public static Transaction getCurrentGoal(){
        return currentGoal;
    }

    //set the current savings goal
    public static void setNewTransaction(Transaction goal) {
         currentGoal = goal;
    }

    // The function Insert transaction into the Database table 
    public void addTransactionIntoDatabase(Transaction goal){
        String sql = "INSERT INTO usertransaction (userId, type, category, amount, notificationsEnabled) VALUES (?, ?, ?, ?, ?)";

        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        try (Connection conn = connection.get_Connection();) {

            PreparedStatement stmt = conn.prepareStatement(sql);
                
            stmt.setInt(1, current_user_id);
            stmt.setString(2, goal.getType());
            stmt.setString(3, goal.getCategory());
            stmt.setDouble(4, goal.getAmount());
            stmt.setBoolean(5, goal.getNotificationsEnabled());

            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Added into table successfully...");
    
        } catch (SQLException e) {
            System.out.println("Failed add to table...");
            e.printStackTrace();
        };
    }
   
    // In TransactionDAO.java
public ArrayList<Transaction> getTransactionList() {
    // get the current user Id
    int current_user_id = UserSession.getInstance().getCurrentUser().getId();

    // Initialize an empty list to store the user's transactions
    ArrayList<Transaction> nextTransaction = new ArrayList<>(); 
    
    // Query the db to get the transaction for the current user 
    // Replace "id" with "transactionId" which is the correct column name in your table
    String sql = "SELECT * FROM usertransaction WHERE userId = ? ORDER BY transactionId DESC"; // Fixed query

    try(Connection conn = connection.get_Connection();){
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, current_user_id);

        // Database results 
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            Transaction currGoal = new Transaction();
            currGoal.setGoalUserId(current_user_id);
            currGoal.setType(rs.getString("type"));
            currGoal.setCategory(rs.getString("category"));
            currGoal.setAmount(rs.getDouble("amount"));
            currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            nextTransaction.add(currGoal);
        }
        conn.close();
        rs.close();
    } catch (SQLException e) {
        System.out.println("Failed to retrieve transactions!!!");
        e.printStackTrace();
    }
    return nextTransaction;
}

    // The function retrives and return the latest transaction
    public Transaction getTransaction(){

        Transaction currGoal = null;

        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
        
        // Query the db to get the transaction for the current user 
        String sql = "SELECT * FROM usertransaction WHERE userId = ? ORDER BY transactionId DESC LIMIT 1"; // Example query

        try(Connection conn = connection.get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, current_user_id);

            // Database results 
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                currGoal = new Transaction();

                currGoal.setGoalUserId(current_user_id);
                currGoal.setType(rs.getString("type"));
                currGoal.setCategory(rs.getString("category"));
                currGoal.setAmount(rs.getDouble("amount"));;
                currGoal.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrive transaction!!!");
            e.printStackTrace();
        }
        return currGoal;
    }

    //The function gets the withdraw transactions
    public ArrayList<Transaction> getWithdrawTransactions() {
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        // Initialize an empty list to store the user's withdrawal transactions
        ArrayList<Transaction> transactionList = new ArrayList<>();
    
        // Query to fetch the last 50 withdrawal transactions
        String sql = "SELECT * FROM usertransaction WHERE userId = ? AND type = 'Withdrawal' ORDER BY transactionId DESC LIMIT 3";
    
        try (Connection conn = connection.get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            // Set parameters in the prepared statement
            stmt.setInt(1, current_user_id);
    
            // Database results
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setGoalUserId(current_user_id);
                transaction.setType(rs.getString("type"));
                transaction.setCategory(rs.getString("category"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
    
                transactionList.add(transaction);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve withdrawal transactions!");
            e.printStackTrace();
        }
    
        return transactionList;
    }

     //The function gets the deposits transactions
     public ArrayList<Transaction> getDepositTransactions() {
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();
    
        // Initialize an empty list to store the user's withdrawal transactions
        ArrayList<Transaction> depositList = new ArrayList<>();
    
        // Query to fetch the deposit transaction
        String sql = "SELECT * FROM usertransaction WHERE userId = ? AND type = 'Deposit' ORDER BY transactionId";
    
        try (Connection conn = connection.get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            // Set parameters in the prepared statement
            stmt.setInt(1, current_user_id);
    
            // Database results
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Transaction depositTransaction = new Transaction();
                depositTransaction.setGoalUserId(current_user_id);
                depositTransaction.setType(rs.getString("type"));
                depositTransaction.setCategory(rs.getString("category"));
                depositTransaction.setAmount(rs.getDouble("amount"));
                depositTransaction.setNotificationsEnabled(rs.getBoolean("notificationsEnabled"));
    
                depositList.add(depositTransaction);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to retrieve withdrawal transactions!");
            e.printStackTrace();
        }
    
        return depositList;
    }



}