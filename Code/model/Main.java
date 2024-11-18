package model;
import Swing.Mainframe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); //Disables headless

        // display the App
        SpringApplication.run(Main.class, args);

        Mainframe mainframe = new Mainframe();

    }

}
