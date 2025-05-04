package edu.csusm.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.csusm.Model.BudgetGoal;

public class ObservableBugetGoal extends Observable{

    private static final ObservableBugetGoal instance = new ObservableBugetGoal();

    private final List<ObserverIF> observers = new ArrayList<>(); 
    private BudgetGoal budgetGoal;

    // Private constructor so no other class can instantiate it
    private ObservableBugetGoal() {}

    // Public static method to get the single instance
    public static ObservableBugetGoal getInstance() {
        return instance;
    }
    
    // simply return the list of observers 
    @Override
    protected List<ObserverIF> getObservers() {
        return observers;
    }

    // assigen data object 
    public void processBudgetGoal(BudgetGoal b) {
        this.budgetGoal = b;
        notifyObservers();
    }

    // notifies all observers 
    public void notifyObservers() {
        for (ObserverIF obs : this.observers) {
            obs.update(this.budgetGoal);
        }
    }
    
}
