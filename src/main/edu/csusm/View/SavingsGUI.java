package edu.csusm.View;

import edu.csusm.Controller.UserController;
import edu.csusm.DAO.SavingsGoalDAO;
import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.UserModel;
import edu.csusm.Observer.ObservableSavingGoal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SavingsGUI extends JPanel { 

    

    private JTextField nameField;
    private JTextField targetField;
    private JComboBox<String> deadlineComboBox; // Dropdown for deadline
    private JTextField startingAmountField;
    private JCheckBox notificationCheckBox;

    // Component for adding funds to existing goal
    private JComboBox<String> existingGoalsDropdown;
    private JTextField additionalFundsField;
    private JButton addFundsButton;

    private JButton addButton;
    private JButton backButton;
    private JButton editButton;

    private UserController controller = new UserController();

    public SavingsGoal savingsGoal;

    private SavingsGoalDAO savingDao = new SavingsGoalDAO();
    
    // Constants for card layout
    private static final String CREATE_PANEL = "CreatePanel";
    private static final String ADD_FUNDS_PANEL = "AddFundsPanel";
    
    // CardLayout to switch between create and add funds panels
    private CardLayout cardLayout;
    private JPanel cardsPanel;

    // Constructor for SavingsGUI
    public SavingsGUI() {
        controller = new UserController();

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
        JLabel descriptionLabel = new JLabel("Manage Your Savings Goals", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionLabel.setForeground(Color.WHITE);

        // Add labels to the title panel
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(descriptionLabel, BorderLayout.SOUTH);

        // Add title panel to the top
        add(titlePanel, BorderLayout.NORTH);
        
        // Create a panel for the mode selection buttons
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        modePanel.setBackground(Color.WHITE);
        
        JButton newGoalButton = new JButton("Create New Goal");
        JButton addToExistingButton = new JButton("Add to Existing Goal");
        
        modePanel.add(newGoalButton);
        modePanel.add(addToExistingButton);
        
        add(modePanel, BorderLayout.NORTH);
        
        // Create card layout and panel for swapping between modes
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);
        cardsPanel.setBackground(Color.WHITE);
        
        // Create the panels for each mode
        JPanel createPanel = createNewGoalPanel();
        JPanel addFundsPanel = createAddFundsPanel();
        
        // Add panels to the card layout
        cardsPanel.add(createPanel, CREATE_PANEL);
        cardsPanel.add(addFundsPanel, ADD_FUNDS_PANEL);
        
        // Add the cards panel to the main panel
        add(cardsPanel, BorderLayout.CENTER);
        
        // Show the create panel by default
        cardLayout.show(cardsPanel, CREATE_PANEL);
        
        // Add action listeners for mode buttons
        newGoalButton.addActionListener(e -> cardLayout.show(cardsPanel, CREATE_PANEL));
        addToExistingButton.addActionListener(e -> {
            // Refresh the dropdown with latest goals
            refreshGoalsDropdown();
            cardLayout.show(cardsPanel, ADD_FUNDS_PANEL);
        });
        
        // Button Panel for Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        
        backButton = new JButton("Back to Dashboard");
        backButton.setFocusable(false);
        backButton.addActionListener(e -> LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Dashboard"));
        
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Create the panel for adding a new savings goal
    private JPanel createNewGoalPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Panel Label
        JLabel panelLabel = new JLabel("Create a New Savings Goal", JLabel.CENTER);
        panelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelLabel, gbc);
        
        // Name Field
        JLabel nameLabel = new JLabel("Goal Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(nameLabel, gbc);
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nameField, gbc);

        // Target Amount Field
        JLabel targetLabel = new JLabel("Target Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(targetLabel, gbc);
        targetField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(targetField, gbc);

        // Deadline Dropdown
        JLabel deadlineLabel = new JLabel("Deadline:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(deadlineLabel, gbc);
        String[] deadlineOptions = {"1 Week", "2 Weeks", "1 Month", "3 Months", "6 Months", "1 Year"};
        deadlineComboBox = new JComboBox<>(deadlineOptions);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(deadlineComboBox, gbc);

        // Starting Amount Field
        JLabel startingAmountLabel = new JLabel("Starting Amount:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(startingAmountLabel, gbc);
        startingAmountField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(startingAmountField, gbc);

        // Notification Checkbox
        notificationCheckBox = new JCheckBox("Receive Notifications");
        notificationCheckBox.setBackground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(notificationCheckBox, gbc);
        
        // Create Button
        addButton = new JButton("Create Savings Goal");
        addButton.setFocusable(false);
        addButton.addActionListener(e -> handleSavings());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);
        
        return panel;
    }
    
    // Create the panel for adding funds to an existing goal
    private JPanel createAddFundsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Panel Label
        JLabel panelLabel = new JLabel("Add Funds to Existing Savings Goal", JLabel.CENTER);
        panelLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(panelLabel, gbc);
        
        // Existing Goals Dropdown
        JLabel existingGoalsLabel = new JLabel("Select Goal:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(existingGoalsLabel, gbc);
        
        existingGoalsDropdown = new JComboBox<>(getSavingsGoalNames());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(existingGoalsDropdown, gbc);
        
        // Additional Funds Field
        JLabel additionalFundsLabel = new JLabel("Additional Funds:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(additionalFundsLabel, gbc);
        
        additionalFundsField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(additionalFundsField, gbc);
        
        // Current Progress Display
        JLabel currentProgressLabel = new JLabel("Current Progress:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(currentProgressLabel, gbc);
        
        JLabel progressInfoLabel = new JLabel("Select a goal to see progress");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(progressInfoLabel, gbc);
        
        // Add action listener to dropdown to update progress info
        existingGoalsDropdown.addActionListener(e -> {
            String selectedGoalName = (String) existingGoalsDropdown.getSelectedItem();
            if (selectedGoalName == null || selectedGoalName.equals("No Savings Goals Found")) {
                progressInfoLabel.setText("No goal selected");
                return;
            }
            
            SavingsGoal selectedGoal = findGoalByName(selectedGoalName);
            if (selectedGoal != null) {
                double currentAmount = selectedGoal.getStartingAmount();
                double targetAmount = selectedGoal.getTargetAmount();
                double percentage = (currentAmount / targetAmount) * 100;
                
                progressInfoLabel.setText(String.format("$%.2f of $%.2f (%.1f%%)", 
                        currentAmount, targetAmount, percentage));
            }
        });
        
        // Add Funds Button
        addFundsButton = new JButton("Add Funds");
        addFundsButton.setFocusable(false);
        addFundsButton.addActionListener(e -> handleAddFunds());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addFundsButton, gbc);
        
        return panel;
    }
    
    // Find a savings goal by name
    private SavingsGoal findGoalByName(String name) {
        ArrayList<SavingsGoal> goals = savingDao.getSavingsGoalsList();
        for (SavingsGoal goal : goals) {
            if (goal.getName().equals(name)) {
                return goal;
            }
        }
        return null;
    }
    
    // Handle adding funds to an existing goal
    private void handleAddFunds() {
        String selectedGoalName = (String) existingGoalsDropdown.getSelectedItem();
        String additionalFundsText = additionalFundsField.getText();
        
        if (selectedGoalName == null || selectedGoalName.equals("No Savings Goals Found")) {
            JOptionPane.showMessageDialog(this, "Please select a valid savings goal.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (additionalFundsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount to add.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            double additionalFunds = Double.parseDouble(additionalFundsText);
            
            if (additionalFunds <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Find the selected goal
            SavingsGoal selectedGoal = findGoalByName(selectedGoalName);
            
            if (selectedGoal != null) {
                // Update the goal's starting amount
                double newAmount = selectedGoal.getStartingAmount() + additionalFunds;
                selectedGoal.setStartingAmount(newAmount);
                
                // Ensure the user model is set before notification
                if (selectedGoal.getUser() == null) {
                    // Set the current user from the controller

                    UserModel currentUser = controller.getUser();
                    selectedGoal = controller.mapSavingGoalWithFactory(
                        selectedGoal.getName(), 
                        selectedGoal.getTargetAmount(), 
                        selectedGoal.getDeadLine(), 
                        newAmount, 
                        selectedGoal.getNotificationsEnabled()
                    );
                }
                
                // Update in database
                savingDao.updateSavingsGoal(selectedGoal);
                
                // Notify observers
                ObservableSavingGoal s = ObservableSavingGoal.getInstance();
                s.processSavingGoal(selectedGoal);
                
                // Show success message
                JOptionPane.showMessageDialog(this, 
                        String.format("Successfully added $%.2f to %s. New balance: $%.2f", 
                                additionalFunds, selectedGoalName, newAmount), 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                
                // Clear the additional funds field
                additionalFundsField.setText("");
                
                // Trigger dropdown action to update progress info
                existingGoalsDropdown.setSelectedItem(selectedGoalName);
            } else {
                JOptionPane.showMessageDialog(this, "Could not find the selected goal.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for additional funds.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Refresh the goals dropdown
    private void refreshGoalsDropdown() {
        existingGoalsDropdown.setModel(new DefaultComboBoxModel<>(getSavingsGoalNames()));
    }
    
    // Get array of savings goal names for the dropdown
    private String[] getSavingsGoalNames() {
        ArrayList<SavingsGoal> goals = savingDao.getSavingsGoalsList();
        if (goals == null || goals.isEmpty()) {
            return new String[]{"No Savings Goals Found"};
        }
        
        String[] goalNames = new String[goals.size()];
        for (int i = 0; i < goals.size(); i++) {
            goalNames[i] = goals.get(i).getName();
        }
        return goalNames;
    }

    // Handle creating a new savings goal
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

            //New Factory code
            SavingsGoal newGoal = controller.mapSavingGoalWithFactory(name, targetAmount, deadline, startingAmountValue, notificationsEnabled);

            ObservableSavingGoal s = ObservableSavingGoal.getInstance();
            s.processSavingGoal(newGoal);
            
            // Add the saving goal into the database table
            savingDao.addGoalIntoDatabase(newGoal);
            SavingsGoalDAO.setCurrentSavingsGoal(newGoal);

            // Show success message
            JOptionPane.showMessageDialog(this, "Savings goal created successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear the fields
            nameField.setText("");
            targetField.setText("");
            deadlineComboBox.setSelectedIndex(0);
            startingAmountField.setText("");
            notificationCheckBox.setSelected(false);
            
            // Refresh the dropdown in the add funds panel
            refreshGoalsDropdown();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for target and/or starting amounts.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}