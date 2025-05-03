package edu.csusm.Observer;


public class ClientRegisterObserver {

    public static void registerObserver(ObserverIF observer, Observable observable) {
        observable.addObserver(observer);
    }
}
