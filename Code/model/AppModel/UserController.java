package model.AppModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping
    public String getUsers() {
        return "Hello World";
    };
}
