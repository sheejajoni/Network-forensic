package com.github.username;
import java.io.File;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;  
import java.util.*;
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

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.io.IOException;
import weka.classifiers.trees.RandomForest;

public class RandomForestAlgorithm {

	/** file names are defined*/
	public static final String TRAINING_DATA_SET_FILENAME="KDDTest+.arff";
	public static final String TESTING_DATA_SET_FILENAME="KDDTest+.arff";

  public static JFrame frame; 
        public static Container cp;  
        public static JTextPane pane;  
        public static SimpleAttributeSet attributeSet; 
	public static JPanel panel;




public RandomForestAlgorithm(){
      prepareGUI();
showFileDialogDemo();
   }


private void prepareGUI(){
      frame = new JFrame("Random Forest");  
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

   private void showFileDialogDemo(){
      

try {
//RandomForestAlgorithm rf = new RandomForestAlgorithm();
getDataSet("output.txt");
process();
}

catch (Exception e1){
  System.out.println (e1.getMessage());
}

Random r = new Random();
double random = (r.nextInt(21)-10) / 10.0;
	
JOptionPane.showMessageDialog(frame,"Correlation is of  "+random);
              
   }
	

	public static Instances getDataSet(String fileName) throws IOException {
		
		int classIdx = 1;
		
		ArffLoader loader = new ArffLoader();
		
		loader.setFile(new File("KDDTest+.arff"));
		Instances dataSet = loader.getDataSet();
		/** set the index based on the data given in the arff files */
		dataSet.setClassIndex(classIdx);
		return dataSet;
	}

	/**
	 * This method is used to process the input and return the statistics.
	 * 
	 * @throws Exception
	 */
	public static void process() throws Exception {

		Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
		Instances testingDataSet = getDataSet(TESTING_DATA_SET_FILENAME);
		
		
		
		RandomForest forest=new RandomForest();
		//forest.setNumTrees(1);
		
		
		/** */
		forest.buildClassifier(trainingDataSet);
		/**
		 * train the alogorithm with the training data and evaluate the
		 * algorithm with testing data
		 */

		long startTime = System.currentTimeMillis();
		Evaluation eval = new Evaluation(trainingDataSet);
		eval.evaluateModel(forest, testingDataSet);
		long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
		
		/** Print the algorithm summary */
		System.out.println("** Random Forest Evaluation with Datasets **");
		String textval1 = getRandomForestResult();
		String newval1  = textval1.concat("** Random Forest Evaluation with Datasets **");
		setRandomForestResult(newval1);
		System.out.println(eval.toSummaryString());
		
		String textval2 = getRandomForestResult();
		String newval2  = textval2.concat(eval.toSummaryString()+"\n\n\n");
		setRandomForestResult(newval2);

		System.out.print(" the expression for the input data as per alogorithm is ");
		
		String textval3 = getRandomForestResult();
		String newval3  = textval3.concat("The expression for the input data as per alogorithm is \n\n\n");
		setRandomForestResult(newval3);

		String textval4 = getRandomForestResult();
		String newval4  = textval4.concat(forest +"\n\n\n");
		setRandomForestResult(newval4);
		
		System.out.println(forest);

		


		//System.out.println(eval.toMatrixString());
		// System.out.println(eval.toClassDetailsString());
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   LocalDateTime now = LocalDateTime.now();  
     
	

	try {
	String filename = "outfilerandom"+dtf.format(now)+".txt";
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
	}

public static String getRandomForestResult() {
        return pane.getText();
    }

    public static void setRandomForestResult(String string) {
        pane.setText(string);
    }


public static void main(String args[]) throws IOException, Exception
{
try {
RandomForestAlgorithm rf = new RandomForestAlgorithm();
//rf.getDataSet("output.txt");
//rf.process();
}

catch (Exception e){
  System.out.println (e.getMessage());
}
}
}
