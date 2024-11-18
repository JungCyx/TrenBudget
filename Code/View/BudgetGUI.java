package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetGUI extends JPanel{
     
    //constructor for SavingsGUI
    BudgetGUI(){

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        JLabel titleLabel = new JLabel("Welcome to Budget", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
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
