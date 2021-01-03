package com.github.username;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.IOException;

public class TCP extends Frame { 
             TextField tf;
             Button b;

             TCP() {
                       setTitle("IP Address Finder"); 
                       tf = new TextField(); 
                       b = new Button("Find IP");
                       b.setBounds(110,200,60,40); 
                       tf.setBounds(100, 100, 85, 20); 
                       add(b); 
                       add(tf);
                       setLayout(null); 
                       setSize(300,300); 
                       setVisible(true); 
                       
       b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { 
                  try {   
                    tf.setText((InetAddress.getByName(new URL(tf.getText()).getHost())).getHostAddress()); 
                      } catch (Exception e1) { 
                        e1.printStackTrace(); 
                      } 
                   } 
                }); 
             } 
public static void main(String []args) throws IOException { 
             new TCP(); 
      }
}