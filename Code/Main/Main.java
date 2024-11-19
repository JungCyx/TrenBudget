package Main;

import View.Mainframe;


public class Main {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        // Retrieve the Mainframe bean
        Mainframe mainframe = new Mainframe();
        mainframe.setVisible(true); // Display the Mainframe
    }
}


