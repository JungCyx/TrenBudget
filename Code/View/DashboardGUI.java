package View;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class DashboardGUI extends JPanel implements ActionListener{
    private final JButton savingsButton;
    private final JButton  budgetButton;
    private final JButton transactionButton;

    public DashboardGUI() {
        //layout for buttons
        setLayout(new FlowLayout(FlowLayout.CENTER, 20,20));

        //create button in frame
        savingsButton = new JButton("Savings Goal");
        savingsButton.setFocusable(false); //ensures button is not highlighted
        savingsButton.addActionListener(this);

        budgetButton = new JButton("Budget");
        budgetButton.setFocusable(false); //ensures button is not highlighted
        budgetButton.addActionListener(this);

        transactionButton = new JButton("Transaction");
        transactionButton.setFocusable(false); //ensures button is not highlighted
        transactionButton.addActionListener(this);


        // Display Current user Budget, SavingGoal and current Spending
        JLabel budget = new JLabel("your current Budget is: " + 10345);
        JLabel saving = new JLabel("your current Saving is: " + 500);
        JLabel spending = new JLabel("your current Spending is: " + 469);
        JLabel transaction = new JLabel("your current Monthly Spending is: " + 1000);

        // Add labels
        add(budget);
        add(saving);
        add(spending);
        add(transaction);

        //Add buttons
        add(savingsButton);
        add(budgetButton);
        add(transactionButton);


    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == savingsButton){
            Mainframe.cardLayout.show(Mainframe.mainPanel, "Savings");
        }
        else if (e.getSource() == budgetButton){
            Mainframe.cardLayout.show(Mainframe.mainPanel, "BudgetWindow");

        }
        else if (e.getSource() == transactionButton){
            Mainframe.cardLayout.show(Mainframe.mainPanel, "TransactionWindow");

        }

    }
}
