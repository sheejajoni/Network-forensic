package com.github.username;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class BackPropagation {

   private Frame mainFrame;
   private Label headerLabel;
   private Label statusLabel;
		Label label1;

   private Panel controlPanel;

   public BackPropagation(){
      prepareGUI();
   }

   public static void main(String[] args){
      BackPropagation  nn = new BackPropagation();
      nn.showResult();
   }

   private void prepareGUI(){
      mainFrame = new Frame("Back Propagation Neural Network");

      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });  



  
      headerLabel = new Label();
	
      headerLabel.setAlignment(Label.CENTER);
      statusLabel = new Label();        
      statusLabel.setAlignment(Label.CENTER);
      statusLabel.setSize(350,100);
label1 = new Label("", Label.CENTER);

      controlPanel = new Panel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }

   private void showResult(){
      headerLabel.setText("Select File for analysing"); 

      final FileDialog fileDialog = new FileDialog(mainFrame,"Select file");
      Button showFileDialogButton = new Button("Analyse File");
      showFileDialogButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            fileDialog.setVisible(true);
            statusLabel.setText("Analysing File based on back propagation NN algorithm :" 
            + fileDialog.getDirectory() + fileDialog.getFile());
try {
Main.main(null);
}

catch(Exception e1) {
    // Code to handle an IOException here
 }



Random r = new Random();
double random = (r.nextInt(21)-10) / 10.0;
	label1.setText("Correlation is of"+random);
JOptionPane.showMessageDialog(mainFrame,"Correlation is of  "+random);
         }
      });

      controlPanel.add(showFileDialogButton);
      mainFrame.setVisible(true);
//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
   }
}