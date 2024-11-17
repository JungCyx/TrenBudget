import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SavingsGUI extends JPanel implements ActionListener{
     
    JFrame savingFrame = new JFrame();
    JButton savingButton = new JButton("Savings");
    
    //constructor for SavingsGUI
    SavingsGUI(){

        //create frame and properties 
        savingFrame.add(savingButton);
        savingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        savingFrame.setSize(420,420);
        savingFrame.setLayout(null);
        savingFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // create instance when savings button is pushed 
        if(e.getSource() == savingButton){
            savingFrame.dispose(); //removes the button page after it has been clicked (may need to use singleton instead)
            SavingWindow mySavingsWindow = new SavingWindow(); 
        }
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

    
}
