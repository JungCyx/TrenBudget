import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetGUI extends JPanel implements ActionListener{
     
    JButton budgetButton = new JButton("Budget");
    
    //constructor for BudgetGUI
    BudgetGUI(){
        //create button in frame
        setLayout(new BorderLayout());
        budgetButton.setFocusable(false); //ensures button is not highlighted
        budgetButton.addActionListener(this);  

        //add button to the panel
        add(budgetButton, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // create instance when savings button is pushed 
        if(e.getSource() == budgetButton){
            JOptionPane.showMessageDialog(this, "Opening Budget Window"); 
            Mainframe.cardLayout.show(Mainframe.mainPanel, "Budget");
        }
    }
}
