package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import DAO.SavingsGoalDAO;
import Model.SavingsGoal;

// import javafx.application.Application;
// import javafx.application.Platform;
// import javafx.embed.swing.JFXPanel;
// import javafx.scene.Scene;
// import javafx.scene.chart.PieChart;
// import javafx.scene.layout.StackPane;
// import javafx.stage.Stage;



public class DashboardGUI extends JPanel implements ActionListener {

    private final JButton savingsButton;
    private final JButton budgetButton;
    private final JButton transactionButton;
    
   

    // private JFXPanel pieChartPanel; 
    // private PieChart pieChart;      


    public DashboardGUI() {

        SavingsGoalDAO sDao = new SavingsGoalDAO();

        // Set layout for the main panel
        setLayout(new BorderLayout());

        // Navigation bar panel
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        navBar.setBackground(new Color(60, 63, 65)); // Modern dark gray

        // Create styled buttons
        savingsButton = createNavButton("Savings Goal");
        budgetButton = createNavButton("Budget");
        transactionButton = createNavButton("Transaction");

        // Add buttons to the navigation bar
        navBar.add(savingsButton);
        navBar.add(budgetButton);
        navBar.add(transactionButton);

        // Add navigation bar to the top
        add(navBar, BorderLayout.NORTH);

        // Content panel for the budget, savings, and transaction details
        JPanel contentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        SavingsGoal currUser = sDao.getSavingsGoal();
        // Create labels for displaying data
        JLabel budgetLabel = new JLabel("Your current budget is: $" + 10345);
        JLabel savingLabel = new JLabel("Your current saving is: $" + currUser.getStartingAmount());
        JLabel transactionLabel = new JLabel("Your current monthly spending is: $" + 1000);

        // Set font for labels
        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        budgetLabel.setFont(labelFont);
        savingLabel.setFont(labelFont);
        transactionLabel.setFont(labelFont);

        // Add labels to the content panel
        contentPanel.add(budgetLabel);
        contentPanel.add(savingLabel);
        contentPanel.add(transactionLabel);

        // Add the content panel to the center
        add(contentPanel, BorderLayout.CENTER);

        // //Piechart components
        // pieChartPanel = new JFXPanel();  
        // add(pieChartPanel, BorderLayout.SOUTH); 

        // // method to create the PieChart
        // initializePieChart();



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
    }


    // // method to create the PieChart
    // private void initializePieChart() {
    //     // Create initial PieChart data
    //     double targetAmount = 1000;   
    //     double startingAmount = 400;  

    //     // Create PieChart slices
    //     PieChart.Data slice1 = new PieChart.Data("Saved", startingAmount);
    //     PieChart.Data slice2 = new PieChart.Data("Remaining", targetAmount - startingAmount);

    //     // Create a PieChart
    //     pieChart = new PieChart();
    //     pieChart.getData().addAll(slice1, slice2);

    //     // Set up the scene for the PieChart
    //     StackPane pieChartLayout = new StackPane();
    //     pieChartLayout.getChildren().add(pieChart);
    //     Scene scene = new Scene(pieChartLayout, 600, 400);

    //     // Initialize the JFXPanel and add the JavaFX scene
    //     pieChartPanel.setScene(scene);
    // }

    // Update the savings goal pie chart with the new values
    // public void updateSavingsGoal() {
    //     SavingsGoal goal = SavingsGoalDAO.getCurrentGoal();  // Get current savings goal from the database
    //     if (goal != null) {
    //         double startingAmount = goal.getStartingAmount();  // Get the current saved amount
    //         double targetAmount = goal.getTargetAmount();    // Get the target amount

            
    //         Platform.runLater(() -> {
    //             // Update pie chart data
    //             PieChart.Data slice1 = new PieChart.Data("Saved", startingAmount);
    //             PieChart.Data slice2 = new PieChart.Data("Remaining", targetAmount - startingAmount);
                
    //             // Clear old data and add the new data
    //             pieChart.getData().clear();
    //             pieChart.getData().addAll(slice1, slice2);
    //         });
    //     }
    // }


}
