package edu.csusm.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAO {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/trenbudget";

    static final String USER = "postgres";
    static final String PASS = "123";
    Connection conn = null;
 
    // returns a connection to the Database object each time its called 
    public Connection get_Connection() {

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                System.out.println("Connected to the DataBase...");
            } else {
                System.out.println("Connection Failed");
            }
        } catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
        return conn;
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS appUser ("
                + "id SERIAL PRIMARY KEY, "
                + "userName VARCHAR(225), "
                + "firstName VARCHAR(255), "
                + "lastName VARCHAR(255), "
                + "email VARCHAR(255), "
                + "password VARCHAR(255))";

        try (Connection conn = get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        String sql = "CREATE TABLE IF NOT EXISTS usergoals ("
                + "goalId SERIAL PRIMARY KEY, "
                + // Unique identifier for this table
                "userId INT NOT NULL, "
                + // Foreign key referencing appUser
                "goalName VARCHAR(255), "
                + "targetAmount Double PRECISION, "
                + "deadline VARCHAR(225), "
                + "startingAmount Double PRECISION, "
                + "notificationsEnabled BOOLEAN, "
                + "FOREIGN KEY (userId) REFERENCES appUser(id) ON DELETE CASCADE)";

        try (Connection conn = get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Saving Table created successfully...");

        } catch (SQLException e) {
            System.out.println("Faild To Create Saving Table...");
            e.printStackTrace();
        };
    }
//create budget Table

    public void createBudgetGoalTable() {
        String sql = "CREATE TABLE IF NOT EXISTS budgetgoals ("
                + "budgetId SERIAL PRIMARY KEY, "
                + // Unique identifier for this table
                "userId INT NOT NULL, "
                + // Foreign key referencing appUser
                "category VARCHAR(255), "
                + "budgetAmount Double PRECISION, "
                + "startDate Date, "
                + "endDate Date, "
                + "notificationsEnabled BOOLEAN, "
                + "FOREIGN KEY (userId) REFERENCES appUser(id) ON DELETE CASCADE)";

        try (Connection conn = get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Budget Table created successfully...");

        } catch (SQLException e) {
            System.out.println("Faild To Create Budget Table...");
            e.printStackTrace();
        };
    }

    public void createTransactionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS usertransaction ("
                + "transactionId SERIAL PRIMARY KEY, "
                + // Unique identifier for this table
                "userId INT NOT NULL, "
                + // Foreign key referencing appUser
                "type VARCHAR(255), "
                + "category VARCHAR(255), "
                + "amount DOUBLE PRECISION, "
                + "notificationsEnabled BOOLEAN, "
                + "FOREIGN KEY (userId) REFERENCES appUser(id) ON DELETE CASCADE"
                + ");";

        try (Connection conn = get_Connection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Transaction Table created successfully...");

        } catch (SQLException e) {
            System.out.println("Failed To Create Transaction Table...");
            e.printStackTrace();
        };
    }

    public void createMortgageTable(){
        String sql = "CREATE TABLE IF NOT EXISTS mortgage ("
                + "mortgageId SERIAL PRIMARY KEY, "
                + "userId INT NOT NULL, "
                + "principal DOUBLE PRECISION NOT NULL, "
                + "interestRate DOUBLE PRECISION NOT NULL, "
                + "termInYears INT NOT NULL, "
                + "downPayment DOUBLE PRECISION, "
                + "insurance DOUBLE PRECISION, "
                + "propertyTaxRate DOUBLE PRECISION, "
                + "FOREIGN KEY (userId) REFERENCES appUser(id) ON DELETE CASCADE"
                + ");";
        try {
            Connection conn = get_Connection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
            conn.close();
            stmt.close();
            System.out.println("Mortgage Table created successfully...");
        } catch (SQLException e) {
            System.out.println("Failed To Create Mortgage Table...");
            e.printStackTrace();
        }
    }
}
