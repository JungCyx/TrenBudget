package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import DAO.SavingsGoalDAO;
import Model.BudgetGoal;
import Model.SavingsGoal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DashboardGUI extends JPanel implements ActionListener {

    private final JButton savingsButton;
    private final JButton budgetButton;
    private final JButton transactionButton;
    private final JButton refreshButton;
   

    private JFXPanel pieChartPanel; 
    private PieChart pieChart;  

    private JPanel contentPanel;
    SavingsGoalDAO sDao = new SavingsGoalDAO();

    public DashboardGUI() {

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Navigation bar panel
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navBar.setBackground(new Color(60, 63, 65)); // Modern dark gray

        // Create styled buttons
        savingsButton = createNavButton("Savings Goal");
        budgetButton = createNavButton("Budget");
        transactionButton = createNavButton("Transaction");
        refreshButton = createNavButton("Refresh");

        // Add buttons to the navigation bar
        navBar.add(savingsButton);
        navBar.add(budgetButton);
        navBar.add(transactionButton);
        navBar.add(refreshButton);

        // Add navigation bar to the top
        add(navBar, BorderLayout.NORTH);

        // Content panel for the budget, savings, and transaction details
        contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        JLabel budgetLabel = new JLabel("Your current budget is: $" + 10345);
        JLabel transactionLabel = new JLabel("Your current monthly spending is: $" + 1000);

        // Set font for labels
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        budgetLabel.setFont(labelFont);
        transactionLabel.setFont(labelFont);

        // Add labels to the content panel
        contentPanel.add(budgetLabel);
        contentPanel.add(transactionLabel);

        // Add the content panel to the center
        add(contentPanel, BorderLayout.CENTER);

        //Piechart components
        pieChartPanel = new JFXPanel();  
        add(pieChartPanel, BorderLayout.SOUTH); 

        // Initialize the pie chart
        initializePieChart();

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == savingsButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Savings");
        } else if (e.getSource() == budgetButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Budget");
        } else if (e.getSource() == transactionButton) {
            LoginGUI.cardLayout.show(LoginGUI.mainPanel, "Transaction");
        }
        else if(e.getSource() == refreshButton){
            // Refresh page
            updateSavingsGoal();
        }

    }

    private void initializePieChart() {

        // Create a PieChart
        pieChart = new PieChart();

        // Set up the scene for the PieChart
        StackPane pieChartLayout = new StackPane();

        pieChartLayout.getChildren().add(pieChart);
        Scene scene = new Scene(pieChartLayout, 600, 400);

        // Initialize the JFXPanel and add the JavaFX scene
        pieChartPanel.setScene(scene);

        updateSavingsGoal();
    }

    // Method to update the savings goal and pie chart
    public void updateSavingsGoal() {
        SavingsGoal currentGoal = sDao.getSavingsGoal(); // Call the DAO

        if (currentGoal != null) {
            JLabel savingLabel = new JLabel("Your current saving is: $" + currentGoal.getStartingAmount());
            savingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            contentPanel.add(savingLabel);
            Platform.runLater(() -> {
                pieChart.getData().clear();
                pieChart.getData().addAll(
                    new PieChart.Data("Saved", currentGoal.getStartingAmount()),
                    new PieChart.Data("Remaining", currentGoal.getTargetAmount() - currentGoal.getStartingAmount())
                );
            });
        } 
    }


    public void updateBudget(){
        BudgetGoal currentBudget = sDao.getSavingsGoal(); // Call the DAO

        if (currentGoal != null) {
            JLabel savingLabel = new JLabel("Your current saving is: $" + currentGoal.getStartingAmount());
            savingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            contentPanel.add(savingLabel);
            Platform.runLater(() -> {
                pieChart.getData().clear();
                pieChart.getData().addAll(
                    new PieChart.Data("Saved", currentGoal.getStartingAmount()),
                    new PieChart.Data("Remaining", currentGoal.getTargetAmount() - currentGoal.getStartingAmount())
                );
            });
        } 
    }
    
 }

