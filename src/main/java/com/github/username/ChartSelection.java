package com.github.username;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;

import javax.swing.*;
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Document;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;   

import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Container; 

public class ChartSelection {

	public static String chartdate;
public static JFrame f= new JFrame("Report Selection"); 
public static JButton selectbtn; 
public static  JTextField t1;  
    public static void main(String[] args) {
    t1=new JTextField();  
    t1.setBounds(50,100, 200,30);  
   
	selectbtn = new JButton("Select Chart");
	selectbtn.setBounds(100, 250, 120, 25); 
     f.add(selectbtn);
    f.add(t1); 
    f.setSize(400,400);  
    f.setLayout(null);  
    f.setVisible(true); 

	//copybtn.addActionListener(this);

selectbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            chartdate = t1.getText();
	/*try {

            ComparisonChart.main(null);

        } catch (IOException e1) {
            e1.printStackTrace();
        }*/

ComparisonChart.main(null);

        }
    });	 
        

    }


public static String getDateFile()
    {
        return chartdate;
    }



}