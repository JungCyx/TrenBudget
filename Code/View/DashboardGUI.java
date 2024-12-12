package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
        budgetChartPanel.setPreferredSize(new Dimension(400, 400));
        savingsChartPanel.setPreferredSize(new Dimension(400, 400));
        transactionChartPanel.setPreferredSize(new Dimension(400, 400));


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
        
        // Optional: Update the starting amount with total deposits
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
        currentBudget = bDao.getBudgetGoal(); // Call the DAO
        // Update the label to show the latest budget total
        budgetLabel.setText("Your most recently created budget goal was: $" + df.format(currentBudget.getBudgetAmount()) + " for " + currentBudget.getCategory());


        // Fetch the budget goals from the database
        ArrayList<BudgetGoal> budgetGoalsList = bDao.getBudgetGoalsByCategory();
    
        if (budgetGoalsList != null && !budgetGoalsList.isEmpty()) {
            // Map to hold the categories and their total budget amounts
            Map<String, Double> categoryTotals = new HashMap<>();
            for (BudgetGoal goal : budgetGoalsList) {
                String category = goal.getCategory();
                double budgetAmount = goal.getBudgetAmount();
    
                // Add the amounts to the corresponding category
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + budgetAmount);
            }
    
        
    
            // Update the BarChart with categorized data
            Platform.runLater(() -> {
                // Clear the existing data from the bar chart
                budgetBarChart.getData().clear();
    
                // Create series for "Spent" and "Remaining"
                XYChart.Series<String, Number> spentSeries = new XYChart.Series<>();
                spentSeries.setName("Spent");
    
                XYChart.Series<String, Number> remainingSeries = new XYChart.Series<>();
                remainingSeries.setName("Remaining");
    
                // Loop through the categories and add data for each
                for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
                    String category = entry.getKey();
                    double totalBudget = entry.getValue();
                    double spent = 10; // Replace with actual logic for spent amount
                    double remaining = Math.max(0, totalBudget - spent);
    
                    // Add data for the current category
                    spentSeries.getData().add(new XYChart.Data<>(category, spent));
                    remainingSeries.getData().add(new XYChart.Data<>(category, remaining));
                }
    
                // Add the series to the bar chart (stacking the data for each category)
                budgetBarChart.getData().addAll(spentSeries, remainingSeries);
    
                // Apply bar colors for better visualization
                spentSeries.getData().forEach(data -> 
                    data.getNode().setStyle("-fx-bar-fill: red;")); // Spent in red
                remainingSeries.getData().forEach(data -> 
                    data.getNode().setStyle("-fx-bar-fill: green;")); // Remaining in green
            });
    
        } else {
            System.out.println("No budget goals available.");
        }
    }

    public void updateTransaction(){
        currentTransaction = tDao.getTransaction(); // Call the DAO
        // Update the label to show the latest budget total
        transactionLabel.setText("Your most transaction was: $" + df.format(currentTransaction.getAmount()) + " " + currentTransaction.getType() + " for " + currentTransaction.getCategory());



        // Fetch the latest 50 withdrawal transactions
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

      

        // Update the PieChart with categorized data
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