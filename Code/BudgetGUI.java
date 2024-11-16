import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BudgetGUI extends JPanel implements ActionListener{
     
    JFrame budgetFrame = new JFrame();
    JButton budgetButton = new JButton("Budget");
    
    //constructor for BudgetGUI
    BudgetGUI(){
        //create button in frame
        budgetButton.setBounds(100,160,200,40); //button dimensions 
        budgetButton.setFocusable(false); //ensures button is not highlighted
        budgetButton.addActionListener(this);  

        //create frame and properties 
        budgetFrame.add(budgetButton);
        budgetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        budgetFrame.setSize(420,420);
        budgetFrame.setLayout(null);
        budgetFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // create instance when savings button is pushed 
        if(e.getSource() == budgetButton){
            budgetFrame.dispose(); //removes the button page after it has been clicked (may need to use singleton instead)
            BudgetWindow myBudgetWindow = new BudgetWindow(); 
        }
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    
}
