package com.github.username;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class MainInterface implements ActionListener
{
JFrame frame;
JMenuBar menuBar;
JMenu menu1, menu2, menu3, menu4, menu5, menu6, menu7; 
JMenuItem tcp, udp, icmp, exit;
JMenuItem startcapture;
JMenuItem decisiontree, svm, randomforest, backpropneuralnetwork;
JMenuItem report;
JMenuItem email;
JMenuItem registry;
JMenuItem duplication;


MainInterface()
{
frame = new JFrame("Proactive Analysis of Data Packets");
try {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

} catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }
//Creating a menu bar
menuBar= new JMenuBar();
menuBar.setBackground(Color.BLUE);
menuBar.setForeground(Color.white);
//UIManager.put("Menu.foreground", Color.WHITE);
UIManager.put("MenuBar.background", Color.BLUE);
UIManager.put("MenuBar.selectionBackground",Color.BLUE);

	UIManager.put("MenuBar.selectionForeground",Color.WHITE);
menuBar.setPreferredSize(new Dimension(1000,50));


//Creating first menu
menu1 = new JMenu("Create Packets");
tcp = new JMenuItem("TCP");
udp = new JMenuItem("UDP");
icmp= new JMenuItem("ICMP");
exit = new JMenuItem("Exit");


//Adding menu items to the  menu
menu1.add(tcp);
menu1.add(udp);
menu1.add(icmp);
menu1.add(exit);


//Creating a second sub-menu
menu2 = new JMenu("Capture packets");
startcapture = new JMenuItem("Start Capture");
menu2.add(startcapture);

//Adding menu items to the sub-menu

menu3 = new JMenu("Classify Packets");
decisiontree = new JMenuItem("Decision Tree");

randomforest = new JMenuItem("Random Forest");
svm = new JMenuItem("SVM");
backpropneuralnetwork = new JMenuItem("Back Propagation Neural Network");
menu3.add(decisiontree);

menu3.add(randomforest);

menu3.add(svm);

menu3.add(backpropneuralnetwork);

menu4 = new JMenu("Report Generation");
report =new  JMenuItem("Report Generation");
menu4.add(report);


menu5 = new JMenu("Email");
email = new JMenuItem ("send email");
menu5.add(email);

menu6 = new  JMenu("Registry");
registry =new JMenuItem("search");
menu6.add(registry);

menu7  = new  JMenu("Forensic Duplication");
duplication = new JMenuItem("Duplicate");
menu7.add(duplication);


//Adding our menu  to the menu bar
menuBar.add(menu1);
menuBar.add(menu2);
menuBar.add(menu3);
menuBar.add(menu4);
menuBar.add(menu5);
menuBar.add(menu6);
menuBar.add(menu7);


//Adding listener to  menu items

tcp.addActionListener(this);
udp.addActionListener(this);
icmp.addActionListener(this);
exit.addActionListener(this);
startcapture.addActionListener(this);
decisiontree.addActionListener(this);
svm.addActionListener(this);
randomforest.addActionListener(this);
backpropneuralnetwork.addActionListener(this);
report.addActionListener(this);
email.addActionListener(this);
registry.addActionListener(this);
duplication.addActionListener(this);





frame.setLayout(new BorderLayout());
try{
BufferedImage img = ImageIO.read(new File("network.png"));
        frame.add(new JLabel(new ImageIcon(img)));
}
catch(IOException e) {
  e.printStackTrace();
}

frame.setJMenuBar(menuBar);

frame.setSize(370,270);
frame.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{

if(ae.getActionCommand().equals("TCP"))
{
try{
	TCP.main(null);

}

catch(IOException e) {
  e.printStackTrace();
}
}

if(ae.getActionCommand().equals("Start Capture"))
{
try{
	App.main(null);

}

catch(IOException e) {
  e.printStackTrace();
}

}



if(ae.getActionCommand().equals("Decision Tree"))
{
	BuildAndTestDecisionTree.main(null);

}


if(ae.getActionCommand().equals("Random Forest"))
{
	try {
	RandomForestAlgorithm.main(null);

		}

	catch(IOException e) {
    // Code to handle an IOException here
 }

catch(Exception e) {
    // Code to handle an IOException here
 }

}



if(ae.getActionCommand().equals("SVM"))
{
	try {
	Classifier.main(null);
//System.exit(1);
		}

	catch(IOException e) {
    // Code to handle an IOException here
 }

catch(Exception e) {
    // Code to handle an IOException here
 }

}


if(ae.getActionCommand().equals("Back Propagation Neural Network"))
{
	//try {
	Main.main(null);
		//}

	//catch(IOException e) {
    // Code to handle an IOException here
/// }

//catch(Exception e) {
    // Code to handle an IOException here
// }

}


if(ae.getActionCommand().equals("Report Generation"))
{
	//GraphPanel.main(null);
	ChartSelection.main(null);

}

if(ae.getActionCommand().equals("Duplicate"))
{
	//GraphPanel.main(null);
	CopyDirectory.main(null);

}


if(ae.getActionCommand().equals("Exit"))
{
System.exit(0);
}
}

public static void main(String args[])
{
new MainInterface();
}

}