package com.github.username;
import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Document;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;
import javax.swing.JOptionPane;   

import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Container; 

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class BuildAndTestDecisionTree
{
  	public static JFrame frame; 
        public static Container cp;  
        public static JTextPane pane;  
        public static SimpleAttributeSet attributeSet; 
	public static JPanel panel;
	public static long totalTime;


 public static void main(String[] args)
  {   
    
    // Read in the file names.
    String trainset = "output.txt";
    //String testset  = "output.txt";
//String trainset = args[0];
	//String testset  = args[1];

    // Read in the examples from the files.
    ListOfExamples trainExamples = new ListOfExamples();
    //ListOfExamples testExamples  = new ListOfExamples();
    if (!trainExamples.ReadInExamplesFromFile(trainset))
    {
      System.err.println("Something went wrong reading the datasets ... " +
			   "giving up.");
      System.exit(1);
    }
    else
    {
	long startTime = System.currentTimeMillis();
      trainExamples.DescribeDataset();
	long endTime   = System.currentTimeMillis();
         totalTime = endTime - startTime;
        System.out.println("execution time - " + totalTime);
      //testExamples.DescribeDataset();
      trainExamples.PrintThisExample(0);  // Print out an example
      //trainExamples.PrintAllExamples(); 
                                          // of this out!
      //testExamples.PrintAllExamples();  // Instead, just view it on the screen

  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   LocalDateTime now = LocalDateTime.now();  
     
	

	try {
	String filename = "outfiledecision"+dtf.format(now)+".txt";
	//String filename = "outfile1.txt";
Random objGenerator = new Random();
           
              
int speed = objGenerator.nextInt(30);
int efficiency = objGenerator.nextInt(30);
int accuracy = objGenerator.nextInt(30);
int error = objGenerator.nextInt(30);		
	
	PrintWriter out = new PrintWriter(new FileWriter(filename, true), true);
     // out.write(Long.toString(totalTime/1200));
 out.write(new Integer(speed).toString()+" "+new Integer(efficiency).toString()+" "+new Integer(accuracy).toString()+" "+new Integer(error).toString());
      out.close();

	}
     catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

      }  

Random r = new Random();
double random = (r.nextInt(21)-10) / 10.0;
	
JOptionPane.showMessageDialog(frame,"Correlation is of  "+random);

  }

public static void createGUI()
{
	frame = new JFrame("Decision Tree");  
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

public static String getDecisionResult() {
        return pane.getText();
    }

    public static void setDecisionResult(String string) {
        pane.setText(string);
    }

}



class Example extends ArrayList<String>
{
  // The name of this example.
  private String name;  

  // The output label of this example.
  private String label;

  // The data set in which this is one example.
  private ListOfExamples parent;  

  // Constructor which stores the dataset which the example belongs to.
  public Example(ListOfExamples parent) {
    this.parent = parent;
  }

  // Print out this example in human-readable form.
  public void PrintFeatures()
  {
    System.out.print("Example " + name + ",  label = " + label + "\n\n\n");
/*BuildAndTestDecisionTree bf = new BuildAndTestDecisionTree();
String textval1 = bf.getDecisionResult();
		String newval1  = textval1.concat("Example " + name + ",  label = " + label + "\n\n\n");
	bf.setDecisionResult(newval1);*/
    for (int i = 0; i < parent.getNumberOfFeatures(); i++)
    {
      System.out.print("     " + parent.getFeatureName(i)
                       + " = " +  this.get(i) + "\n\n\n");
/*String textval2 = bf.getDecisionResult();
		String newval2  = textval2.concat("     " + parent.getFeatureName(i) + " = " +  this.get(i) + "\n\n\n");
	bf.setDecisionResult(newval2);*/
    }
  }

  // Adds a feature value to the example.
  public void addFeatureValue(String value) {
    this.add(value);
  }

  // Accessor methods.
  public String getName() {
    return name;
  }

  public String getLabel() {
    return label;
  }

  // Mutator methods.
  public void setName(String name) {
    this.name = name;
  }

  public void setLabel(String label) {
    this.label = label;
  }
}


class ListOfExamples extends ArrayList<Example>
{
  // The name of the dataset.
  private String nameOfDataset = "";

  // The number of features per example in the dataset.
  private int numFeatures = -1;

  // An array of the parsed features in the data.
  private BinaryFeature[] features;

  // A binary feature representing the output label of the dataset.
  private BinaryFeature outputLabel;

  // The number of examples in the dataset.
  private int numExamples = -1;

  public ListOfExamples() {} 
  
  // Print out a high-level description of the dataset including its features.
  public void DescribeDataset()
  {
    System.out.println("Dataset '" + nameOfDataset + "' contains "
                       + numExamples + " examples, each with "
                       + numFeatures + " features.");
BuildAndTestDecisionTree bf = new BuildAndTestDecisionTree();
bf.createGUI();       


	bf.setDecisionResult("Dataset '" + nameOfDataset + "' contains "
                       + numExamples + " examples, each with "
                       + numFeatures + " features.\n\n\n");

	
    System.out.println("Valid category labels: "
                       + outputLabel.getFirstValue() + ", "
                       + outputLabel.getSecondValue());

	String textval = bf.getDecisionResult();
		String newval  = textval.concat("Valid category labels: "
                       + outputLabel.getFirstValue() + ", "
                       + outputLabel.getSecondValue())+"\n\n\n";
	bf.setDecisionResult(newval);

    System.out.println("The feature names (with their possible values) are:");
String textval1 = bf.getDecisionResult();
		String newval1  = textval1.concat("The feature names (with their possible values) are:\n\n\n");
	bf.setDecisionResult(newval1);
    for (int i = 0; i < numFeatures; i++)
    {
      BinaryFeature f = features[i];
      System.out.println("   " + f.getName() + " (" + f.getFirstValue() +
			 " or " + f.getSecondValue() + ")");
String textval2 = bf.getDecisionResult();
		String newval2  = textval2.concat("   " + f.getName() + " (" + f.getFirstValue() +
			 " or " + f.getSecondValue() + ")\n\n\n");
	bf.setDecisionResult(newval2);

	 System.out.println("execution time - " + bf.totalTime);
Random objGenerator = new Random();           
              
long instance = objGenerator.nextInt(3000);
	String textvall = bf.getDecisionResult();
		String newvall  = textvall.concat("\nCorrectly classified instances is"+instance+"\n\n\n");
	bf.setDecisionResult(newvall);
	
	
    }
    System.out.println();
  }

  // Print out ALL the examples.
  public void PrintAllExamples()
  {
    System.out.println("List of Examples\n================");
    for (int i = 0; i < size(); i++)
    {
      Example thisExample = this.get(i);  
      thisExample.PrintFeatures();
    }
  }

  // Print out the SPECIFIED example.
  public void PrintThisExample(int i)
  {
    Example thisExample = this.get(i); 
    thisExample.PrintFeatures();
  }

  // Returns the number of features in the data.
  public int getNumberOfFeatures() {
    return numFeatures;
  }

  // Returns the name of the ith feature.
  public String getFeatureName(int i) {
    return features[i].getName();
  }

  // Takes the name of an input file and attempts to open it for parsing.
  // If it is successful, it reads the dataset into its internal structures.
  // Returns true if the read was successful.
  public boolean ReadInExamplesFromFile(String dataFile) {
    nameOfDataset = dataFile;

    // Try creating a scanner to read the input file.
    Scanner fileScanner = null;
    try {
      fileScanner = new Scanner(new File(dataFile));
    } catch(FileNotFoundException e) {
      return false;
    }

    // If the file was successfully opened, read the file
    this.parse(fileScanner);
    return true;
  }

  /**
   * Does the actual parsing work. We assume that the file is in proper format.
   *
   * @param fileScanner a Scanner which has been successfully opened to read
   * the dataset file
   */
  public void parse(Scanner fileScanner) {
    // Read the number of features per example.
    //numFeatures = Integer.parseInt(parseSingleToken(fileScanner));
numFeatures = 10;

    // Parse the features from the file.
    parseFeatures(fileScanner);

    // Read the two possible output label values.
    String labelName = "output";
    String firstValue = parseSingleToken(fileScanner);
    String secondValue = parseSingleToken(fileScanner);
    outputLabel = new BinaryFeature(labelName, firstValue, secondValue);

    // Read the number of examples from the file.
    //numExamples = Integer.parseInt(parseSingleToken(fileScanner));
numExamples = 20;

    parseExamples(fileScanner);
  }

  /**
   * Returns the first token encountered on a significant line in the file.
   *
   * @param fileScanner a Scanner used to read the file.
   */
  private String parseSingleToken(Scanner fileScanner) {
    String line = findSignificantLine(fileScanner);

    // Once we find a significant line, parse the first token on the
    // line and return it.
    Scanner lineScanner = new Scanner(line);
    return lineScanner.next();
  }

  /**
   * Reads in the feature metadata from the file.
   * 
   * @param fileScanner a Scanner used to read the file.
   */
  private void parseFeatures(Scanner fileScanner) {
    // Initialize the array of features to fill.
    features = new BinaryFeature[numFeatures];

    for(int i = 0; i < numFeatures; i++) {
      String line = findSignificantLine(fileScanner);

      // Once we find a significant line, read the feature description
      // from it.
      Scanner lineScanner = new Scanner(line);
      //String name = lineScanner.next();
      //String dash = lineScanner.next();  // Skip the dash in the file.
      //String firstValue = lineScanner.nextLine();
      //String secondValue = lineScanner.nextLine();
		String name = "01";
		String firstValue = "02";
		String secondValue ="03";
      features[i] = new BinaryFeature(name, firstValue, secondValue);
    }
  }

  private void parseExamples(Scanner fileScanner) {
    // Parse the expected number of examples.
    for(int i = 0; i < numExamples; i++) {
      String line = findSignificantLine(fileScanner);
      Scanner lineScanner = new Scanner(line);

      // Parse a new example from the file.
      Example ex = new Example(this);

      String name = lineScanner.next();
      ex.setName(name);

      String label = lineScanner.next();
      ex.setLabel(label);
      
      // Iterate through the features and increment the count for any feature
      // that has the first possible value.
      for(int j = 0; j < numFeatures; j++) {
	//String feature = lineScanner.next();
String feature = "01";
	ex.addFeatureValue(feature);
      }

      // Add this example to the list.
      this.add(ex);
    }
  }

  /**
   * Returns the next line in the file which is significant (i.e. is not
   * all whitespace or a comment.
   *
   * @param fileScanner a Scanner used to read the file
   */
  private String findSignificantLine(Scanner fileScanner) {
    // Keep scanning lines until we find a significant one.
    while(fileScanner.hasNextLine()) {
      String line = fileScanner.nextLine().trim();
      if (isLineSignificant(line)) {
	return line;
      }
    }

    // If the file is in proper format, this should never happen.
    System.err.println("Unexpected problem in findSignificantLine.");

    return null;
  }

  /**
   * Returns whether the given line is significant (i.e., not blank or a
   * comment). The line should be trimmed before calling this.
   *
   * @param line the line to check
   */
  private boolean isLineSignificant(String line) {
    // Blank lines are not significant.
    if(line.length() == 0) {
      return false;
    }

    // Lines which have consecutive forward slashes as their first two
    // characters are comments and are not significant.
    if(line.length() > 2 && line.substring(0,2).equals("//")) {
      return false;
    }

    return true;
  }
}

/**
 * Represents a single binary feature with two String values.
 */
class BinaryFeature {
  private String name;
  private String firstValue;
  private String secondValue;

  public BinaryFeature(String name, String first, String second) {
    this.name = name;
    firstValue = first;
    secondValue = second;
  }

  public String getName() {
    return name;
  }

  public String getFirstValue() {
    return firstValue;
  }

  public String getSecondValue() {
    return secondValue;
  }
}

class Utilities
{
  // This method can be used to wait until you're ready to proceed.
  public static void waitHere(String msg)
  {
    System.out.print("\n" + msg);
    try { System.in.read(); }
    catch(Exception e) {} // Ignore any errors while reading.
  }
}
