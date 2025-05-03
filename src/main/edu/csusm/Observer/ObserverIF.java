package edu.csusm.Observer;

// the interface is implemented by Observers for example EmailNotification the logic for update is left for the class to defin 

// OwO
public interface ObserverIF{

    // Observer update app 
    <T> void update(T DataObject);

}
