package edu.csusm;

import edu.csusm.DAO.DAO;
import edu.csusm.View.LoginGUI;

public class Main {
    public static DAO connector = new DAO();

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        System.out.println("hello world");
        
        connector.createUserTable(); // Create a user table in the DB 
        connector.createSavingGoalTable(); // Creates a saving goal table in the DB
        connector.createBudgetGoalTable();
        connector.createTransactionTable();//creates a transaction table in DB

        LoginGUI mainframe = new LoginGUI(); // Retrieve the Mainframe bean
        mainframe.setVisible(true); // Display the Mainframe
 
    }
}


