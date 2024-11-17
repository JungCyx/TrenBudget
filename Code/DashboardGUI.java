import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class DashboardGUI extends JPanel implements ActionListener{
    private JButton savingsButton;
    private JButton  budgetButton;

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

        //add buttons
        add(savingsButton);
        add(budgetButton);
        

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == savingsButton){

            SwingUtilities.getWindowAncestor(DashboardGUI.this).dispose(); //close dashboard window

                    //open savings goal frame with GUI
                    JFrame dashboardFrame = new JFrame("Savings Goal");
                    dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dashboardFrame.setSize(400,400);
                    dashboardFrame.add(new SavingsGUI());
                    dashboardFrame.setLocationRelativeTo(null);
                    dashboardFrame.setVisible(true);

        }
        else if (e.getSource() == budgetButton){
            SwingUtilities.getWindowAncestor(DashboardGUI.this).dispose(); //close dashboard window

            //open budget frame with GUI
            JFrame dashboardFrame = new JFrame("Budget");
            dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dashboardFrame.setSize(400,400);
            dashboardFrame.add(new BudgetGUI());
            dashboardFrame.setLocationRelativeTo(null);
            dashboardFrame.setVisible(true);

        }
    }
}
