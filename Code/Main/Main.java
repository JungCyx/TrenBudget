package Main;

import DAO.DAO;
import View.Mainframe;

public class Main {
    public static DAO connector = new DAO();

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        
        connector.createUserTable(); // Create a user table in the DB 
        connector.createSavingGoalTable(); // Creates a saving goal table in the DB

        Mainframe mainframe = new Mainframe(); // Retrieve the Mainframe bean
        mainframe.setVisible(true); // Display the Mainframe
 
    }
}


