package View;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;


public class TransactionWindow extends JFrame {

    JFrame transactionFrame = new JFrame ();
    JLabel transactionLabel = new JLabel ("Hello!");

    //constructor
    TransactionWindow(){
    //label dimensions
    transactionLabel.setBounds (0,0,100,50) ;
    transactionLabel.setFont(new Font(null, Font.PLAIN, 25));

    transactionFrame.add(transactionLabel); //add label to frame 

    transactionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    transactionFrame.setSize(420,420);
    transactionFrame.setLayout(null);
    transactionFrame.setVisible(true);

    }
    
}
