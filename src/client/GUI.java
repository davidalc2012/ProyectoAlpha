/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author agnar
 */


import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GUI extends JFrame implements ItemListener{
    private JFrame frame,frame1;
    private JCheckBox[] checkBoxes;
    private Socket sTCP;
    
    public GUI (Socket sTCP){
        this.sTCP = sTCP;
        frame = new JFrame("Número de Jugador: " + sTCP.getLocalPort());
        frame.setLayout(new GridLayout(3,3));
        checkBoxes = new JCheckBox[9];
        for (int i = 0; i<9; i++){
            checkBoxes[i] = new JCheckBox("hoyo " + (i+1));
            checkBoxes[i].setName(Integer.toString(i+1));
            frame.add(checkBoxes[i]);
            checkBoxes[i].addItemListener(this);
        }
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void itemStateChanged(ItemEvent e){
        try {
            if(((JCheckBox)e.getItem()).isSelected()){
                String name =  ((JCheckBox) e.getItem()).getName()   ;
                DataOutputStream out = new DataOutputStream(sTCP.getOutputStream());
                out.writeUTF(name);
                this.disableAll();
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void disableAll(){
        for (int i = 0; i<9; i++){
            checkBoxes[i].setEnabled(false);
        }
    }
    
    public void enableAll(){
        for (int i = 0; i<9; i++){
            checkBoxes[i].setEnabled(true);
            checkBoxes[i].setSelected(false);
        }
    }

    public void markOne(int number){
        this.enableAll();
        for (int i = 0; i<9; i++){
                checkBoxes[i].setForeground(Color.black);
        } 
        checkBoxes[number-1].setForeground(Color.red);

    } 
    
    public void win(String message){
        this.disableAll();
        JOptionPane.showMessageDialog(null, message);
    }
    
}
