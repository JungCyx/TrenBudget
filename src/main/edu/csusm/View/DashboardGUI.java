package edu.csusm.View;

import edu.csusm.DAO.BudgetGoalDAO;
import edu.csusm.DAO.SavingsGoalDAO;
import edu.csusm.DAO.TransactionDAO;
import edu.csusm.Model.SavingsGoal;
import edu.csusm.Model.Transaction;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.StackPane;
import edu.csusm.Model.BudgetGoal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private StackedBarChart<String, Number> budgetBarChart;
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
        contentPanel.setBackground(java.awt.Color.decode("#f4f4f4")); // Using HEX color code

        add(contentPanel, BorderLayout.CENTER);

        //Chart panel for pie charts
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BoxLayout(chartPanel, BoxLayout.X_AXIS));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(java.awt.Color.decode("#f4f4f4"));


        //Piechart components
        budgetChartPanel = new JFXPanel();
        savingsChartPanel = new JFXPanel();
        transactionChartPanel = new JFXPanel();

        //Size of pieCharts
        budgetChartPanel.setPreferredSize(new Dimension(500, 500));
        savingsChartPanel.setPreferredSize(new Dimension(500, 500));
        transactionChartPanel.setPreferredSize(new Dimension(500, 500));


        // Initialize the pie chart
        initializePieChart(budgetChartPanel, "Budget");
        initializePieChart(savingsChartPanel, "Savings");
        initializePieChart(transactionChartPanel, "Transaction");

        //Add to the panels
        Platform.runLater(() -> {
             budgetChartPanel.setScene(new Scene(new StackPane(budgetBarChart), 500, 400));
             savingsChartPanel.setScene(new Scene(new StackPane(savingsPieChart), 500, 400));
             transactionChartPanel.setScene(new Scene(new StackPane(transactionPieChart), 500, 400));
   
    });
        chartPanel.add(budgetChartPanel);
        chartPanel.add(Box.createHorizontalStrut(10));
        chartPanel.add(savingsChartPanel);
        chartPanel.add(Box.createHorizontalStrut(10));
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
            initializeBudgetBarChart(chartPanel);
            
        }
        else if (title.equals("Savings")) {
            initializeSavingsPieChart(chartPanel);
        } 
        else if (title.equals("Transaction")) {
            initializeTransactionPieChart(chartPanel);
        }

    }

    private void initializeBudgetBarChart(JFXPanel chartPanel) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Categories");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");
        budgetBarChart = new StackedBarChart<>(xAxis, yAxis);
        budgetBarChart.setTitle("Budget Goals Overview");

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
        savingLabel.setText("Your most recently created savings goal was: $" + df.format(currentGoal.getTargetAmount()) + " for " + currentGoal.getName());
        
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
        
        currentGoal.setStartingAmount(currentGoal.getStartingAmount() + totalDeposits);
        
        Platform.runLater(() -> {
            savingsPieChart.getData().clear();
            
            double remainingAmount = Math.max(0, currentGoal.getTargetAmount() - currentGoal.getStartingAmount());
    
            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Saved", currentGoal.getStartingAmount()),
                new PieChart.Data("Remaining", remainingAmount)
            );
    
            
            // Set the pie chart data
            savingsPieChart.setData(pieChartData);
        });
    }
    


    public void updateBudget() {
        currentBudget = bDao.getBudgetGoal(); 
        budgetLabel.setText("Your most recently created budget goal was: $" +
            df.format(currentBudget.getBudgetAmount()) + " for " + currentBudget.getCategory());
        
        // Budget goals from the database
        ArrayList<BudgetGoal> budgetGoalsList = bDao.getBudgetGoalsByCategory();
        ArrayList<Transaction> withdrawalList = tDao.getWithdrawTransactions();
    
        //categories and total budget amounts
        Map<String, Double> categoryBudgets = new HashMap<>();
        Map<String, Double> spentAmounts = new HashMap<>();
    
        //budget amounts for each category
        for (BudgetGoal goal : budgetGoalsList) {
            String category = goal.getCategory();
            double budgetAmount = goal.getBudgetAmount();
            categoryBudgets.put(category, budgetAmount);
        }
    
        //withdrawals for each category
        if (withdrawalList != null && !withdrawalList.isEmpty()) {
            for (Transaction transaction : withdrawalList) {
                String transactionCategory = transaction.getCategory();
                double transactionAmount = transaction.getAmount();
    
                //calculation
                if (categoryBudgets.containsKey(transactionCategory)) {
                    spentAmounts.merge(transactionCategory, transactionAmount, Double::sum);
                }
            }
        }
    
        Platform.runLater(() -> {
            budgetBarChart.getData().clear();
    
            XYChart.Series<String, Number> budgetSeries = new XYChart.Series<>();
            budgetSeries.setName("Spent");
    
            XYChart.Series<String, Number> remainingSeries = new XYChart.Series<>();
            remainingSeries.setName("Remaining");
    
            for (Map.Entry<String, Double> entry : categoryBudgets.entrySet()) {
                String category = entry.getKey();
                double totalBudget = entry.getValue();
                double spent = spentAmounts.getOrDefault(category, 0.0);
                double remaining = Math.max(0, totalBudget - spent);
    
                budgetSeries.getData().add(new XYChart.Data<>(category, spent)); 
                remainingSeries.getData().add(new XYChart.Data<>(category, remaining)); 
            }
            budgetBarChart.getData().addAll(budgetSeries, remainingSeries);
        });
    }
    

    public void updateTransaction(){
        currentTransaction = tDao.getTransaction(); // Call the DAO
        // Update the label to show the latest budget total
        transactionLabel.setText("Your most transaction was: $" + df.format(currentTransaction.getAmount()) + " " + currentTransaction.getType() + " for " + currentTransaction.getCategory());



        // Get the latest 50 withdrawal transactions
    ArrayList<Transaction> transactionsList = tDao.getWithdrawTransactions();

    if (transactionsList != null && !transactionsList.isEmpty()) {
        // Make a map to store the categories and their total amounts
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction transaction : transactionsList) {
            String categoryOfTheTransaction = transaction.getCategory();
            double amountOfTransaction = transaction.getAmount();

            // Add the amounts to the corresponding category
            categoryTotals.put(categoryOfTheTransaction, categoryTotals.getOrDefault(categoryOfTheTransaction, 0.0) + amountOfTransaction);
        }

        // Update the PieChart with categorize data
        Platform.runLater(() -> {
            transactionPieChart.getData().clear();  // Clear existing data
            // Add the new data to the pie chart
            for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                transactionPieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Set the title of the pie chart
            transactionPieChart.setTitle("Past 50 Withdraw Overview");
        });

    } else {
        System.out.println("No withdrawal transactions available.");
    }
}
    

    // DONT CHANGE
    public void createInfoText() {
        currentBudget = bDao.getBudgetGoal();
        currentGoal = sDao.getSavingsGoal();
        currentTransaction = tDao.getTransaction();

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