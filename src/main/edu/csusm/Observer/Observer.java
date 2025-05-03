package edu.csusm.Observer;

public interface Observer{

    // Observer update app 
    <T> void update(T DataObject);

    /*
     * the function allows the instanse to return itself for Observer
     * to use
     */
     Observer getInstance();

}
