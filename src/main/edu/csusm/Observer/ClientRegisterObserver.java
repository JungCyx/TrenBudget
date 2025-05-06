package edu.csusm.Observer;

// A class to simply add to the Observers to Observables
public class ClientRegisterObserver {

    // a static function seemed to be a good desgin 
    public static void registerObserver(ObserverIF observer, Observable observable) {
        observable.addObserver(observer);
    }
}
