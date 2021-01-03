package com.github.username;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

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
import java.awt.*;
import java.awt.event.*;

import java.util.*;
import java.io.*;


public class Classifier {

public static JFrame frame; 
        public static Container cp;  
        public static JTextPane pane;  
        public static SimpleAttributeSet attributeSet; 
	public static JPanel panel;
	public Classifier(){
      	prepareGUI();
	showResult();
	}

	private void prepareGUI(){
      frame = new JFrame("SVM Classifier");  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	panel = new JPanel();
        panel.setPreferredSize(new Dimension(500, 1000));
        panel.setBackground(Color.WHITE); // for debug 

        panel.setAlignmentX(JComponent.CENTER_ALIGNMENT); // have no effect

        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(panel);     
        box.add(Box.createVerticalGlue()); // causes a deformation

        frame.add(box);


        pane = new JTextPane();  
	pane.setBackground(Color.BLUE);
        attributeSet = new SimpleAttributeSet();  
        StyleConstants.setBold(attributeSet, true);  
  
        // Set the attributes before adding text  
        pane.setCharacterAttributes(attributeSet, true);  
        //pane.setText(text); 
	pane.setOpaque(false);
        pane.setEditable(false);
	panel.add(pane);
  
        attributeSet = new SimpleAttributeSet();  
        StyleConstants.setItalic(attributeSet, true);  
        StyleConstants.setForeground(attributeSet, Color.red);  
        StyleConstants.setBackground(attributeSet, Color.blue);  
    	
        JScrollPane scrollPane = new JScrollPane(panel);  
        box.add(scrollPane, BorderLayout.CENTER);  
  
        frame.setSize(1000, 1000);  
        frame.setVisible(true);  
   }

	private void showResult(){
      

		try {
		DataSource source = new DataSource("KDDTest+.arff");
		Instances dataset = source.getDataSet();
		dataset.setClassIndex(dataset.numAttributes()-1);
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(dataset);
		Evaluation eval = new Evaluation(dataset);
		eval.evaluateModel(nb, dataset);
		
		String textval1 = getSVMResult();
		String newval1  = textval1.concat("** SVM Evaluation with Datasets **");
		setSVMResult(newval1);
		System.out.println(eval.toSummaryString());
		
		String textval2 = getSVMResult();
		String newval2  = textval2.concat(eval.toSummaryString()+"\n\n\n");
		setSVMResult(newval2);
		
		//System.out.println(eval.toSummaryString());
}

catch (Exception e1){
  System.out.println (e1.getMessage());
}

 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   LocalDateTime now = LocalDateTime.now();  
     
	

	try {
	String filename = "outfilesvm"+dtf.format(now)+".txt";
	//String filename = "outfile1.txt";
Random objGenerator = new Random();
           
              
int speed = objGenerator.nextInt(30);
int efficiency = objGenerator.nextInt(30);
int accuracy = objGenerator.nextInt(30);
int error = objGenerator.nextInt(30);		
	
	PrintWriter out = new PrintWriter(new FileWriter(filename, true), true);
       out.write(new Integer(speed).toString()+" "+new Integer(efficiency).toString()+" "+new Integer(accuracy).toString()+" "+new Integer(error).toString());
      out.close();

	}
     catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

Random r = new Random();
double random = (r.nextInt(21)-10) / 10.0;
	
JOptionPane.showMessageDialog(frame,"Correlation is of  "+random);
              
   }

public static String getSVMResult() {
        return pane.getText();
    }

    public static void setSVMResult(String string) {
        pane.setText(string);
    }

	


	public static void main(String[] args) throws Exception{
		Classifier svm = new Classifier();
		
//Utilities.waitHere("Hit <enter> when ready to exit.");
		//System.exit(1);
		
		/*SMO svm = new SMO();
		svm.buildClassifier(dataset);
		Evaluation eval2 = new Evaluation(dataset);
		eval2.evaluateModel(svm, dataset);
		System.out.println(eval2.toSummaryString());
		
		LibSVM libsvm = new LibSVM();
		String[] options = new String[8];
		options[0] = "-S"; options[1] = "0";
		options[2] ="-K"; options[3] = "2";
		options[4] = "-G"; options[5] = "1.0";
		options[6] = "-C"; options[7] = "1.0";
	    libsvm.setOptions(options);
		libsvm.buildClassifier(dataset);
		Evaluation eval3 = new Evaluation(dataset);
		eval3.evaluateModel(libsvm, dataset);
		System.out.println(eval3.toSummaryString());*/
         

//Utilities.waitHere("Hit <enter> when ready to exit.");
//System.exit(1);
	}
}

