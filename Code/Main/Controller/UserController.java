package Main.Controller;

public class UserController {

    public UserController(){}

    public boolean authenticateInput(String username, String password) {
        return "user".equals(username) && "pass".equals(password);
    }

    public boolean createUser(String username, String password, String firstName, String lastName, String email) {

        return false;
    };
}
