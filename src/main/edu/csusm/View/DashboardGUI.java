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

public class DashboardGUI extends JPanel implements ActionListener {
    // Navigation buttons
    private JButton refreshButton;
    private JButton logoutButton;
    
    // Action buttons
    private JButton savingsButton;
    private JButton budgetButton;
    private JButton transactionButton;
    private JButton currencyExchangeButton;
    
    // Labels
    private JLabel budgetLabel;
    private JLabel savingLabel;
    private JLabel transactionLabel; 
    
    // Panels
    private JPanel summaryPanel;
    private JPanel transactionsPanel;
    
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
        
        // Create main content area with summary first, then action buttons
        JPanel mainContent = new JPanel(new BorderLayout(PADDING, PADDING));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Create the welcome header
        JPanel headerPanel = createHeaderPanel();
        mainContent.add(headerPanel, BorderLayout.NORTH);
        
        // Create center panel with summary and transactions side by side
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, PADDING, 0));
        contentPanel.setBackground(BACKGROUND_COLOR);
        
        // Create left panel with more height for the action buttons panel
        JPanel leftPanel = new JPanel(new BorderLayout(0, PADDING));
        leftPanel.setBackground(BACKGROUND_COLOR);
        
        // Create summary panel
        summaryPanel = createSummaryPanel();
        leftPanel.add(summaryPanel, BorderLayout.CENTER);
        
        // Create action buttons panel below summary with more height allocation
        JPanel actionPanel = createActionButtonsPanel();
        leftPanel.add(actionPanel, BorderLayout.SOUTH);
        
        contentPanel.add(leftPanel);
        
        // Create transactions panel
        transactionsPanel = createTransactionsPanel();
        contentPanel.add(transactionsPanel);
        
        mainContent.add(contentPanel, BorderLayout.CENTER);
        
        add(mainContent, BorderLayout.CENTER);
        
        // Update data
        updateData();
    }
    
    private JPanel createNavBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(PRIMARY_COLOR);
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_COLOR.darker()));
        
        // Top bar with only logout and refresh buttons
        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        topButtons.setBackground(PRIMARY_COLOR);
        
        // Create navigation buttons
        refreshButton = createNavButton("Refresh");
        logoutButton = createNavButton("Logout");
        
        // Add buttons to the top bar
        topButtons.add(refreshButton);
        topButtons.add(logoutButton);
        
        navBar.add(topButtons, BorderLayout.EAST);
        
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
    
    // Create action buttons panel with a grid layout and bigger buttons
    private JPanel createActionButtonsPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(2, 2, PADDING, PADDING)); // 2x2 grid with padding
        actionPanel.setBackground(CARD_COLOR);
        actionPanel.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
        
        // Create action buttons with consistent color
        savingsButton = createActionButton("Savings Goal", PRIMARY_COLOR);
        budgetButton = createActionButton("Budget", PRIMARY_COLOR);
        transactionButton = createActionButton("Transaction", PRIMARY_COLOR); 
        currencyExchangeButton = createActionButton("Currency Exchange", PRIMARY_COLOR);
        
        // Add buttons to panel in grid layout
        actionPanel.add(savingsButton);
        actionPanel.add(budgetButton);
        actionPanel.add(transactionButton);
        actionPanel.add(currencyExchangeButton);
        
        return actionPanel;
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
    
    public JButton createNavButton(String text) {
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
    
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Bigger padding for larger buttons
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        
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
    
    private void updateData() {
        // Update all data components
        updateBalance();
        updateSavingsGoal();
        updateBudget();
        updateTransaction();
        updateTransactionsList();
    }
    
    private void updateBalance() {
        // Calculate and display the current balance
        double balance = calculateBalance();
        
        // Update the balance display
        updateValueLabel("Current Balance:", currencyFormat.format(balance));
    }
    
    private void updateSavingsGoal() {
        currentGoal = sDao.getSavingsGoal(); // Call the DAO
    
        if (currentGoal == null) {
            updateValueLabel("Savings Goal:", "No savings goal found");
            return;
        }
    
        // Get deposit transactions
        ArrayList<Transaction> depositList = tDao.getDepositTransactions();
    
        double totalDeposits = 0.0;
        if (depositList != null && !depositList.isEmpty()) {
            for (Transaction transaction : depositList) {
                // Check if the transaction category matches the goal's name
                if (transaction.getCategory().equalsIgnoreCase(currentGoal.getName())) {
                    totalDeposits += transaction.getAmount();
                }
            }
        }
    
        double totalSaved = currentGoal.getStartingAmount() + totalDeposits;
        String savingsText = currentGoal.getName() + ": " + 
                        currencyFormat.format(totalSaved) + " / " + 
                        currencyFormat.format(currentGoal.getTargetAmount());
                        
        updateValueLabel("Savings Goal:", savingsText);
    }
    
    private void updateBudget() {
        currentBudget = bDao.getBudgetGoal();
        
        if (currentBudget == null) {
            updateValueLabel("Current Budget:", "No budget found");
            return;
        }
        
        String budgetText = currentBudget.getCategory() + ": " + 
                       currencyFormat.format(currentBudget.getBudgetAmount());
        updateValueLabel("Current Budget:", budgetText);
    }
    
    private void updateTransaction() {
        currentTransaction = tDao.getTransaction();
        
        if (currentTransaction == null) {
            updateValueLabel("Latest Transaction:", "No transactions found");
            return;
        }
        
        String transactionText = currentTransaction.getType() + " - " + 
                            currentTransaction.getCategory() + ": " +
                            currencyFormat.format(currentTransaction.getAmount());
        updateValueLabel("Latest Transaction:", transactionText);
    }
    
    private void updateValueLabel(String labelName, String value) {
        // Find the value label in the summary panel content and update it
        for (Component component : summaryPanel.getComponents()) {
            if (component instanceof JPanel contentPanel) {
                for (Component contentChild : contentPanel.getComponents()) {
                    if (contentChild instanceof JPanel rowPanel) {
                        for (Component rowChild : rowPanel.getComponents()) {
                            if (rowChild instanceof JLabel && labelName.equals(((JLabel) rowChild).getName())) {
                                ((JLabel) rowChild).setText(value);
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
}