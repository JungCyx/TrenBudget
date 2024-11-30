package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import DAO.SavingsGoalDAO;
import Model.SavingsGoal;
import Model.UserSession;

//import javafx.application.Platform;

public class DashboardGUI extends JPanel implements ActionListener {

    private final JButton savingsButton;
    private final JButton budgetButton;
    private final JButton transactionButton;
    
   

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
            Mainframe.cardLayout.show(Mainframe.mainPanel, "Savings");
        } else if (e.getSource() == budgetButton) {
            Mainframe.cardLayout.show(Mainframe.mainPanel, "Budget");
        } else if (e.getSource() == transactionButton) {
            Mainframe.cardLayout.show(Mainframe.mainPanel, "Transaction");
        }
    }


    //update savings goal
    public void updateSavingsGoal(){
        SavingsGoal goal = SavingsGoalDAO.getCurrentGoal();
        if(goal != null){
            //display pie
        }
        
    }
}
