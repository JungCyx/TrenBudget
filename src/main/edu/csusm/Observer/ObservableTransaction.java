package edu.csusm.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.csusm.Model.Transaction;

public class ObservableTransaction extends Observable{

    List<Observer> observers = new ArrayList<>();
    Transaction transaction;

    @Override
    protected List<Observer> getObservers() {
        return observers;
    }

    public void processTransaction(Transaction t){
        this.transaction = t;

    }

    public void notifyObservers(){
        for( Observer obs : this.observers ){
            obs.update(this.transaction);
        }
    }
    
}