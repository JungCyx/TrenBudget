package View;

import javax.swing.*;

import DAO.BudgetGoalDAO;
import Main.Controller.UserController;
import Model.BudgetGoal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class BudgetGUI extends JPanel {
    private JComboBox<String> categoryField;
    private JTextField amountField;
    private JTextField durationField;
    private JCheckBox notificationCheckBox;
    private JButton addButton;
    private JButton backButton;
     
    //constructor for SavingsGUI
    BudgetGUI(){

    // Constructor for BudgetGUI
        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Title and description
        JLabel titleLabel = new JLabel("Budget", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel descriptionLabel = new JLabel("Add a new budget", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        add(titleLabel, BorderLayout.NORTH);
        add(descriptionLabel, BorderLayout.PAGE_START);

        // Center Panel to display the input fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Dropdown for Categories
        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(categoryLabel, gbc);

        categoryField = new JComboBox<>(new String[] {
                "Mortgage", "Rent", "Property Taxes", "Household Repairs", "HOA Fees",
                "Transportation", "Car Payment", "Car Warranty", "Gas", "Tires",
                "Car Maintenance", "Parking Fees", "Car Repairs", "DMV Fees",
                "Groceries", "Restaurants", "Pet Food", "Electricity", "Water",
                "Garbage", "Phones", "Cable", "Internet", "Apparel", "Healthcare",
                "Dental Care", "Health Insurance", "Homeowner’s Insurance",
                "Auto Insurance", "Life Insurance", "Household Items/Supplies",
                "Gym Memberships", "Salon Services", "Cosmetics", "Babysitter",
                "Subscriptions", "Personal Loans", "Student Loans", "Credit Cards",
                "Retirement Fund", "Investing", "Emergency Fund", "Big Purchases",
                "Gifts/Donations", "Special Occasion", "Entertainment",
                "Vacations", "Subscriptions"
        });
        categoryField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(categoryField, gbc);

        // Amount Field
        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(amountLabel, gbc);

        amountField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(amountField, gbc);


        //Duration Field
        //TODO: add start and end date field instead "YYYY-MM-dd"
        JLabel durationLabel = new JLabel("Duration:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(durationLabel, gbc);
        durationField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(durationField, gbc);

        // Notification setup
        notificationCheckBox = new JCheckBox("Turn On Notifications");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(notificationCheckBox, gbc);

        add(centerPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Switch to the "Dashboard" panel when the button is clicked
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Dashboard");
        });
        buttonPanel.add(backButton);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> handleBudget());
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Handle the user's inputs
    private void handleBudget() {
        String category = (String) categoryField.getSelectedItem();
        String amount = amountField.getText();
        String duration = durationField.getText();
        // boolean notificationsEnabled = notificationCheckBox.isSelected();


        //Check if any field is empty
        if (duration.isEmpty() || category.isEmpty() || amount.isEmpty() ){
            JOptionPane.showMessageDialog(this, "Please fill out all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //check the amount they inputed
        try{
        
            JOptionPane.showMessageDialog(this, null, "Success", JOptionPane.INFORMATION_MESSAGE);

            //clear everything after it is added
            durationField.setText("");
            categoryField.setSelectedIndex(0);
            amountField.setText("");
            yearDropdown.setSelectedIndex(0);
            monthDropdown.setSelectedIndex(0);
            dayDropdown.setSelectedIndex(0);
            notificationCheckBox.setSelected(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the amount", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update the days dropdown based on the selected year and month
    private void updateDaysDropdown(JComboBox<Integer> dayDropdown, JComboBox<Integer> yearDropdown,
            JComboBox<String> monthDropdown) {
        dayDropdown.removeAllItems();
        int year = (int) yearDropdown.getSelectedItem();
        int month = monthDropdown.getSelectedIndex();

        int daysInMonth = switch (month) {
            case 0, 2, 4, 6, 7, 9, 11 -> 31; // January, March, May, July, August, October, December
            case 3, 5, 8, 10 -> 30; // April, June, September, November
            case 1 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28; // February
            default -> 0;
        };

        for (int i = 1; i <= daysInMonth; i++) {
            dayDropdown.addItem(i);
        }
    }
}
