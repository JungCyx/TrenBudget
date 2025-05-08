package edu.csusm.View;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import edu.csusm.Builder.MortgageBuilder;
import edu.csusm.Controller.UserController;
import edu.csusm.DAO.BudgetGoalDAO;
import edu.csusm.DAO.MortgageDAO;
import edu.csusm.Model.BudgetGoal;
import edu.csusm.Model.UserSession;
import edu.csusm.Observer.ObservableBugetGoal;

public class BudgetGUI extends JPanel {
    private BudgetGoalDAO bDAO;
    private MortgageDAO mDAO;

    private JComboBox<String> categoryField;
    private JTextField amountField;
    private JCheckBox notificationCheckBox;
    private JButton addButton;
    private JButton backButton;
    private JButton createMortgageButton;
    private JPanel mortgageBuilderPanel; // Will hold the attribute buttons once triggered
    private MortgageBuilder mortgageBuilder = new MortgageBuilder();
    private Map<String, JTextField> mortgageFields = new HashMap<>();

    private UserController controller;
    private JComboBox<Integer> startYearDropdown, endYearDropdown;
    private JComboBox<String> startMonthDropdown, endMonthDropdown;
    private JComboBox<Integer> startDayDropdown, endDayDropdown;

    // Constructor for BudgetGUI
    public BudgetGUI() {
        setLayout(new BorderLayout()); // Use BorderLayout for the main panel
        setBackground(Color.WHITE); // Set the background color to white

        controller = new UserController();
        bDAO = new BudgetGoalDAO();
        mDAO = new MortgageDAO();

        // Title Navbar
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(38, 120, 190));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Budget", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Create description label
        JLabel descriptionLabel = new JLabel("Create a New Budget ", JLabel.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        descriptionLabel.setForeground(Color.WHITE);

        // Add labels to the title panel
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(descriptionLabel, BorderLayout.SOUTH);

        // Add title panel to the top
        add(titlePanel, BorderLayout.NORTH);

        // Center Panel to display the input fields
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE); // Ensure inner panel matches background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Dropdown for Categories
        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(categoryLabel, gbc);

