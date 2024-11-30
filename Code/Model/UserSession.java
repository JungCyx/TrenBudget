package Model;

public class UserSession {
    // Private static instance to ensure only one instance of this class is used
    private static UserSession instance;
    private UserModel currentUser;  // Store the current user's data

    // Private constructor to prevent instantiation from outside
    private UserSession() {}

    // Get the instance of UserSession (Singleton Pattern)
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Set the current user
    public void setCurrentUser(UserModel user) {
        this.currentUser = user;
    }

    // Get the current user's information
    public UserModel getCurrentUser() {
        return this.currentUser;
    }

    // Check if user is logged in
    // public boolean CheckIfUserLogged

}

