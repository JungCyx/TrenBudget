package edu.csusm.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.csusm.DAO.BudgetGoalDAO;
import edu.csusm.DAO.SavingsGoalDAO;
import edu.csusm.DAO.TransactionDAO;
import edu.csusm.Model.BudgetGoal;
import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.Transaction;
import edu.csusm.Model.UserModel;
import edu.csusm.Model.UserSession;

public class DashboardGUI extends JPanel {

    // UI Components
    private JPanel summaryPanel;
    private JPanel actionsPanel;
    private JPanel recentActivityPanel;
    
    // Navigation buttons
    private JButton savingsButton;
    private JButton budgetButton;
    private JButton transactionButton;
    private JButton refreshButton;
    private JButton logoutButton;
    
    // Info labels
    private JLabel welcomeLabel;
    private JLabel dateLabel;
    private JLabel balanceLabel;
    private JLabel savingsLabel;
    private JLabel budgetLabel;
    
    // Data access objects
    private final SavingsGoalDAO savingsDao;
    private final BudgetGoalDAO budgetDao;
    private final TransactionDAO transactionDao;
    
    // Current user data
    private final UserModel currentUser;
    
    // Styling constants
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185); // Soft blue
    private static final Color ACCENT_COLOR = new Color(26, 188, 156); // Mint green
    private static final Color WARNING_COLOR = new Color(231, 76, 60); // Red
    private static final Color BACKGROUND_COLOR = new Color(245, 246, 250); // Light gray/blue
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(52, 73, 94); // Dark blue/gray
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font SUBHEADING_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final int PADDING = 20;
    
    // Formatter for currency
    private final DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");

    public DashboardGUI() {
        // Initialize data objects
        savingsDao = new SavingsGoalDAO();
        budgetDao = new BudgetGoalDAO();
        transactionDao = new TransactionDAO();
        currentUser = UserSession.getInstance().getCurrentUser();
        
        // Set up main panel
        setLayout(new BorderLayout(15, 15));
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Add components
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Create center panel with grid layout
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Left side - Summary and Actions
        JPanel leftPanel = new JPanel(new BorderLayout(0, 15));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.add(createSummaryPanel(), BorderLayout.NORTH);
        leftPanel.add(createActionsPanel(), BorderLayout.CENTER);
        
        // Right side - Recent Activity
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_COLOR);
        rightPanel.add(createRecentActivityPanel(), BorderLayout.CENTER);
        
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        add(centerPanel, BorderLayout.CENTER);
        
        // Refresh data on load
        refreshDashboardData();
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 1),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Left side - Welcome and Date
        JPanel leftHeaderPanel = new JPanel(new GridLayout(2, 1));
        leftHeaderPanel.setBackground(PRIMARY_COLOR);
        
        welcomeLabel = new JLabel("Welcome, " + currentUser.getFirstName());
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(Color.WHITE);
        leftHeaderPanel.add(welcomeLabel);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        dateLabel = new JLabel(dateFormat.format(new Date()));
        dateLabel.setFont(LABEL_FONT);
        dateLabel.setForeground(Color.WHITE);
        leftHeaderPanel.add(dateLabel);
        
        headerPanel.add(leftHeaderPanel, BorderLayout.WEST);
        
        // Right side - Navigation
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(PRIMARY_COLOR);
        
        savingsButton = createNavButton("Savings");
        budgetButton = createNavButton("Budget");
        transactionButton = createNavButton("Transaction");
        refreshButton = createNavButton("Refresh");
        logoutButton = createNavButton("Logout");
        
        navPanel.add(savingsButton);
        navPanel.add(budgetButton);
        navPanel.add(transactionButton);
        navPanel.add(refreshButton);
        navPanel.add(logoutButton);
        
        headerPanel.add(navPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSummaryPanel() {
        summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(CARD_COLOR);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Title
        JLabel summaryTitle = new JLabel("Financial Summary");
        summaryTitle.setFont(HEADING_FONT);
        summaryTitle.setForeground(TEXT_COLOR);
        summaryTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.add(summaryTitle);
        summaryPanel.add(Box.createVerticalStrut(15));
        
        // Balance
        JPanel balanceRow = createSummaryRow("Current Balance:", "$0.00");
        balanceLabel = (JLabel) balanceRow.getComponent(1);
        summaryPanel.add(balanceRow);
        summaryPanel.add(Box.createVerticalStrut(10));
        
        // Savings
        JPanel savingsRow = createSummaryRow("Savings Progress:", "$0.00 / $0.00");
        savingsLabel = (JLabel) savingsRow.getComponent(1);
        summaryPanel.add(savingsRow);
        summaryPanel.add(Box.createVerticalStrut(10));
        
        // Budget
        JPanel budgetRow = createSummaryRow("Current Budget:", "No active budget");
        budgetLabel = (JLabel) budgetRow.getComponent(1);
        summaryPanel.add(budgetRow);
        
        return summaryPanel;
    }
    
    private JPanel createSummaryRow(String labelText, String valueText) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(CARD_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        row.add(label, BorderLayout.WEST);
        
        JLabel value = new JLabel(valueText);
        value.setFont(LABEL_FONT);
        value.setForeground(TEXT_COLOR);
        value.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(value, BorderLayout.EAST);
        
        return row;
    }
    
    private JPanel createActionsPanel() {
        actionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        actionsPanel.setBackground(BACKGROUND_COLOR);
        
        actionsPanel.add(createActionButton("Add Transaction", ACCENT_COLOR, e -> 
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Transaction")));
        
        actionsPanel.add(createActionButton("Create Budget", PRIMARY_COLOR, e -> 
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Budget")));
        
        actionsPanel.add(createActionButton("Set Savings Goal", PRIMARY_COLOR, e -> 
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Savings")));
        
        actionsPanel.add(createActionButton("Transaction History", PRIMARY_COLOR, e -> 
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "TransactionHistory")));
        
        return actionsPanel;
    }
    
    private JPanel createRecentActivityPanel() {
        recentActivityPanel = new JPanel();
        recentActivityPanel.setLayout(new BorderLayout());
        recentActivityPanel.setBackground(CARD_COLOR);
        recentActivityPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Title
        JLabel activityTitle = new JLabel("Recent Activity");
        activityTitle.setFont(HEADING_FONT);
        activityTitle.setForeground(TEXT_COLOR);
        recentActivityPanel.add(activityTitle, BorderLayout.NORTH);
        
        // Activity list (will be populated in refreshDashboardData())
        JPanel activityListPanel = new JPanel();
        activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));
        activityListPanel.setBackground(CARD_COLOR);
        activityListPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        // Add a scrollpane
        JScrollPane scrollPane = new JScrollPane(activityListPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        recentActivityPanel.add(scrollPane, BorderLayout.CENTER);
        
        return recentActivityPanel;
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this::handleNavigation);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    private JButton createActionButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createActivityItem(String type, String description, String amount, boolean isDeposit) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBackground(CARD_COLOR);
        itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        // Left - icon
        JLabel iconLabel = new JLabel(isDeposit ? "+" : "-");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        iconLabel.setForeground(isDeposit ? ACCENT_COLOR : WARNING_COLOR);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(30, 40));
        itemPanel.add(iconLabel, BorderLayout.WEST);
        
        // Center - description
        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(CARD_COLOR);
        
        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        typeLabel.setForeground(TEXT_COLOR);
        centerPanel.add(typeLabel);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(120, 120, 120));
        centerPanel.add(descLabel);
        
        itemPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Right - amount
        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        amountLabel.setForeground(isDeposit ? ACCENT_COLOR : WARNING_COLOR);
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        itemPanel.add(amountLabel, BorderLayout.EAST);
        
        return itemPanel;
    }
    
    private void handleNavigation(ActionEvent e) {
        JButton sourceButton = (JButton) e.getSource();
        String buttonText = sourceButton.getText();
        
        switch (buttonText) {
            case "Savings":
                LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Savings");
                break;
            case "Budget":
                LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Budget");
                break;
            case "Transaction":
                LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Transaction");
                break;
            case "Refresh":
                refreshDashboardData();
                break;
            case "Logout":
                LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Login");
                break;
        }
    }
    
    private void refreshDashboardData() {
        // Update financial summary
        updateFinancialSummary();
        
        // Update recent activity
        updateRecentActivity();
    }
    
    private void updateFinancialSummary() {
        // Calculate total balance (deposits - withdrawals)
        double totalBalance = calculateBalance();
        balanceLabel.setText(currencyFormat.format(totalBalance));
        
        // Update savings goal info
        SavingsGoal currentSavingsGoal = savingsDao.getSavingsGoal();
        if (currentSavingsGoal != null) {
            double currentAmount = currentSavingsGoal.getStartingAmount();
            double targetAmount = currentSavingsGoal.getTargetAmount();
            savingsLabel.setText(currencyFormat.format(currentAmount) + " / " + 
                              currencyFormat.format(targetAmount));
            
            // Change color based on progress
            double progress = currentAmount / targetAmount;
            if (progress >= 0.8) {
                savingsLabel.setForeground(ACCENT_COLOR);
            } else {
                savingsLabel.setForeground(TEXT_COLOR);
            }
        } else {
            savingsLabel.setText("No active savings goal");
            savingsLabel.setForeground(TEXT_COLOR);
        }
        
        // Update budget info
        BudgetGoal currentBudget = budgetDao.getBudgetGoal();
        if (currentBudget != null) {
            budgetLabel.setText(currentBudget.getCategory() + ": " + 
                             currencyFormat.format(currentBudget.getBudgetAmount()));
        } else {
            budgetLabel.setText("No active budget");
        }
    }
    
    private void updateRecentActivity() {
        // Get the activity list panel
        JScrollPane scrollPane = (JScrollPane) recentActivityPanel.getComponent(1);
        JViewport viewport = scrollPane.getViewport();
        JPanel activityListPanel = (JPanel) viewport.getView();
        
        // Clear existing items
        activityListPanel.removeAll();
        
        // Get recent transactions
        ArrayList<Transaction> recentTransactions = transactionDao.getTransactionList();
        
        if (recentTransactions.isEmpty()) {
            JLabel noActivityLabel = new JLabel("No recent activity");
            noActivityLabel.setFont(LABEL_FONT);
            noActivityLabel.setForeground(new Color(150, 150, 150));
            noActivityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            activityListPanel.add(noActivityLabel);
        } else {
            // Show up to 5 most recent transactions
            int count = 0;
            for (Transaction t : recentTransactions) {
                if (count >= 5) break;
                
                boolean isDeposit = "Deposit".equals(t.getType());
                JPanel activityItem = createActivityItem(
                        t.getType(),
                        t.getCategory(),
                        currencyFormat.format(t.getAmount()),
                        isDeposit
                );
                activityItem.setAlignmentX(Component.LEFT_ALIGNMENT);
                activityListPanel.add(activityItem);
                activityListPanel.add(Box.createVerticalStrut(10));
                count++;
            }
        }
        
        // Refresh the UI
        activityListPanel.revalidate();
        activityListPanel.repaint();
    }
    
    private double calculateBalance() {
        double balance = 0.0;
        
        ArrayList<Transaction> allTransactions = transactionDao.getTransactionList();
        
        for (Transaction t : allTransactions) {
            if ("Deposit".equals(t.getType())) {
                balance += t.getAmount();
            } else if ("Withdrawal".equals(t.getType())) {
                balance -= t.getAmount();
            }
            // Transfer type doesn't affect overall balance
        }
        
        return balance;
    }
}
