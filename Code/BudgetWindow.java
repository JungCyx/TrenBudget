import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;


public class BudgetWindow extends JFrame {

    JFrame budgetFrame = new JFrame ();
    JLabel budgetLabel = new JLabel ("Hello!");

    //constructor
    BudgetWindow(){
    //label dimensions
    budgetLabel.setBounds (0,0,100,50) ;
    budgetLabel.setFont(new Font(null, Font.PLAIN, 25));

    budgetFrame.add(budgetLabel); //add label to frame 

    budgetFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    budgetFrame.setSize(420,420);
    budgetFrame.setLayout(null);
    budgetFrame.setVisible(true);

    }
    
}
