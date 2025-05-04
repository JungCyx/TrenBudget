package edu.csusm.Observer;

import java.util.ArrayList;
import java.util.List;

import edu.csusm.Model.SavingsGoal;

public class ObservableSavingGoal extends Observable{

    private static final ObservableSavingGoal instance = new ObservableSavingGoal();

    private final List<ObserverIF> observers = new ArrayList<>();
    private SavingsGoal savingGoal;
    
    private ObservableSavingGoal(){}

    public static ObservableSavingGoal getInstance(){
        return instance;
    }

    @Override
    protected List<ObserverIF> getObservers() {
        return observers;
    }

    // assigen data object 
    public void processSavingGoal(SavingsGoal s) {
        this.savingGoal = s;
        notifyObservers();
    }

    // notifies all observers 
    public void notifyObservers() {
        for (ObserverIF obs : this.observers) {
            obs.update(this.savingGoal);
        }
    }
    
}
