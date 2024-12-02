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
    private JComboBox<String> deadlineComboBox; // Dropdown for deadline
    private JTextField startingAmountField;
    private JCheckBox notificationCheckBox;

    private JButton addButton;
    private JButton backButton;
    private JButton editButton;

    public SavingsGoal savingsGoal;

    private SavingsGoalDAO savingDao = new SavingsGoalDAO();

    // Constructor for SavingsGUI
    public SavingsGUI() {

        setLayout(new BorderLayout()); // Use BorderLayout for the main panel
        setBackground(Color.WHITE); // Set background to white

        // Create the title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(38, 120, 190)); // Modern blue
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create title label
        JLabel titleLabel = new JLabel("Savings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Create description label
        JLabel descriptionLabel = new JLabel("Create a New Savings Goal", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionLabel.setForeground(Color.WHITE);

        // Add labels to the title panel
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(descriptionLabel, BorderLayout.SOUTH);

        // Add title panel to the top
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel for input fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Name Field
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

        // Deadline Dropdown
        JLabel deadlineLabel = new JLabel("Deadline:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(deadlineLabel, gbc);
        String[] deadlineOptions = {"1 Week", "2 Weeks", "1 Month", "3 Months", "6 Months", "1 Year"};
        deadlineComboBox = new JComboBox<>(deadlineOptions);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(deadlineComboBox, gbc);

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
        notificationCheckBox.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(notificationCheckBox, gbc);
        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        backButton = new JButton("Back");
        backButton.setFocusable(false);
        buttonPanel.add(backButton);

        editButton = new JButton("Edit");
        backButton.setFocusable(false);
        buttonPanel.add(editButton);

        addButton = new JButton("Create Savings");
        addButton.setFocusable(false);
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Dashboard");
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSavings();
            }
        });
    }

    // Handle user's input
    private void handleSavings() {

        String name = nameField.getText();
        String target = targetField.getText();
        String deadline = (String) deadlineComboBox.getSelectedItem(); // Get selected deadline
        String startingAmount = startingAmountField.getText();
        boolean notificationsEnabled = notificationCheckBox.isSelected();

        // Check for empty fields
        if (name.isEmpty() || target.isEmpty() || startingAmount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Parse target and starting amount as doubles
            Double targetAmount = Double.parseDouble(target);
            Double startingAmountValue = Double.parseDouble(startingAmount);

            // Create a SavingsGoal object
            SavingsGoal newGoal = new SavingsGoal(name, targetAmount, deadline, startingAmountValue,
                    notificationsEnabled);

            // Add the saving goal into the database table
            savingDao.addGoalIntoDatabase(newGoal);
            SavingsGoalDAO.setCurrentSavingsGoal(newGoal);

            // Show success message
            JOptionPane.showMessageDialog(this, "Savings goal created successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Optionally, clear the fields
            nameField.setText("");
            targetField.setText("");
            deadlineComboBox.setSelectedIndex(0);
            startingAmountField.setText("");
            notificationCheckBox.setSelected(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for target and/or starting amounts.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
