package Main.Controller;


public class UserController {

    public UserController() {}

    public boolean authenticateInput(String username, String password) {
        return "user".equals(username) && "pass".equals(password);
    }


}
