package edu.csusm.Model;

public interface emaptyIF{

    // function that returns current UserModel 
    public UserModel getUser();

    /*
     * the function allows the instanse to return itself for Observer
     * to use
     */
    emaptyIF getInstance();
    
    // a function that formats an string for email notification
    String emailContant();

    boolean getNotificationsEnabled();

}