import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.*;

public class DashboardGUI extends JPanel {
    public DashboardGUI() {
        setLayout(new BorderLayout());
        JLabel dashboardLabel = new JLabel("DashBoard", JLabel.CENTER);
        dashboardLabel.setFont(new Font("Ariel", Font.BOLD, 20));
        add(dashboardLabel, BorderLayout.CENTER);

    }
}
