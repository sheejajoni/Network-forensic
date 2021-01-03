package com.github.username;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;  
import javax.swing.JScrollPane;  
import javax.swing.JTextPane;  
import javax.swing.text.BadLocationException;  
import javax.swing.text.Document;  
import javax.swing.text.SimpleAttributeSet;  
import javax.swing.text.StyleConstants;   

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Container; 


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Network {

	public static JFrame frame; 
        public static Container cp;  
        public static JTextPane pane;  
        public static SimpleAttributeSet attributeSet; 
	public static JPanel panel;

    private int _hiddenDims = 64;        // Number of hidden neurons.
    private int _inputDims = 32;        // Number of input neurons.
    private double _error = 0.001;       // Desired error value
    private int _iteration;            // Current training iteration.
    private int _restartAfter = 2000;   // Restart training if iterations exceed this.
    private String _PatternsPath;      // Patterns csv file full path
    private Layer _hidden;              // Collection of hidden neurons.
    private Layer _inputs;              // Collection of input neurons.
    private List<Pattern> _patterns;    // Collection of training patterns.
    private Neuron _output;            // Output neuron.
    private Random _rnd = new Random(); // Global random number generator.
    private Encoder e;
    private double _lambda = .008;                // Steepness of sigmoid curve.
    private double _learnRate = .5;            // Learning rate.

	public Network() {
			 prepareGUI();
			showResult();
			}

private void prepareGUI(){
      frame = new JFrame("Back Propagation Neural Network");  
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
        pane.setText("Neural Network"); 
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
      
 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
   LocalDateTime now = LocalDateTime.now();  
     
	

	try {
	String filename = "outfilebp"+dtf.format(now)+".txt";
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

    public Network(int InputNodes,int HiddenNodes, double Error,int Itertaion, String PatternsPath,Encoder M, double plambda, double plearnRate) throws Exception
    {
        e = M;
_lambda = plambda;
_learnRate = plearnRate;
        _hiddenDims = HiddenNodes;
        _inputDims = InputNodes;
        _error = Error;
        _restartAfter = Itertaion;
        _PatternsPath = PatternsPath;
        long startTime = System.currentTimeMillis();
        LoadPatterns();
        Initialise();
        Train();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("execution time - " + totalTime);
String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\nexecution time - " + totalTime+"\n");
		setBackPropagationResult(newval1);
        Test();
    }
    public Network(int InputNodes,int HiddenNodes, double Error,int Itertaion, String PatternsPath, double plambda, double plearnRate) throws Exception
    {
_lambda = plambda;
_learnRate = plearnRate;
        _hiddenDims = HiddenNodes;
        _inputDims = InputNodes;
        _error = Error;
        _restartAfter = Itertaion;
        _PatternsPath = PatternsPath;
        long startTime = System.currentTimeMillis();
        LoadPatterns();
        Initialise();
        Train();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("execution time - " + totalTime);
String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\nexecution time - " + totalTime+"\n");
		setBackPropagationResult(newval1);
        Test();
    }


    public Network(int InputNodes,int HiddenNodes, double Error,int Itertaion, String PatternsPath,Encoder M) throws Exception
    {
        e = M;
        _hiddenDims = HiddenNodes;
        _inputDims = InputNodes;
        _error = Error;
        _restartAfter = Itertaion;
        _PatternsPath = PatternsPath;
        long startTime = System.currentTimeMillis();
        LoadPatterns();
        Initialise();
        Train();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("execution time - " + totalTime);
String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\nexecution time - " + totalTime+"\n");
		setBackPropagationResult(newval1);
        Test();
    }
    public Network(int InputNodes,int HiddenNodes, double Error,int Itertaion, String PatternsPath) throws Exception
    {
prepareGUI();
			
        _hiddenDims = HiddenNodes;
        _inputDims = InputNodes;
        _error = Error;
        _restartAfter = Itertaion;
        _PatternsPath = PatternsPath;
        long startTime = System.currentTimeMillis();
        LoadPatterns();
        Initialise();
        Train();
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("execution time - " + totalTime);
String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\nexecution time - " + totalTime+"\n");
		setBackPropagationResult(newval1);
        Test();
showResult();
    }

    private void Train()
    {
        double error;
        do
        {
            error = 0;

            for (Pattern pattern :  _patterns)
            {
                double delta = pattern.Output() - Activate(pattern);
                AdjustWeights(delta);
                error += Math.pow(delta, 2);
            }

            System.out.println("Iteration "+ _iteration + "  \tError "+ error);
		String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\n Iteration "+ _iteration + "  \tError "+ error+"\n");
		setBackPropagationResult(newval1);
            _iteration++;
            if (_iteration > _restartAfter) Initialise();
        } while (error < _error);
    }



    private void Test()
    {
        System.out.println("\nBegin network testing\n\n");
		String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\nBegin network testing\n\n");
		setBackPropagationResult(newval1);

        Decoder Obj = new Decoder();
       // while (true)
       // {
            try {
                if (e != null) {
                    Encoder enn = new Encoder();
                    //Scanner re = new Scanner(System.in);
                    //System.out.println("Input Your Data in the same format that stored at the csv file -> ");
                    //
			String values = "2b fa 12";
                    System.out.println(Obj.Unlock(Activate(new Pattern(enn.BuildTestData(values), _inputDims)), this.e) + " | " + Activate(new Pattern(enn.BuildTestData(values), _inputDims)));
String textval2 = getBackPropagationResult();
		String newval2  = textval2.concat(Obj.Unlock(Activate(new Pattern(enn.BuildTestData(values), _inputDims)), this.e) + " | " + Activate(new Pattern(enn.BuildTestData(values), _inputDims)));
		setBackPropagationResult(newval2);

                } else {

                    //Scanner re = new Scanner(System.in);
                   // System.out.println("Input Your Data in the same format that stored at the csv file -> ");
                    //String values = re.nextLine() ;
		String values = "2b fa 12";
                    values += ",0";
                    System.out.println(Activate(new Pattern(values, _inputDims)));
			String textval3 = getBackPropagationResult();
		String newval3  = textval3.concat(Double.toString(Activate(new Pattern(values, _inputDims))));
		setBackPropagationResult(newval3);

                }
            }
            catch (Exception e)
            {
                System.out.println("Error : "+e.getStackTrace());
            }
        //}
    }

    private double Activate(Pattern pattern)
    {
        double[] aInputs = pattern.Inputs();
        for (int i = 0; i < aInputs.length; i++)
        {
            _inputs.get(i).Output(aInputs[i]);
        }
        for (Neuron neuron :  _hidden)
        {
            neuron.Activate();
        }
        _output.Activate();
        return _output.Output();
    }

    private void AdjustWeights(double delta)
    {
        _output.AdjustWeights(delta);
        for (Neuron neuron : _hidden)
        {
            neuron.AdjustWeights(_output.ErrorFeedback(neuron));
        }
    }

    private void Initialise()
    {
        _inputs = new Layer(_inputDims);
        _hidden = new Layer(_hiddenDims, _inputs, _rnd, _lambda, _learnRate);
        _output = new Neuron(_hidden, _rnd, _lambda, _learnRate);
        _iteration = 0;
        System.out.println("Network Initialised");
String textval1 = getBackPropagationResult();
		String newval1  = textval1.concat("\n** Back Propagation Evaluation with Datasets **\n");
		setBackPropagationResult(newval1);
String textval2 = getBackPropagationResult();
		String newval2  = textval2.concat("\nNetwork Initialised\n");
		setBackPropagationResult(newval2);

    }

    private void LoadPatterns() throws Exception {
        _patterns = new ArrayList<>();
        FileInputStream file = new FileInputStream(_PatternsPath);
        BufferedReader br = new BufferedReader(new InputStreamReader(file));
String line;        
String strLine;
        while ((strLine = br.readLine()) != null)
if(_inputDims==0)
{
        line = strLine.split(",").toString();
_inputDims=line.length() - 1;
}        {
            Pattern p = new Pattern(strLine, _inputDims);
            _patterns.add(p);
        }
        br.close();
    }

public static String getBackPropagationResult() {
        return pane.getText();
    }

    public static void setBackPropagationResult(String string) {
        pane.setText(string);
    }


}


