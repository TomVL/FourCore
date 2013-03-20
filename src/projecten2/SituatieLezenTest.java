package projecten2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import projecten2.*;

        
public abstract class SituatieLezenTest implements ActionListener{
    
    public static void main(String[] args){
                
        
        JLabel situatieLbl = new JLabel("Situaties: ");
        final JTextArea situatieTArea = new JTextArea(10, 20);
        JButton opvragenBtn = new JButton("Situatie opvragen");
        
        
        JPanel pane = new JPanel();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.anchor = GridBagConstraints.LINE_END;
        
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        pane.add(situatieLbl, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(opvragenBtn, c);
        
        /*c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        pane.add(typeLbl, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(typeCBox, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        pane.add(omschrijvingLbl, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.BOTH;
        pane.add(omschrijvingTArea, c);
        
        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        pane.add(toevoegenBtn, c);
        * */
        
        JFrame frame = new JFrame("Situatie");
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
           
        opvragenBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Situatie situatie = new Situatie();
                
               
  
                System.out.println("opgevraagde situaties:");
                
               DBConnectie connectie = new DBConnectie();
               //situatieTArea.setText(connectie.situatieOpvragen() + "\n");
               /* try {
                 connectie.situatieOpvragen();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(SituatieLezenTest.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
            }  

            
        });
    }

    
    
}
