package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionGUI extends JPanel {

    // Constructor for SavingsGUI
    public TransactionGUI() {

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Create the title label
        JLabel titleLabel = new JLabel("Welcome to Transaction", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Add the title label to the top of the panel
        add(titleLabel, BorderLayout.NORTH);

        // Create the "Back" button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Switch to the "Dashboard" panel when the button is clicked
                // Don't Delete
                Mainframe.cardLayout.show(Mainframe.mainPanel, "Dashboard");
            }
        });

        // Add the bottom panel to the bottom of the main panel (BorderLayout.SOUTH)
        add(backButton, BorderLayout.SOUTH);
    }
}


