package View;

import javax.swing.*;

import DAO.SavingsGoalDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.SavingsGoal; 

public class SavingsGUI extends JPanel {
    private JTextField nameField;
    private JTextField targetField;
    private JTextField deadlineField;
    private JTextField startingAmountField;
    private JCheckBox notificationCheckBox;
    private JButton addButton;
    private JButton backButton;
    public SavingsGoal savingsGoal;


    // Constructor for SavingsGUI
    public SavingsGUI() {

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Create the title label
        JLabel titleLabel = new JLabel("Savings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel descriptionLabel = new JLabel(" Create a new savings goal", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        // Add the title label to the top of the panel
        add(titleLabel, BorderLayout.NORTH);
        add(descriptionLabel, BorderLayout.PAGE_START);


        // Center Panel for input fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);


        //Name Field
        JLabel nameLabel = new JLabel("Goal Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(nameLabel, gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(nameField, gbc);


        // Target Amount Field
        JLabel targetLabel = new JLabel("Target Amount:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(targetLabel, gbc);
        targetField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(targetField, gbc);


        // Deadline Field
        JLabel deadlineLabel = new JLabel("Deadline:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(deadlineLabel, gbc);
        deadlineField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(deadlineField, gbc);

        // Starting Amount Field
        JLabel startingAmountLabel = new JLabel("Starting Amount:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(startingAmountLabel, gbc);
        startingAmountField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(startingAmountField, gbc);

        // Notification Checkbox
        notificationCheckBox = new JCheckBox("Receive Notifications");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(notificationCheckBox, gbc);
        add(centerPanel, BorderLayout.CENTER);
    

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the "Dashboard" panel when the button is clicked
                // Don't Delete
                Mainframe.cardLayout.show(Mainframe.mainPanel, "Dashboard");
            }
        });
        buttonPanel.add(backButton);

        addButton = new JButton("Create Savings");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSavings();
            }
        });
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    //Handel user's input - changes made here 
    private void handleSavings() {

        String name = nameField.getText();
        String target = targetField.getText();
        String deadline = deadlineField.getText();
        String startingAmount = startingAmountField.getText();
        boolean notificationsEnabled = notificationCheckBox.isSelected();


        // Check for empty fields
        if (name.isEmpty() || target.isEmpty() || deadline.isEmpty() || startingAmount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
        // Parse target and starting amount as doubles
        double targetAmount = Double.parseDouble(target);
        double startingAmountValue = Double.parseDouble(startingAmount);
        
        // Create a SavingsGoal object //TODO appUser instance 
        SavingsGoal newgGoal = new SavingsGoal(name, targetAmount, deadline, startingAmountValue, notificationsEnabled);
        SavingsGoalDAO.setCurrentSavingsGoal(newgGoal);

        // Show success message
        JOptionPane.showMessageDialog(this, "Savings goal created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Optionally, clear the fields
        nameField.setText("");
        targetField.setText("");
        deadlineField.setText("");
        startingAmountField.setText("");
        notificationCheckBox.setSelected(false);

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Please enter valid numbers for target and/or starting amounts.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
}

        /*
        // Validate numerical fields
        try {
            
            JOptionPane.showMessageDialog(this, null, "Success", JOptionPane.INFORMATION_MESSAGE);

        }
         catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for target and/or starting amounts.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} */


