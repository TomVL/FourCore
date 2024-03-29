package projecten2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

        
public abstract class MeldingTest implements ActionListener{
    
    private static String[] types = {"Gevaarlijke Omstandigheden", "Problemen met het Wegdek", "Vervuiling", "Andere"};
    
    public static void main(String[] args){
        JLabel idMeldingLbl = new JLabel("idMelding: ")      ;
        final JTextField idMeldingTxt = new JTextField(20);
        JLabel titelLbl = new JLabel("Titel: ");
        final JTextField titelTxt = new JTextField(20);
        JLabel typeLbl = new JLabel("Type: ");
        final JComboBox typeCBox = new JComboBox(types);
        //typeCBox.setModel(new DefaultComboBoxModel(SType.values()));
        JLabel omschrijvingLbl = new JLabel("Omschrijving: ");
        final JTextArea omschrijvingTArea = new JTextArea(5, 20);
        JButton toevoegenBtn = new JButton("Toevoegen");
        
        
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
        pane.add(titelLbl, c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        pane.add(titelTxt, c);
        
        c.gridx = 0;
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
        
        JFrame frame = new JFrame("Situatie");
        frame.setContentPane(pane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
           
        toevoegenBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnectie connectie = new DBConnectie();
                connectie.connectDB();
                
            }  

            
        });
    }

    
    
}
