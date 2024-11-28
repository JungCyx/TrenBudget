package Main.Controller;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DAO.DAO;
import Model.UserModel;


public class UserController {

    private UserModel userModel;
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


    // The function maps the user input filed to the userModel and return the mapped object of user
    public UserModel mapUser(String userName, String firstName, String lastName, String email, String password){
        return new UserModel(userName, firstName, lastName, email, password );
    }

   public boolean addUserToDataBase(UserModel user){
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
    
    return false;
   }


}
