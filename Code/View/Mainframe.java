package View;

import javax.swing.*;
import java.awt.*;

public class Mainframe extends JFrame {
    public static CardLayout cardLayout;
    public static JPanel mainPanel;

    public Mainframe() {

        setTitle("TrenBudget");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 250);
        setLocationRelativeTo(null); // Center the window

        // Test the controller

        // Setup layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add views
        mainPanel.add(new LoginGUI(), "Login");
        mainPanel.add(new CreateAccountGUI(), "Register");
        mainPanel.add(new DashboardGUI(), "Dashboard");
        mainPanel.add(new BudgetGUI(), "BudgetWindow");
        mainPanel.add(new SavingsGUI(), "Savings");
        cardLayout.show(mainPanel, "Login");

        add(mainPanel);
        setVisible(true);
    }
}


 