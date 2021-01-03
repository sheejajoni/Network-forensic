package com.github.username;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

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

public class CopyDirectory {

	public static String fromDirectory, toToDirectory;
public static JFrame f= new JFrame("Copy Directory"); 
public static JButton copybtn; 
public static  JTextField t1,t2;  
    public static void main(String[] args) {

	
    t1=new JTextField();  
    t1.setBounds(50,100, 200,30);  
    t2=new JTextField(); 
t2.setBounds(50,150, 200,30); 
	copybtn = new JButton("Duplicate");
	copybtn.setBounds(100, 250, 120, 25); 
     f.add(copybtn);
    f.add(t1); f.add(t2);  
    f.setSize(400,400);  
    f.setLayout(null);  
    f.setVisible(true); 

	//copybtn.addActionListener(this);

copybtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            fromDirectory = t1.getText();
	 toToDirectory = t2.getText();

	try {

            copyDirectoryLegacyIO(
                new File(fromDirectory),
                new File(toToDirectory));

        } catch (IOException e1) {
            e1.printStackTrace();
        }
JOptionPane.showMessageDialog(f,"Done");
        System.out.println("Done");
        
        }
    });

	 //String fromDirectory = "C:/Users/HELLO/Documents/Java/Files/";
        //String toToDirectory = "C:/Users/HELLO/Documents/Java/ResultFiles/";

        

    }

public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == copybtn) {
             fromDirectory = t1.getText();
	 toToDirectory = t2.getText();

	try {

            copyDirectoryLegacyIO(
                new File(fromDirectory),
                new File(toToDirectory));

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("Done");
        }
    }


    public static void copyDirectoryLegacyIO(File source, File target)
        throws IOException {

        if (source.isDirectory()) {

            //if directory not exists, create it
            if (!target.exists()) {
                if (target.mkdir()) {
                    System.out.println("Directory copied from "
                            + source + "  to " + target);
                } else {
                    System.err.println("Unable to create directory : " + target);
                }
            }

            // list all the directory contents, file walker
            String[] files = source.list();
            if (files == null) {
                return;
            }

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(source, file);
                File destFile = new File(target, file);
                //recursive copy
                copyDirectoryLegacyIO(srcFile, destFile);
            }

        } else {

            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = null;
            OutputStream out = null;

            try {

                in = new FileInputStream(source);
                out = new FileOutputStream(target);

                byte[] buffer = new byte[1024];

                int length;
                //copy the file content in bytes
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                System.out.println("File copied from " + source + " to " + target);

            } catch (IOException e) {

                System.err.println("IO errors : " + e.getMessage());

            } finally {
                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.close();
                }
            }
        }

    }

}