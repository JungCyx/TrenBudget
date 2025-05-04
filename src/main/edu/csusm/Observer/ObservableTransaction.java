package edu.csusm.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.csusm.Model.Transaction;

/*
 * ObservableTransaction extends abstact Observable class which makes the class simply responsible to alert all 
 * added Observers just like ObserverEmailNotification 
 */
public class ObservableTransaction extends Observable {

    private static final ObservableTransaction instance = new ObservableTransaction();

    // an list contaning all the observers that needs to be updated/alerted when change happens to Transaction 
    private final List<ObserverIF> observers = new ArrayList<>();
    private Transaction transaction;

    // Private constructor so no other class can instantiate it
    private ObservableTransaction() {}

    // Public static method to get the single instance
    public static ObservableTransaction getInstance() {
        return instance;
    }
    
    // simply return the list of observers 
    @Override
    protected List<ObserverIF> getObservers() {
        return observers;
    }

    // assigen data object 
    public void processTransaction(Transaction t) {
        this.transaction = t;
        notifyObservers();
    }

    // notifies all observers 
    public void notifyObservers() {
        for (ObserverIF obs : this.observers) {
            obs.update(this.transaction);
        }
    }
}
