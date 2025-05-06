package edu.csusm.Observer;

import java.util.List;

/*
 * A super abstract class that will contain to addObserver and deleteObserver which are 
 * common function to Observable subclass. Will also contain getObservers which is an abstract
 * function forces subclass to defin a list of Observers
 */

 public abstract class Observable {

    protected abstract List<ObserverIF> getObservers();
    // add the observer to the obervable list 
    public void addObserver(ObserverIF o) {
        getObservers().add(o);
    }
    // remove the observer from the obervable list 
    public boolean removeObserver(ObserverIF o) {
        return getObservers().remove(o);
    }
}



