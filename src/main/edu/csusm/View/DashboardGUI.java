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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class DashboardGUI extends JPanel implements ActionListener {
    
    // Navigation buttons
    private JButton savingsButton;
    private JButton budgetButton;
    private JButton transactionButton;
    private JButton currencyExchangeButton;
    private JButton refreshButton;
    private JButton logoutButton;
    
    // Chart panels
    private JFXPanel budgetChartPanel;
    private JFXPanel savingsChartPanel;
    private JFXPanel transactionChartPanel;
    
    private StackedBarChart<String, Number> budgetBarChart;
    private PieChart savingsPieChart;
    private PieChart transactionPieChart;
    
    // Panels
    private JPanel summaryPanel;
    private JPanel transactionsPanel;
    private JPanel chartsPanel;
    
    // Data access objects
    private final SavingsGoalDAO sDao;
    private final BudgetGoalDAO bDao;
    private final TransactionDAO tDao;
    
    // Current data models
    private BudgetGoal currentBudget;
    private SavingsGoal currentGoal;
    private Transaction currentTransaction;
    private final UserModel currentUser;
    
    // Styling constants
    private static final Color PRIMARY_COLOR = new Color(38, 120, 190); // Blue
    private static final Color ACCENT_COLOR = new Color(46, 204, 113); // Green
    private static final Color WARNING_COLOR = new Color(231, 76, 60); // Red
    private static final Color BACKGROUND_COLOR = new Color(245, 246, 250); // Light gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(52, 73, 94); // Dark blue/gray
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font HEADING_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final int PADDING = 15;
    
    // Formatter for currency
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");

    public DashboardGUI() {
        // Initialize data objects
        sDao = new SavingsGoalDAO();
        bDao = new BudgetGoalDAO();
        tDao = new TransactionDAO();
        currentUser = UserSession.getInstance().getCurrentUser();
        
        // Set up main panel
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // Create navigation bar
        JPanel navBar = createNavBar();
        add(navBar, BorderLayout.NORTH);
        
        // Create main content area with 3 sections: summary, transactions, charts
        JPanel mainContent = new JPanel(new BorderLayout(PADDING, PADDING));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Create the welcome header
        JPanel headerPanel = createHeaderPanel();
        mainContent.add(headerPanel, BorderLayout.NORTH);
        
        // Create center panel with summary and transactions side by side
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, PADDING, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        
        // Create summary panel
        summaryPanel = createSummaryPanel();
        centerPanel.add(summaryPanel);
        
        // Create transactions panel
        transactionsPanel = createTransactionsPanel();
        centerPanel.add(transactionsPanel);
        
        mainContent.add(centerPanel, BorderLayout.CENTER);
        
        // Create charts panel
        chartsPanel = createChartsPanel();
        mainContent.add(chartsPanel, BorderLayout.SOUTH);
        
        add(mainContent, BorderLayout.CENTER);
        
        // Update data
        updateData();
    }
    
    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navBar.setBackground(PRIMARY_COLOR);
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR.darker()));
        
        // Create navigation buttons
        savingsButton = createNavButton("Savings");
        budgetButton = createNavButton("Budget");
        transactionButton = createNavButton("Transaction");
        currencyExchangeButton = createNavButton("Currency Exchange");
        refreshButton = createNavButton("Refresh");
        logoutButton = createNavButton("Logout");
        
        // Add buttons to the navigation bar
        navBar.add(savingsButton);
        navBar.add(budgetButton);
        navBar.add(transactionButton);
        navBar.add(currencyExchangeButton);
        navBar.add(refreshButton);
        navBar.add(logoutButton);
        
        return navBar;
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CARD_COLOR);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFirstName() + "!");
        welcomeLabel.setFont(TITLE_FONT);
        welcomeLabel.setForeground(TEXT_COLOR);
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        JLabel dateLabel = new JLabel(dateFormat.format(new Date()));
        dateLabel.setFont(LABEL_FONT);
        dateLabel.setForeground(TEXT_COLOR);
        headerPanel.add(dateLabel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Summary title
        JLabel titleLabel = new JLabel("Financial Summary");
        titleLabel.setFont(HEADING_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Summary content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_COLOR);
        
        // Overall balance
        JPanel balancePanel = createInfoRow("Current Balance:", "Loading...", ACCENT_COLOR);
        contentPanel.add(balancePanel);
        contentPanel.add(Box.createVerticalStrut(15));
        
        // Savings goals
        JPanel savingsPanel = createInfoRow("Savings Goal:", "Loading...", TEXT_COLOR);
        contentPanel.add(savingsPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        
        // Budget summary
        JPanel budgetPanel = createInfoRow("Current Budget:", "Loading...", TEXT_COLOR);
        contentPanel.add(budgetPanel);
        contentPanel.add(Box.createVerticalStrut(15));
        
        // Latest transaction
        JPanel transactionPanel = createInfoRow("Latest Transaction:", "Loading...", TEXT_COLOR);
        contentPanel.add(transactionPanel);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        // Transactions title
        JLabel titleLabel = new JLabel("Recent Transactions");
        titleLabel.setFont(HEADING_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Transactions list container
        JPanel listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(CARD_COLOR);
        
        // Create a scroll pane for the transactions
        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Fill with placeholder or real transactions
        listContainer.add(new JLabel("Loading transactions..."));
        
        return panel;
    }
    
    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, PADDING, 0));
        panel.setBackground(BACKGROUND_COLOR);
        
        // Budget chart
        budgetChartPanel = new JFXPanel();
        JPanel budgetPanel = wrapChartInPanel(budgetChartPanel, "Budget Overview");
        panel.add(budgetPanel);
        
        // Savings chart
        savingsChartPanel = new JFXPanel();
        JPanel savingsPanel = wrapChartInPanel(savingsChartPanel, "Savings Progress");
        panel.add(savingsPanel);
        
        // Transactions chart
        transactionChartPanel = new JFXPanel();
        JPanel transactionPanel = wrapChartInPanel(transactionChartPanel, "Spending Categories");
        panel.add(transactionPanel);
        
        // Initialize charts
        initializeCharts();
        
        return panel;
    }
    
    private JPanel wrapChartInPanel(JFXPanel chartPanel, String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADING_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        chartPanel.setPreferredSize(new Dimension(300, 250));
        panel.add(chartPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createInfoRow(String label, String value, Color valueColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(LABEL_FONT);
        labelComponent.setForeground(TEXT_COLOR);
        panel.add(labelComponent, BorderLayout.WEST);
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setName(label); // Use the label as identifier
        valueComponent.setFont(new Font(LABEL_FONT.getFontName(), Font.BOLD, LABEL_FONT.getSize()));
        valueComponent.setForeground(valueColor);
        valueComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(valueComponent, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createTransactionItem(Transaction transaction) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(240, 240, 240)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        // Transaction type icon/indicator
        boolean isDeposit = "Deposit".equals(transaction.getType());
        JLabel typeIndicator = new JLabel(isDeposit ? "+" : "-");
        typeIndicator.setFont(new Font(LABEL_FONT.getFontName(), Font.BOLD, 16));
        typeIndicator.setForeground(isDeposit ? ACCENT_COLOR : WARNING_COLOR);
        typeIndicator.setHorizontalAlignment(SwingConstants.CENTER);
        typeIndicator.setPreferredSize(new Dimension(30, 30));
        panel.add(typeIndicator, BorderLayout.WEST);
        
        // Transaction details
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.setBackground(CARD_COLOR);
        
        JLabel categoryLabel = new JLabel(transaction.getCategory());
        categoryLabel.setFont(LABEL_FONT);
        categoryLabel.setForeground(TEXT_COLOR);
        detailsPanel.add(categoryLabel);
        
        JLabel typeLabel = new JLabel(transaction.getType());
        typeLabel.setFont(new Font(LABEL_FONT.getFontName(), Font.PLAIN, 12));
        typeLabel.setForeground(new Color(150, 150, 150));
        detailsPanel.add(typeLabel);
        
        panel.add(detailsPanel, BorderLayout.CENTER);
        
        // Transaction amount
        JLabel amountLabel = new JLabel(currencyFormat.format(transaction.getAmount()));
        amountLabel.setFont(new Font(LABEL_FONT.getFontName(), Font.BOLD, LABEL_FONT.getSize()));
        amountLabel.setForeground(isDeposit ? ACCENT_COLOR : WARNING_COLOR);
        amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(amountLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    public  JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        
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
    
    private void initializeCharts() {
        // Initialize budget bar chart
        Platform.runLater(() -> {
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Categories");
            
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Amount");
            
            budgetBarChart = new StackedBarChart<>(xAxis, yAxis);
            budgetBarChart.setTitle("Budget Goals");
            budgetBarChart.setAnimated(false);
            
            budgetChartPanel.setScene(new Scene(new StackPane(budgetBarChart), 300, 250));
        });
        
        // Initialize savings pie chart
        Platform.runLater(() -> {
            savingsPieChart = new PieChart();
            savingsPieChart.setTitle("Savings Progress");
            savingsPieChart.setLabelsVisible(true);
            savingsPieChart.setAnimated(false);
            
            savingsChartPanel.setScene(new Scene(new StackPane(savingsPieChart), 300, 250));
        });
        
        // Initialize transaction pie chart
        Platform.runLater(() -> {
            transactionPieChart = new PieChart();
            transactionPieChart.setTitle("Spending Categories");
            transactionPieChart.setLabelsVisible(true);
            transactionPieChart.setAnimated(false);
            
            transactionChartPanel.setScene(new Scene(new StackPane(transactionPieChart), 300, 250));
        });
    }
    
    private void updateData() {
        // Update financial summary
        updateFinancialSummary();
        
        // Update transaction list
        updateTransactionsList();
        
        // Update charts
        updateCharts();
    }
    
    private void updateFinancialSummary() {
        // Calculate total balance (deposits - withdrawals)
        double totalBalance = calculateBalance();
        updateValueLabel("Current Balance:", currencyFormat.format(totalBalance));
        
        // Update savings goal info
        SavingsGoal currentSavingsGoal = sDao.getSavingsGoal();
        if (currentSavingsGoal != null) {
            double currentAmount = currentSavingsGoal.getStartingAmount();
            double targetAmount = currentSavingsGoal.getTargetAmount();
            String savingsText = currencyFormat.format(currentAmount) + " / " + 
                              currencyFormat.format(targetAmount) + " (" + 
                              currentSavingsGoal.getName() + ")";
            updateValueLabel("Savings Goal:", savingsText);
        } else {
            updateValueLabel("Savings Goal:", "No active savings goal");
        }
        
        // Update budget info
        BudgetGoal currentBudget = bDao.getBudgetGoal();
        if (currentBudget != null) {
            String budgetText = currentBudget.getCategory() + ": " + 
                             currencyFormat.format(currentBudget.getBudgetAmount());
            updateValueLabel("Current Budget:", budgetText);
        } else {
            updateValueLabel("Current Budget:", "No active budget");
        }
        
        // Update latest transaction
        Transaction latestTransaction = tDao.getTransaction();
        if (latestTransaction != null) {
            String transactionText = latestTransaction.getType() + " - " + 
                                  latestTransaction.getCategory() + ": " +
                                  currencyFormat.format(latestTransaction.getAmount());
            updateValueLabel("Latest Transaction:", transactionText);
        } else {
            updateValueLabel("Latest Transaction:", "No recent transactions");
        }
    }
    
    private void updateValueLabel(String labelName, String value) {
        // Find the panel with the matching label
        for (Component c : summaryPanel.getComponents()) {
            if (c instanceof JPanel) {
                JPanel contentPanel = (JPanel) c;
                for (Component panelComponent : contentPanel.getComponents()) {
                    if (panelComponent instanceof JPanel) {
                        JPanel rowPanel = (JPanel) panelComponent;
                        for (Component rowComponent : rowPanel.getComponents()) {
                            if (rowComponent instanceof JLabel && labelName.equals(((JLabel) rowComponent).getName())) {
                                ((JLabel) rowComponent).setText(value);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void updateTransactionsList() {
        // Get the transactions panel and clear it
        JScrollPane scrollPane = (JScrollPane) transactionsPanel.getComponent(1);
        JPanel listContainer = (JPanel) scrollPane.getViewport().getView();
        listContainer.removeAll();
        
        // Get recent transactions
        ArrayList<Transaction> transactions = tDao.getTransactionList();
        
        if (transactions == null || transactions.isEmpty()) {
            JLabel noTransactionsLabel = new JLabel("No transactions found");
            noTransactionsLabel.setFont(LABEL_FONT);
            noTransactionsLabel.setForeground(new Color(150, 150, 150));
            noTransactionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            listContainer.add(noTransactionsLabel);
        } else {
            // Show up to 5 most recent transactions
            int count = 0;
            for (Transaction transaction : transactions) {
                if (count >= 5) break;
                
                JPanel transactionItem = createTransactionItem(transaction);
                transactionItem.setAlignmentX(Component.LEFT_ALIGNMENT);
                listContainer.add(transactionItem);
                listContainer.add(Box.createVerticalStrut(10));
                count++;
            }
        }
        
        listContainer.revalidate();
        listContainer.repaint();
    }
    
    private void updateCharts() {
        updateBudgetChart();
        updateSavingsChart();
        updateTransactionChart();
    }
    
    private void updateBudgetChart() {
        // Get budget data from the database
        ArrayList<BudgetGoal> budgetGoals = bDao.getBudgetGoalsByCategory();
        ArrayList<Transaction> withdrawals = tDao.getWithdrawTransactions();
        
        if (budgetGoals == null || budgetGoals.isEmpty()) {
            Platform.runLater(() -> {
                budgetBarChart.getData().clear();
                budgetBarChart.setTitle("No Budget Goals Available");
            });
            return;
        }
        
        // Map for categorizing budget and spending data
        Map<String, Double> budgetAmounts = new HashMap<>();
        Map<String, Double> spentAmounts = new HashMap<>();
        
        // Process budget goals
        for (BudgetGoal goal : budgetGoals) {
            budgetAmounts.put(goal.getCategory(), goal.getBudgetAmount());
        }
        
        // Process withdrawals
        if (withdrawals != null && !withdrawals.isEmpty()) {
            for (Transaction transaction : withdrawals) {
                String category = transaction.getCategory();
                if (budgetAmounts.containsKey(category)) {
                    spentAmounts.merge(category, transaction.getAmount(), Double::sum);
                }
            }
        }
        
        // Update the chart
        Platform.runLater(() -> {
            budgetBarChart.getData().clear();
            
            XYChart.Series<String, Number> spentSeries = new XYChart.Series<>();
            spentSeries.setName("Spent");
            
            XYChart.Series<String, Number> remainingSeries = new XYChart.Series<>();
            remainingSeries.setName("Remaining");
            
            for (Map.Entry<String, Double> entry : budgetAmounts.entrySet()) {
                String category = entry.getKey();
                double budgetAmount = entry.getValue();
                double spentAmount = spentAmounts.getOrDefault(category, 0.0);
                double remainingAmount = Math.max(0, budgetAmount - spentAmount);
                
                spentSeries.getData().add(new XYChart.Data<>(category, spentAmount));
                remainingSeries.getData().add(new XYChart.Data<>(category, remainingAmount));
            }
            
            budgetBarChart.getData().addAll(spentSeries, remainingSeries);
        });
    }
    
    private void updateSavingsChart() {
        // Get savings goal data
        SavingsGoal savingsGoal = sDao.getSavingsGoal();
        
        if (savingsGoal == null) {
            Platform.runLater(() -> {
                savingsPieChart.getData().clear();
                savingsPieChart.setTitle("No Savings Goal Available");
            });
            return;
        }
        
        // Calculate amounts
        double savedAmount = savingsGoal.getStartingAmount();
        double targetAmount = savingsGoal.getTargetAmount();
        double remainingAmount = Math.max(0, targetAmount - savedAmount);
        
        // Update the chart
        Platform.runLater(() -> {
            savingsPieChart.getData().clear();
            
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Saved", savedAmount),
                new PieChart.Data("Remaining", remainingAmount)
            );
            
            savingsPieChart.setData(pieChartData);
            savingsPieChart.setTitle(savingsGoal.getName());
        });
    }
    
    private void updateTransactionChart() {
        // Get transaction data
        ArrayList<Transaction> withdrawals = tDao.getWithdrawTransactions();
        
        if (withdrawals == null || withdrawals.isEmpty()) {
            Platform.runLater(() -> {
                transactionPieChart.getData().clear();
                transactionPieChart.setTitle("No Transaction Data Available");
            });
            return;
        }
        
        // Map for categorizing spending
        Map<String, Double> categoryTotals = new HashMap<>();
        
        // Process transactions
        for (Transaction transaction : withdrawals) {
            String category = transaction.getCategory();
            categoryTotals.merge(category, transaction.getAmount(), Double::sum);
        }
        
        // Update the chart
        Platform.runLater(() -> {
            transactionPieChart.getData().clear();
            
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
            
            transactionPieChart.setData(pieChartData);
            transactionPieChart.setTitle("Spending by Category");
        });
    }
    
    private double calculateBalance() {
        double balance = 0.0;
        
        ArrayList<Transaction> allTransactions = tDao.getTransactionList();
        
        if (allTransactions != null) {
            for (Transaction t : allTransactions) {
                if ("Deposit".equals(t.getType())) {
                    balance += t.getAmount();
                } else if ("Withdrawal".equals(t.getType())) {
                    balance -= t.getAmount();
                }
                // Transfer type doesn't affect overall balance
            }
        }
        
        return balance;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == savingsButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Savings");
        } else if (e.getSource() == budgetButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Budget");
        } else if (e.getSource() == transactionButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Transaction");
        } else if (e.getSource() == currencyExchangeButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "CurrencyExchange");
        } else if (e.getSource() == logoutButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Login");
        } else if (e.getSource() == refreshButton) {
            updateData();
        }
    }
}
