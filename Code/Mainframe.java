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

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginGUI(), "Login");
        mainPanel.add(new DashboardGUI(), "Dashboard");
        mainPanel.add(new BudgetGUI(), "BudgetWindow");
        //TODO: add savingsButton and page

        cardLayout.show(mainPanel, "Login");

        add(mainPanel);
        setVisible(true);
    }
}
 