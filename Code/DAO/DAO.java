package DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO{
   
   static final String DB_URL = "jdbc:postgresql://localhost:5432/trenbudget";
   static final String USER = "postgres";
   static final String PASS = "123";
   Connection conn = null;

   // returns a connection to the Database object each time its called 
   public Connection get_Connection() {
   
      try{ 
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
      if (conn != null){
         System.out.println("Connected to the DataBase...");
      }
      else{
         System.out.println("Connection Failed");
      }
   } catch (Exception e){
      System.out.println("Connection Failed");
      e.printStackTrace();
   }
   return conn;
   }

   public void createUserTable() {
      String sql = "CREATE TABLE IF NOT EXISTS appUser (" +
                   "id SERIAL PRIMARY KEY, " +
                   "userName VARCHAR(225), " +
                   "firstName VARCHAR(255), " +
                   "lastName VARCHAR(255), " +
                   "email VARCHAR(255), " +
                   "password VARCHAR(255))";
  
      try (Connection conn = get_Connection();
           PreparedStatement stmt = conn.prepareStatement(sql)) {
  
          stmt.executeUpdate();
          conn.close();
          stmt.close();
          System.out.println("User Table created successfully...");
  
      } catch (SQLException e) {
         System.out.println("Faild To Create User Table...");
          e.printStackTrace();    
      };
  }

  public void createSavingGoalTable() {
   String sql = "CREATE TABLE IF NOT EXISTS userGoals (" +
             "goalId SERIAL PRIMARY KEY, " +  // Unique identifier for this table
             "userId INT NOT NULL, " +        // Foreign key referencing appUser
             "goalName VARCHAR(255), " +
             "targetAmount FLOAT, " +
             "deadline VARCHAR(225), " +
             "startingAmount FLOAT, " +
             "notificationsEnabled BOOLEAN, " +
             "FOREIGN KEY (userId) REFERENCES appUser(id) ON DELETE CASCADE)";

   try (Connection conn = get_Connection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

       stmt.executeUpdate();
       conn.close();
       stmt.close();
       System.out.println("Saving Table created successfully...");

   } catch (SQLException e) {
      System.out.println("Faild To Create Saving Table...");
       e.printStackTrace();    
   };
}

  
}