        categoryField = new JComboBox<>(new String[]{
            "Rent", "Property Taxes", "Household Repairs", "HOA Fees",
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
        startDatePanel.setBackground(Color.WHITE); // Set background color
        startYearDropdown = new JComboBox<>();
        startMonthDropdown = new JComboBox<>(new String[]{
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
        endDatePanel.setBackground(Color.WHITE); // Set background color
        endYearDropdown = new JComboBox<>();
        endMonthDropdown = new JComboBox<>(new String[]{
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
        notificationCheckBox.setBackground(Color.WHITE); // Match background
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(notificationCheckBox, gbc);

// Panel setup
        JPanel removeBudgetPanel = new JPanel();
        removeBudgetPanel.setBackground(Color.WHITE); // Match background
        removeBudgetPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcRemove = new GridBagConstraints();
        gbcRemove.insets = new Insets(10, 10, 10, 10);

        JLabel removeLabel = new JLabel("Remove Budget:");
        gbcRemove.gridx = 0;
        gbcRemove.gridy = 0;
        gbcRemove.anchor = GridBagConstraints.EAST;
        removeBudgetPanel.add(removeLabel, gbcRemove);

// Create a JComboBox for selecting the budget to remove
        JComboBox<String> removeBudgetDropdown = new JComboBox<>(getBudgetNames()); // Populate with budget names
        removeBudgetDropdown.setPreferredSize(new Dimension(200, 25));
        gbcRemove.gridx = 1;
        gbcRemove.anchor = GridBagConstraints.WEST;
        removeBudgetPanel.add(removeBudgetDropdown, gbcRemove);

// Add the Remove Button
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> handleRemoveBudget(removeBudgetDropdown));
        gbcRemove.gridx = 0;
        gbcRemove.gridy = 1;
        gbcRemove.gridwidth = 2;
        gbcRemove.anchor = GridBagConstraints.CENTER;
        removeBudgetPanel.add(removeButton, gbcRemove);

        mortgageBuilderPanel = new JPanel();
        mortgageBuilderPanel.setBackground(new Color(38, 120, 190)); // Blue background
        mortgageBuilderPanel.setPreferredSize(new Dimension(550, 400));
        mortgageBuilderPanel.setLayout(new GridBagLayout());

        createMortgageButton = new JButton("Create a New Mortgage");
        createMortgageButton.setForeground(Color.WHITE);
        createMortgageButton.setFont(new Font("Arial", Font.BOLD, 17));
        createMortgageButton.setContentAreaFilled(false);
        createMortgageButton.setBorderPainted(false);
        createMortgageButton.setFocusPainted(false);

        createMortgageButton.addActionListener(e -> showMortgageBuilderFields());

        mortgageBuilderPanel.add(createMortgageButton); // Add to center
// Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 20));
// Position mortgage builder panel to the right of form
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 8; // Span multiple rows
        gbc.anchor = GridBagConstraints.NORTHWEST;
        centerPanel.add(mortgageBuilderPanel, gbc);

        backButton = new JButton("Back");
        backButton.addActionListener(e -> handleBackButton());
        buttonPanel.add(backButton);

        addButton = new JButton("Add");
        addButton.addActionListener(e -> handleBudget());
        buttonPanel.add(addButton);

        GridBagConstraints gbcRemovePanel = new GridBagConstraints();
        gbcRemovePanel.gridx = 0;
        gbcRemovePanel.gridy = 5; // Ensure it is below the checkbox (grid row 4)
        gbcRemovePanel.gridwidth = 2;
        gbcRemovePanel.fill = GridBagConstraints.HORIZONTAL; // Ensure it stretches across the width
        centerPanel.add(removeBudgetPanel, gbcRemovePanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // Add this panel to the North region of the mainPanel
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.SOUTH);
    }
// Add removeBudgetPanel to the main layout (just below the form)

    // Method to populate the dropdown with existing budgets
    public String[] getBudgetNames() {
        List<BudgetGoal> allBudgetGoals = bDAO.getAllBudgetGoals(); // Get the list of all budget goals
        String[] budgetNames = new String[allBudgetGoals.size()];

        // Extract the category names from the BudgetGoal objects
        for (int i = 0; i < allBudgetGoals.size(); i++) {
            budgetNames[i] = allBudgetGoals.get(i).getCategory();
        }
        return budgetNames;
    }

// Handle the removal of the selected budget
    private void handleRemoveBudget(JComboBox<String> removeBudgetDropdown) {
        String selectedBudget = (String) removeBudgetDropdown.getSelectedItem();
        if (selectedBudget != null && !selectedBudget.isEmpty()) {
            bDAO.removeBudgetByName(selectedBudget);
            JOptionPane.showMessageDialog(this, "Budget " + selectedBudget + " has been removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a valid budget to remove.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

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
            case 0, 2, 4, 6, 7, 9, 11 ->
                31; // Jan, Mar, May, Jul, Aug, Oct, Dec
            case 3, 5, 8, 10 ->
                30; // Apr, Jun, Sep, Nov
            case 1 ->
                (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28; // Feb
            default ->
                0;
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

    private void handleBackButton() {

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

        String startD = returnValidString(startYear) + "-" + startMonth + "-" + returnValidString(startDay);
        String EndD = returnValidString(endYear) + "-" + endMonth + "-" + returnValidString(endDay);

        //old code
        /* 
        BudgetGoal newBudgetGoal = controller.mapBudgetGoalWithUser(category, Double.parseDouble(amount), startD, EndD, notifications, controller.getUser());
        */

        //New code (factory)
        BudgetGoal newBudgetGoal = controller.mapBudgetGoalWithFactory(category,Double.parseDouble(amount),startD,EndD,notifications);

        bDAO.addBudgetIntoDatabase(newBudgetGoal);

        ObservableBugetGoal b = ObservableBugetGoal.getInstance();
        b.processBudgetGoal(newBudgetGoal);

        if (category.isEmpty() || amount.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all the fields", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Double.parseDouble(amount); // Validate amount as a number
            JOptionPane.showMessageDialog(this,
                    "Category: " + category + "\nAmount: $" + amount
                    + "\nStart Date: " + startMonth + " " + startDay + ", " + startYear
                    + "\nEnd Date: " + endMonth + " " + endDay + ", " + endYear,
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the amount", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        } 

    }

    private void showMortgageBuilderFields() {
        String[] attributes = {
                "Principal", "Interest Rate", "Term (Years)",
                "Down Payment", "Insurance", "Property Tax Rate"
        };

        GridBagConstraints gbcAttr = new GridBagConstraints();
        gbcAttr.insets = new Insets(10, 10, 10, 10);
        gbcAttr.gridx = 0;

        mortgageBuilderPanel.removeAll();
        mortgageBuilderPanel.setBackground(new Color(245, 245, 245)); // light gray
        mortgageBuilderPanel.setBorder(BorderFactory.createTitledBorder("Build Mortgage"));
        mortgageBuilderPanel.setLayout(new GridBagLayout());

        int row = 0;
        for (String attr : attributes) {
            JButton attrButton = new JButton("Add " + attr);
            attrButton.setPreferredSize(new Dimension(200, 30));
            final int currentRow = row++; // Capture the button's row

            gbcAttr.gridx = 0;
            gbcAttr.gridy = currentRow;
            gbcAttr.gridwidth = 2;

            attrButton.addActionListener(e -> {
                mortgageBuilderPanel.remove(attrButton);

                JLabel label = new JLabel(attr + ":");
                JComponent inputComponent;
                switch (attr) {
                    case "Term (Years)" -> {
                        JComboBox<Integer> termDropdown = new JComboBox<>(new Integer[]{5, 10, 15, 20, 25, 30});
                        termDropdown.setPreferredSize(new Dimension(100, 25));
                        inputComponent = termDropdown;
                    }
                    case "Interest Rate" -> {
                        JComboBox<String> rateDropdown = new JComboBox<>(new String[]{
                                "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0", "5.5", "6.0"
                        });
                        rateDropdown.setPreferredSize(new Dimension(100, 25));
                        inputComponent = rateDropdown;
                    }
                    default -> inputComponent = new JTextField(12);
                }
                if (inputComponent instanceof JTextField textField)
                    mortgageFields.put(attr, textField);
                else {
                    mortgageFields.put(attr, new JTextField()); // dummy to signal presence
                }

                GridBagConstraints gbcLabel = new GridBagConstraints();
                gbcLabel.gridx = 0;
                gbcLabel.gridy = currentRow;
                gbcLabel.gridwidth = 1;
                gbcLabel.insets = new Insets(5, 5, 5, 5);
                gbcLabel.anchor = GridBagConstraints.EAST;

                GridBagConstraints gbcField = new GridBagConstraints();
                gbcField.gridx = 1;
                gbcField.gridy = currentRow;
                gbcField.gridwidth = 1;
                gbcField.insets = new Insets(5, 5, 5, 5);
                gbcField.anchor = GridBagConstraints.WEST;

                mortgageBuilderPanel.add(label, gbcLabel);
                mortgageBuilderPanel.add(inputComponent, gbcField);

                mortgageBuilderPanel.revalidate();
                mortgageBuilderPanel.repaint();
            });
            mortgageBuilderPanel.add(attrButton, gbcAttr);
        }


        JButton saveButton = new JButton("Save Mortgage");
        JLabel confirmationLabel = new JLabel("");
        confirmationLabel.setForeground(Color.BLACK);
        confirmationLabel.setFont(new Font("Arial", Font.BOLD, 12));

        saveButton.addActionListener(e -> {
            try {
                if (mortgageFields.containsKey("Principal"))
                    mortgageBuilder.addPrincipal(Double.parseDouble(mortgageFields.get("Principal").getText()));

                if (mortgageFields.containsKey("Interest Rate")) {
                    Component comp = findComponentByLabel("Interest Rate:");
                    if (comp instanceof JComboBox<?> combo) {
                        mortgageBuilder.addInterestRate(Double.parseDouble((String) combo.getSelectedItem()));
                    }
                }

                if (mortgageFields.containsKey("Term (Years)")) {
                    Component comp = findComponentByLabel("Term (Years):");
                    if (comp instanceof JComboBox<?> combo) {
                        mortgageBuilder.addTermInYears((int) combo.getSelectedItem());
                    }
                }

                if (mortgageFields.containsKey("Down Payment"))
                    mortgageBuilder.addDownPayment(Double.parseDouble(mortgageFields.get("Down Payment").getText()));

                if (mortgageFields.containsKey("Insurance"))
                    mortgageBuilder.addInsurance(Double.parseDouble(mortgageFields.get("Insurance").getText()));

                if (mortgageFields.containsKey("Property Tax Rate"))
                    mortgageBuilder.addPropertyTaxRate(Double.parseDouble(mortgageFields.get("Property Tax Rate").getText()));

                mortgageBuilder.setUserId(UserSession.getInstance().getCurrentUser().getId());

                // Save to DB
                mDAO.createMortgage(mortgageBuilder);
                confirmationLabel.setText("Mortgage saved.");
            } catch (Exception ex) {
                confirmationLabel.setForeground(Color.RED);
                confirmationLabel.setText("Error: " + ex.getMessage());
            }
        });

// Save button and label layout
        gbcAttr.gridy = row++;
        mortgageBuilderPanel.add(saveButton, gbcAttr);
        gbcAttr.gridy = row;
        mortgageBuilderPanel.add(confirmationLabel, gbcAttr);
    }

    private Component findComponentByLabel(String labelText) {
        Component[] components = mortgageBuilderPanel.getComponents();
        for (int i = 0; i < components.length - 1; i++) {
            if (components[i] instanceof JLabel label &&
                    label.getText().equals(labelText)) {
                // Assume the next component is the field/dropdown
                return components[i + 1];
            }
        }
        return null;
    }

}
