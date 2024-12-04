package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import DAO.BudgetGoalDAO;
import DAO.SavingsGoalDAO;
import DAO.TransactionDAO;
import Model.BudgetGoal;
import Model.SavingsGoal;
import Model.Transaction;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;




public class DashboardGUI extends JPanel implements ActionListener {

    private final JButton savingsButton;
    private final JButton budgetButton;
    private final JButton transactionButton;
    private final JButton refreshButton;
    private final JButton logoutButton;

    
    private JLabel budgetLabel;
    private JLabel savingLabel;
    private JLabel transactionLabel; 
   
 

    private JFXPanel budgetChartPanel;
    private JFXPanel savingsChartPanel;
    private JFXPanel transactionChartPanel;
    private JPanel contentPanel;

    private PieChart budgetPieChart;
    private PieChart savingsPieChart;
    private PieChart transactionPieChart;

    SavingsGoalDAO sDao = new SavingsGoalDAO();
    BudgetGoalDAO bDao = new BudgetGoalDAO();
    TransactionDAO tDao = new TransactionDAO();
    
    BudgetGoal currentBudget;
    SavingsGoal currentGoal;
    Transaction currentTransaction;

    DecimalFormat df = new DecimalFormat("#");

    public DashboardGUI() {

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Navigation bar panel
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navBar.setBackground(new Color(38, 120, 190));

        // Create styled buttons
        savingsButton = createNavButton("Savings Goal");
        budgetButton = createNavButton("Budget");
        transactionButton = createNavButton("Transaction");
        refreshButton = createNavButton("Refresh");
        logoutButton = createNavButton("Logout");


        // Add buttons to the navigation bar
        navBar.add(savingsButton);
        navBar.add(budgetButton);
        navBar.add(transactionButton);
        navBar.add(refreshButton);
        navBar.add(logoutButton);


        // Add navigation bar to the top
        add(navBar, BorderLayout.NORTH);

        // Content panel for the budget, savings, and transaction details
        contentPanel = new JPanel(new GridLayout(3,1,10,10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        //Chart panel for pie charts
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.WHITE);


        //Piechart components
        budgetChartPanel = new JFXPanel();
        savingsChartPanel = new JFXPanel();
        transactionChartPanel = new JFXPanel();

        //Size of pieCharts
        budgetChartPanel.setPreferredSize(new Dimension(200, 200));
        savingsChartPanel.setPreferredSize(new Dimension(200, 200));
        transactionChartPanel.setPreferredSize(new Dimension(200, 200));

        // Initialize the pie chart
        initializePieChart(budgetChartPanel, "Budget");
        initializePieChart(savingsChartPanel, "Savings");
        initializePieChart(transactionChartPanel, "Transaction");

        //Add to the panels
        Platform.runLater(() -> {
             budgetChartPanel.setScene(new Scene(new StackPane(budgetPieChart), 600, 400));
            savingsChartPanel.setScene(new Scene(new StackPane(savingsPieChart), 600, 400));
             transactionChartPanel.setScene(new Scene(new StackPane(transactionPieChart), 600, 400));
   
    });

        chartPanel.add(budgetChartPanel);
        chartPanel.add(Box.createHorizontalStrut(20));
        chartPanel.add(savingsChartPanel);
        chartPanel.add(Box.createHorizontalStrut(20));
        chartPanel.add(transactionChartPanel);
        add(chartPanel, BorderLayout.SOUTH);

        createInfoText();
        updateSavingsGoal();
        updateBudget();
        updateTransaction();
    }
    
    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusable(false);
        button.setBackground(new Color(38, 120, 190)); // Modern blue
        button.setForeground(Color.WHITE); // White text
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.addActionListener(this);
        return button;
    }
    // DONT CHANGE
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == savingsButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Savings");
        } else if (e.getSource() == budgetButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Budget");
        } else if (e.getSource() == transactionButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Transaction");
        }
        else if (e.getSource() == logoutButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Login");
        }
        else if(e.getSource() == refreshButton){
            // Refresh page
            updateSavingsGoal();
            updateBudget();
            updateTransaction();
        }


    }

    //Assign each pie chart
    private void initializePieChart(JFXPanel chartPanel, String title) {
        if(title.equals("Budget")){
            initializeBudgetPieChart(chartPanel);
            
        }
        else if (title.equals("Savings")) {
            initializeSavingsPieChart(chartPanel);
        } 
        else if (title.equals("Transaction")) {
            initializeTransactionPieChart(chartPanel);
        }

    }

    private void initializeBudgetPieChart(JFXPanel chartPanel) {
        budgetPieChart = new PieChart();
        budgetPieChart.setTitle("Budget Overview");

}
    
    private void initializeSavingsPieChart(JFXPanel chartPanel) {
        savingsPieChart = new PieChart();
        savingsPieChart.setTitle("Savings Overview");
    }
    
    private void initializeTransactionPieChart(JFXPanel chartPanel) {
        transactionPieChart = new PieChart();
        transactionPieChart.setTitle("Transaction Overview");
    }
    

    // DONT CHANGE
    // Method to update the savings goal and pie chart
    public void updateSavingsGoal() {
        currentGoal = sDao.getSavingsGoal(); // Call the DAO
        savingLabel.setText("Your current saving is: $" + df.format(currentGoal.getStartingAmount()));

        if (currentGoal != null) {
            Platform.runLater(() -> {
                savingsPieChart.getData().clear();
                savingsPieChart.getData().addAll(
                    new PieChart.Data("Saved", currentGoal.getStartingAmount()),
                    new PieChart.Data("Remaining", Math.max(0, currentGoal.getTargetAmount() - currentGoal.getStartingAmount()))
                );
            });
        } else {
            System.out.println(currentGoal.getName());
        }
    }


    public void updateBudget(){
        currentBudget = bDao.getBudgetGoal();
        budgetLabel.setText("Your current budget is: $" + df.format(currentBudget.getBudgetAmount()));


        if (currentBudget != null) {
            Platform.runLater(() -> {
                budgetPieChart.getData().clear();
                budgetPieChart.getData().addAll(
                    new PieChart.Data("Max Amount", currentBudget.getBudgetAmount()),
                    new PieChart.Data("Spent", Math.max(0,100 - currentBudget.getBudgetAmount())) // we have to include transactiosn here 

                    
                );
            });
        } else{
            System.out.println("No budget data available");
        }
    }

    public void updateTransaction() {
        currentTransaction = tDao.getTransactionl();
        transactionLabel.setText("Your current Transaction is: $" + df.format(currentTransaction.getAmount()));

        if (currentTransaction != null && currentBudget != null) {
            Platform.runLater(() -> {
                transactionPieChart.getData().clear();
                transactionPieChart.getData().addAll(
                        new PieChart.Data("Transaction Amount", currentTransaction.getAmount()),
                        new PieChart.Data("Remaining Budget", Math.max(0, currentBudget.getBudgetAmount() - currentTransaction.getAmount()))
                );
            });
        } else {
            System.out.println("No transaction data available");
        }
    }

    // DONT CHANGE
    public void createInfoText() {
        currentBudget = bDao.getBudgetGoal();
        currentGoal = sDao.getSavingsGoal();
        currentTransaction = tDao.getTransactionl();

        budgetLabel = new JLabel("Your current budget is: $" + df.format(currentBudget.getBudgetAmount()));
        budgetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(budgetLabel);

        savingLabel = new JLabel("Your current saving is: $" + df.format(currentGoal.getStartingAmount()));
        savingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPanel.add(savingLabel);

        transactionLabel = new JLabel("Your current Transaction is: $" + df.format(currentTransaction.getAmount()));
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        transactionLabel.setFont(labelFont);
        contentPanel.add(transactionLabel);
    }
    
 }