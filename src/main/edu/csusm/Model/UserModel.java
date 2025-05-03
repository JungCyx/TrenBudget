package edu.csusm.Model;

public class UserModel implements emaptyIF{
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int id;
    
    //Create account for login 
    public UserModel(String userName, String firstName, String lastName, String email, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    //Retrieve user info from DB 
    public UserModel(int id, String userName, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    //User Getters & Setters 
    public UserModel() {
        
    }

    public UserModel(int i, String string) {
        //TODO Auto-generated constructor stub
    }
    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel getUser(){
        return this;
    }

    @Override
    public emaptyIF getInstance() {
    return this;
    }

    @Override
    public String emailContant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
