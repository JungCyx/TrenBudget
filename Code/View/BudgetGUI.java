package View;

import javax.swing.*;

import DAO.BudgetGoalDAO;
import Main.Controller.UserController;
import Model.BudgetGoal;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class BudgetGUI extends JPanel {
    private JComboBox<String> categoryField;
    private JTextField amountField;
    private JCheckBox notificationCheckBox;
    private JButton addButton;
    private JButton backButton;

    private UserController controller;
    private BudgetGoalDAO bDao;

    private JComboBox<Integer> startYearDropdown, endYearDropdown;
    private JComboBox<String> startMonthDropdown, endMonthDropdown;
    private JComboBox<Integer> startDayDropdown, endDayDropdown;

    // Constructor for BudgetGUI
    public BudgetGUI() {
        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        controller = new UserController();
        bDao = new BudgetGoalDAO();

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

        // Start Date Fields
        JLabel startDateLabel = new JLabel("Start Date:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(startDateLabel, gbc);

        JPanel startDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        startYearDropdown = new JComboBox<>();
        startMonthDropdown = new JComboBox<>(new String[] {
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        });
        startDayDropdown = new JComboBox<>();
        populateYearDropdown(startYearDropdown);
        updateDaysDropdown(startDayDropdown, startYearDropdown, startMonthDropdown);
        addDateDropdownListeners(startYearDropdown, startMonthDropdown, startDayDropdown);

        startDatePanel.add(startYearDropdown);
        startDatePanel.add(startMonthDropdown);
        startDatePanel.add(startDayDropdown);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(startDatePanel, gbc);

        // End Date Fields
        JLabel endDateLabel = new JLabel("End Date:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(endDateLabel, gbc);

        JPanel endDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        endYearDropdown = new JComboBox<>();
        endMonthDropdown = new JComboBox<>(new String[] {
                "01", "02", "03", "04", "05", "06",
                "07", "08", "09", "10", "11", "12"
        });
        endDayDropdown = new JComboBox<>();
        populateYearDropdown(endYearDropdown);
        updateDaysDropdown(endDayDropdown, endYearDropdown, endMonthDropdown);
        addDateDropdownListeners(endYearDropdown, endMonthDropdown, endDayDropdown);

        endDatePanel.add(endYearDropdown);
        endDatePanel.add(endMonthDropdown);
        endDatePanel.add(endDayDropdown);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(endDatePanel, gbc);

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
        backButton.addActionListener(e -> handleBackButton());
        buttonPanel.add(backButton);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> handleBudget());
        buttonPanel.add(addButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Populate year dropdown with values from the current year back to 1900
    private void populateYearDropdown(JComboBox<Integer> yearDropdown) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            yearDropdown.addItem(i);
        }
    }

    // Update the days dropdown based on the selected year and month
    private void updateDaysDropdown(JComboBox<Integer> dayDropdown, JComboBox<Integer> yearDropdown,
            JComboBox<String> monthDropdown) {
        dayDropdown.removeAllItems();
        int year = (int) yearDropdown.getSelectedItem();
        int month = monthDropdown.getSelectedIndex();

        int daysInMonth = switch (month) {
            case 0, 2, 4, 6, 7, 9, 11 -> 31; // Jan, Mar, May, Jul, Aug, Oct, Dec
            case 3, 5, 8, 10 -> 30; // Apr, Jun, Sep, Nov
            case 1 -> (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28; // Feb
            default -> 0;
        };

        for (int i = 1; i <= daysInMonth; i++) {
            dayDropdown.addItem(i);
        }
    }

    // Add listeners to update the days dropdown when the year or month changes
    private void addDateDropdownListeners(JComboBox<Integer> yearDropdown, JComboBox<String> monthDropdown,
            JComboBox<Integer> dayDropdown) {
        ActionListener updateDaysListener = e -> updateDaysDropdown(dayDropdown, yearDropdown, monthDropdown);
        yearDropdown.addActionListener(updateDaysListener);
        monthDropdown.addActionListener(updateDaysListener);
    }

    public String returnValidString(int number) {
        // Check if the number is between 1 and 9
        if (number >= 1 && number <= 9) {
            return "0" + number; // Add "0" at the beginning
        }
        // Otherwise, return the number as a string
        return String.valueOf(number);
    }

    private void handleBackButton(){

        LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Dashboard");

    }

    // Handle the user's inputs
    private void handleBudget() {
        String category = (String) categoryField.getSelectedItem();
        String amount = amountField.getText();
        int startYear = (int) startYearDropdown.getSelectedItem();
        String startMonth = (String) startMonthDropdown.getSelectedItem();
        int startDay = (int) startDayDropdown.getSelectedItem();
        int endYear = (int) endYearDropdown.getSelectedItem();
        String endMonth = (String) endMonthDropdown.getSelectedItem();
        int endDay = (int) endDayDropdown.getSelectedItem();
        boolean notifications = notificationCheckBox.isSelected();



        if (category.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Double.parseDouble(amount); // Validate amount as a number
            JOptionPane.showMessageDialog(this,
                    "Category: " + category + "\nAmount: $" + amount +
                            "\nStart Date: " + startMonth + " " + startDay + ", " + startYear +
                            "\nEnd Date: " + endMonth + " " + endDay + ", " + endYear,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the amount", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

        String startD = returnValidString(startYear)+ "-" + startMonth + "-" + returnValidString(startDay);
        String EndD = returnValidString(endYear)+ "-" + endMonth + "-" + returnValidString(endDay);

        BudgetGoal newBudgetGoal = controller.mapBudgetGoal(category, Double.parseDouble(amount), startD, EndD, notifications);
        bDao.addBudgetIntoDatabase(newBudgetGoal);

    }

    
}


