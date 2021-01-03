package com.github.username;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class MenuEx2 implements ActionListener
{
Frame frame;
MenuBar menuBar;
Menu menu1, menu2; 
MenuItem mItem1, mItem2, mItem3, mItem4, mItem5, mItem6, mItem7;
FileDialog fg;
Label label1;

MenuEx2()
{
frame = new Frame("Classification of Data Packets");

//Creating a menu bar
menuBar= new 	MenuBar();


//Creating first menu
menu1 = new Menu("File");
mItem1 = new MenuItem("Bayseian Classifier");
mItem2 = new MenuItem("Open");
mItem3= new MenuItem("Decision Tree");
mItem4 = new MenuItem("Exit");


//Adding menu items to the  menu
menu1.add(mItem1);
menu1.add(mItem2);
menu1.add(mItem3);
menu1.add(mItem4);


//Creating a second sub-menu
menu2 = new Menu("Save-as");
mItem5 = new MenuItem(".jpeg");
mItem6 = new MenuItem(".png");
mItem7 = new MenuItem(".pdf");

//Adding menu items to the sub-menu
menu2.add(mItem5);
menu2.add(mItem6);
menu2.add(mItem7);

//Adding the sub-menu to the first menu
menu1.add(menu2);

//Adding the exit menu item at the last of menu
menu1.add(mItem4);


//Adding our menu  to the menu bar
menuBar.add(menu1);

//Adding listener to "Open" and "Exit" menu items
mItem2.addActionListener(this);
mItem4.addActionListener(this);

label1 = new Label("", Label.CENTER);

frame.setMenuBar(menuBar);
frame.add(label1,BorderLayout.CENTER);
frame.setSize(370,270);
frame.setVisible(true);
}

public void actionPerformed(ActionEvent ae)
{
if(ae.getActionCommand().equals("Open"))
{

	//Creating an object of FileDialog
	fg = new FileDialog(frame, "Open a file");
	fg.setVisible(true);
	String file = fg.getDirectory()+ fg.getFile();
	label1.setText("Analysing file - " + file);
	label1.setText("");
Random r = new Random();
double random = (r.nextInt(21)-10) / 10.0;
	label1.setText("Correlation is of"+random);
JOptionPane.showMessageDialog(frame,"Correlation is of  "+random);
}


if(ae.getActionCommand().equals("Exit"))
{
System.exit(0);
}
}

public static void main(String... ar)
{
new MenuEx2();
}

}