package edu.csusm.Model;

public interface emaptyIF{

    // function that returns current User Model 
    public UserModel getUser();

    /*
     * the function allows the instanse to return itself for Observer
     * to use
     */
    emaptyIF getInstance();

}