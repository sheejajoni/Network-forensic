package com.github.username;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.*;  

public class ComparisonChart extends ApplicationFrame {
static int [] output;

   public ComparisonChart( String applicationTitle , String chartTitle ) {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Features",            
         "Weight",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   
   private CategoryDataset createDataset( ) {
      final String decisiontree = "Decision Tree";        
      final String randomforest = "Random Forest";        
      final String svm = "SVM"; 
      final String nn = "Back Propagation Neural Network";       
      final String speed = "Speed";        
      final String accuracy = "Accuracy";        
      final String efficiency = "Efficiency";        
      final String errorrate = "Error Rate";        
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( ); 

try {
	//Scanner scanner = new Scanner(new File("outfile.txt"));
String decision = "outfiledecision"+ChartSelection.getDateFile()+".txt";
String random = "outfilerandom"+ChartSelection.getDateFile()+".txt";
String support = "outfilesvm"+ChartSelection.getDateFile()+".txt";
String backprop = "outfilebp"+ChartSelection.getDateFile()+".txt";
output = new int [16];
File f = new File(decision); 
  
        // Check if the specified file 
        // Exists or not 
        if (f.exists()) {
           
Scanner scanner = new Scanner(new File(decision));

int i = 0;
while(scanner.hasNextInt()){
   output[i++] = scanner.nextInt();
}
}

else {

output[0]=0;
output[1]=0;
output[2]=0;
output[3]=0;
}

File f1 = new File(random); 
  
        // Check if the specified file 
        // Exists or not 
        if (f1.exists()) {

Scanner scanner1 = new Scanner(new File(random));

int i = 4;
while(scanner1.hasNextInt()){
   output[i++] = scanner1.nextInt();
}
}

else {

output[4]=0;
output[5]=0;
output[6]=0;
output[7]=0;
}

File f2 = new File(svm); 
  
        // Check if the specified file 
        // Exists or not 
        if (f2.exists()) {

Scanner scanner2 = new Scanner(new File(support));

int i = 8;
while(scanner2.hasNextInt()){
   output[i++] = scanner2.nextInt();
}
}
else {

output[8]=0;
output[9]=0;
output[10]=0;
output[11]=0;
}

File f3 = new File(backprop); 
  
        // Check if the specified file 
        // Exists or not 
        if (f3.exists()) {

Scanner scanner3 = new Scanner(new File(backprop));

int i = 12;
while(scanner3.hasNextInt()){
   output[i++] = scanner3.nextInt();
}
}
else {

output[12]=0;
output[13]=0;
output[14]=0;
output[15]=0;
}


}

catch(IOException ex) {
            System.err.println("An IOException was caught!");
            ex.printStackTrace();
        }

//System.out.println(output[5]);

      dataset.addValue( output[0] , decisiontree , speed );        
      dataset.addValue( output[1] , decisiontree , efficiency );        
      dataset.addValue( output[2] , decisiontree , accuracy ); 
      dataset.addValue( output[3] , decisiontree , errorrate );           

      dataset.addValue( output[4] , randomforest , speed );        
      dataset.addValue( output[5] , randomforest , efficiency );       
      dataset.addValue( output[6] , randomforest , accuracy );        
      dataset.addValue( output[7] , randomforest , errorrate );

      dataset.addValue( output[8] , svm , speed );        
      dataset.addValue( output[9] , svm , efficiency );        
      dataset.addValue( output[10] , svm , accuracy );        
      dataset.addValue( output[11] , svm , errorrate ); 

      dataset.addValue( output[12] , nn , speed );        
      dataset.addValue( output[13] , nn , efficiency );        
      dataset.addValue( output[14] , nn , accuracy );        
      dataset.addValue( output[15] , nn , errorrate );              

      return dataset; 
   }
   
   public static void main( String[ ] args ) {
      ComparisonChart chart = new ComparisonChart("Comparison of ML Algorithms", 
         "Comparitive Analysis");

            
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}
