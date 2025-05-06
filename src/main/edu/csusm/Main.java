package edu.csusm;

import edu.csusm.DAO.DAO;
import edu.csusm.Observer.ClientRegisterObserver;
import edu.csusm.Observer.Observable;
import edu.csusm.Observer.ObservableBugetGoal;
import edu.csusm.Observer.ObservableSavingGoal;
import edu.csusm.Observer.ObservableTransaction;
import edu.csusm.Observer.ObserverEmailNotification;
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

        Observable transactionObservable = ObservableTransaction.getInstance(); // create an obervable for transaction logic 
        Observable bugetGoalObservable = ObservableBugetGoal.getInstance();
        Observable savingGoalObservable = ObservableSavingGoal.getInstance();

        

        ObserverEmailNotification eamilObserver = new ObserverEmailNotification(); // create email observaer 

        ClientRegisterObserver.registerObserver(eamilObserver, transactionObservable); // add observer to Observable 
        ClientRegisterObserver.registerObserver(eamilObserver, bugetGoalObservable); // add observer to Observable 
        ClientRegisterObserver.registerObserver(eamilObserver, savingGoalObservable); // add observer to Observable 

        LoginGUI mainframe = new LoginGUI(); // Retrieve the Mainframe bean
        mainframe.setVisible(true); // Display the Mainframe
 
    }
}


