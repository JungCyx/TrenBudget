
package DAO;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Model.Transaction;
import Model.UserSession;


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
   
    // The function retrives the transaction and returns transaction model <List> //highest to lowest return (latest value) 0,1
    public ArrayList<Transaction> getTransactionList(){
        // get the current user Id
        int current_user_id = UserSession.getInstance().getCurrentUser().getId();

        // Initialize an empty list to store the user's transactions
        ArrayList<Transaction> nextTransaction = new ArrayList<>(); 
        
        // Query the db to get the transaction for the current user 
        String sql = "SELECT * FROM usertransaction WHERE userId = ? ORDER BY id DESC"; // Example query

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
            System.out.println("Failed to retrive transactions!!!");
            e.printStackTrace();
        };
        return nextTransaction;
    }

    // The function retrives and return the latest transaction
    public Transaction getTransactionl(){

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
}
