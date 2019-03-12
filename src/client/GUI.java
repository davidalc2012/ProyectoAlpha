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

public class GUI extends JFrame implements ItemListener{
    
    public GUI (){
        JFrame frame = new JFrame("Pégale al monstruo!!!");
        frame.setLayout(new GridLayout(3,3));
        JCheckBox[] checkBoxes = new JCheckBox[9];
        for (int i = 0; i<9; i++){
            checkBoxes[i] = new JCheckBox("hoyo " + i);
            frame.add(checkBoxes[i]);
            checkBoxes[i].addItemListener(this);
        }
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void itemStateChanged(ItemEvent e){
        System.out.println(((JCheckBox) e.getItem()).getText());
    }
}
