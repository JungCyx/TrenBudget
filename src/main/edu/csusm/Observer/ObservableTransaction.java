package edu.csusm.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.csusm.Model.Transaction;

public class ObservableTransaction extends Observable {

    private static final ObservableTransaction instance = new ObservableTransaction();

    private final List<ObserverIF> observers = new ArrayList<>();
    private Transaction transaction;

    // Private constructor so no other class can instantiate it
    private ObservableTransaction() {}

    // Public static method to get the single instance
    public static ObservableTransaction getInstance() {
        return instance;
    }

    @Override
    protected List<ObserverIF> getObservers() {
        return observers;
    }

    public void processTransaction(Transaction t) {
        this.transaction = t;
        notifyObservers();
    }

    public void notifyObservers() {
        for (ObserverIF obs : this.observers) {
            obs.update(this.transaction);
        }
    }
}
