package edu.csusm.Observer;

import java.util.List;

/*
 * A super abstract class that will contain to addObserver and deleteObserver which are 
 * common function to Observable subclass. Will also contain getObservers which is an abstract
 * function forces subclass to defin a list of Observers
 */

public abstract class Observable {

    protected abstract List<Observer> getObservers();

    public void addObserver(Observer o){

        getObservers().add(o);
    }

    public boolean removeObserver(Observer o){
        return getObservers().remove(o);
    }
    
}


