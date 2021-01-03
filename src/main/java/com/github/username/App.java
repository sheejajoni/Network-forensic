package com.github.username;

import com.sun.jna.Platform;
import java.io.*;
import java.util.*;
import org.pcap4j.core.NotOpenException;
//import org.pcap4j.core.IllegalRawDataException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapDumper;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapStat;
import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.packet.Packet;
//import org.pcap4j.util.NifSelector;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.pcap4j.core.PcapAddress;
import org.pcap4j.core.Pcaps;


import java.awt.event.*; 
import java.awt.*; 
import javax.swing.*;

import java.awt.CardLayout;

import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.io.BufferedReader;
import java.io.FileReader;

public class App extends JFrame {
private static String LINE_SEPARATOR = System.getProperty("line.separator");

static StringBuilder see = new StringBuilder(200);
static String devices, newdevices;
static String input;
static int count = 0;

static int devindex;

static String[] numbers; 

static String devicelist;

static List<PcapNetworkInterface> nifsdevice;
//static JList<String> listBox;
static JComboBox<String> listBox, decisionBox; 
static JPanel panel = new JPanel();
static JPanel panel1 = new JPanel();

static JProgressBar jb;



  /**
   * @return a PcapNetworkInterface object which represents a selected network interface.
   * @throws IOException if no network interface is available.
   */
public static void compareFiles()
{
try{
BufferedReader reader1 = new BufferedReader(new FileReader("pcap.txt"));
         
        BufferedReader reader2 = new BufferedReader(new FileReader("output.txt"));
         
        String line1 = reader1.readLine();
         
        String line2 = reader2.readLine();
String s = null;
           

            while( (s = reader2.readLine()) != null ){
               if(reader1.readLine().contains(s)){
			count++;
                  System.out.println("datapack"+s);
System.out.println("count val"+count);
}
            }
         
        boolean areEqual = true;
         
        int lineNum = 1;
         
        while (line1 != null || line2 != null)
        {
            if(line1 == null || line2 == null)
            {
                areEqual = false;
                 
                break;
            }
            else if(! line1.equalsIgnoreCase(line2))
            {
                areEqual = false;
                 
                break;
            }
             
            line1 = reader1.readLine();
             
            line2 = reader2.readLine();
             
            lineNum++;
        }
         
        if(areEqual)
        {
            System.out.println("Two files have same content.");
        }
        else
        {
            System.out.println("Two files have different content. They differ at line "+lineNum);
             
            System.out.println("File1 has "+line1+" and File2 has "+line2+" at line "+lineNum);
        }
         
        reader1.close();
         
        reader2.close();
}catch (IOException ex) {
                ex.printStackTrace();
                }

/*try { BufferedReader reader1 = new BufferedReader(new FileReader("pcap.txt"));
            BufferedReader reader2 = new BufferedReader(new FileReader("output.txt"));

            //assuming file1.txt is smaller than file2.txt in terms of no. of lines
            HashSet<String> file1 = new HashSet<String>();

            String s = null;
            while( ( s = reader1.readLine()) != null){
               file1.add(s);
            }

            while( (s = reader2.readLine()) != null ){
               if(file1.contains(s)){
			count++;
                  System.out.println("datapack"+s);
System.out.println("count val"+count);
}
            }
      }
      catch(IOException e){
         System.out.println(e);
      }*/

}

  public final static PcapNetworkInterface selectNetworkInterface() throws IOException {
    List<PcapNetworkInterface> allDevs = null;
    try {
      allDevs = Pcaps.findAllDevs();
    } catch (PcapNativeException e) {
      throw new IOException(e.getMessage());
    }

    if (allDevs == null || allDevs.isEmpty()) {
      throw new IOException("No NIF to capture.");
    }

    showNifList(allDevs);

    return doSelect(allDevs);
  }

  /**
   * @param msg msg
   * @throws IOException if fails to write.
   */
  protected static void write(String msg) throws IOException {
    System.out.print(msg);
  }

  /**
   * @return string
   * @throws IOException if fails to read.
   */
  protected static String read() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    return reader.readLine();
  }

  /**
   * @param nifs nifs
   * @throws IOException if fails to show.
   */
  protected static void showNifList(List<PcapNetworkInterface> nifs) throws IOException {
    StringBuilder sb = new StringBuilder(200);
    int nifIdx = 0;
    for (PcapNetworkInterface nif : nifs) {
      sb.append("NIF[").append(nifIdx).append("]: ").append(nif.getName()).append(LINE_SEPARATOR);

      if (nif.getDescription() != null) {
        sb.append("      : description: ").append(nif.getDescription()).append(LINE_SEPARATOR);
      }

      /*for (LinkLayerAddress addr : nif.getLinkLayerAddresses()) {
        sb.append("      : link layer address: ").append(addr).append(LINE_SEPARATOR);
      }*/

      for (PcapAddress addr : nif.getAddresses()) {
        sb.append("      : address: ").append(addr.getAddress()).append(LINE_SEPARATOR);
      }
      nifIdx++;
    }
    sb.append(LINE_SEPARATOR);
devices = sb.toString();
    write(sb.toString());
  }

  /**
   * @param nifs nifs
   * @return a PcapNetworkInterface object which represents a selected network interface.
   * @throws IOException if fails in something around IO.
   */
  protected static PcapNetworkInterface doSelect(List<PcapNetworkInterface> nifs) throws IOException {
    int nifIdx;
    while (true) {
      write("Select a device number to capture packets, or enter 'q' to quit > ");

      String input;
      if ((input = read()) == null) {
        continue;
      }

      if (input.equals("q")) {
        return null;
      }

      try {
        nifIdx = Integer.parseInt(input);
        if (nifIdx < 0 || nifIdx >= nifs.size()) {
          write("Invalid input." + LINE_SEPARATOR);
          continue;
        } else {
          break;
        }
      } catch (NumberFormatException e) {
        write("Invalid input." + LINE_SEPARATOR);
        continue;
      }
    }

    return nifs.get(nifIdx);
  }
// frame 
    static JFrame f; 
  
    // label to display text 
    static JLabel l;

static long pktreceived, pktdropped, pktdropbyinterface, pktcaptured;

static String pktreceivedS, pktdroppedS, pktdropbyinterfaceS, pktcapturedS;
static String choosedevice;

static PcapNetworkInterface device  = null;

static PcapNetworkInterface device1  = null;

    /*static PcapNetworkInterface getNetworkDevice() {
        PcapNetworkInterface device = null;
        try {

            //device = new NifSelector().selectNetworkInterface();
device = selectNetworkInterface();

System.out.println("nnnnn"+devicelist);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return device;
    }*/
static void createPackets() throws PcapNativeException, NotOpenException
{


        // Open the device and get a handle
        int snapshotLength = 65536; // in bytes   
        int readTimeout = 50; // in milliseconds                   
        final PcapHandle handle;
        handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);
        final PcapDumper dumper = handle.dumpOpen("out.pcap");

        // Set a filter to only listen for tcp packets on port 80 (HTTP)
        String filter = "tcp port 80";
        handle.setFilter(filter, BpfCompileMode.OPTIMIZE);
// Create a listener that defines what to do with the received packets
        PacketListener listener = new PacketListener() {
            @Override
            public void gotPacket(Packet packet) {
                // Print packet information to screen
                System.out.println(handle.getTimestamp());
                System.out.println(packet);
try{
String dataval = packet.toString();
System.out.println("datavalue"+dataval);
PrintWriter out = new PrintWriter(new FileWriter("output.txt", true), true);
      out.write(dataval);
      out.close();
}catch ( IOException ea) {
            ea.printStackTrace();
        }

//compareFiles();

                // Dump packets to file
                try {
                    dumper.dump(packet, handle.getTimestamp());
                } catch (NotOpenException e) {
                    e.printStackTrace();
                }
            }
        };

        // Tell the handle to loop using the listener we created
        try {
            int maxPackets = 50;
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print out handle statistics
        PcapStat stats = handle.getStats();
pktreceivedS = Long. toString(stats.getNumPacketsReceived());
pktdroppedS = Long. toString(stats.getNumPacketsDropped());
pktdropbyinterfaceS = Long. toString(stats.getNumPacketsDroppedByIf());
pktcapturedS = Long. toString(stats.getNumPacketsCaptured());

//System.out.println("Packets received check : " + pktreceived);

        System.out.println("Packets received: " + stats.getNumPacketsReceived());
        System.out.println("Packets dropped: " + stats.getNumPacketsDropped());
        System.out.println("Packets dropped by interface: " + stats.getNumPacketsDroppedByIf());

// Supported by WinPcap only
        if (Platform.isWindows()) {
            System.out.println("Packets captured: " +stats.getNumPacketsCaptured());
        }

        

        // Cleanup when complete
        dumper.close();
        handle.close();
}

    public static void main(String[] args) throws IOException {

	f = new JFrame("Data Packets"); 
  
        // create a label to display text 
        //l = new JLabel(); 

panel.setLayout(new FlowLayout());
        

//String sentence = new String(packet).trim();
//l.setText(sentence); 
final JLabel label=new JLabel(); 
final JLabel lpktreceived=new JLabel(); 
final JLabel lpktdropped=new JLabel(); 
final JLabel lpktdropbyinterface=new JLabel(); 
final JLabel lpktcaptured=new JLabel(); 
final JLabel lpktdroppedT=new JLabel(); 
final JLabel lpktdropbyinterfaceT=new JLabel(); 
final JLabel lpktcapturedT=new JLabel(); 
final JLabel ldevice=new JLabel(); 
final JTextField tdeviceno=new JTextField();
final JLabel tdevicetext=new JLabel();



label.setPreferredSize(new Dimension(250, 100));
label.setForeground(new Color(120, 90, 40));
label.setBackground(new Color(100, 20, 70)); 
label.setBounds(150,100, 250,20);

tdeviceno.setPreferredSize(new Dimension(100, 100));
tdeviceno.setForeground(new Color(120, 90, 40));
//tdeviceno.setBackground(new Color(100, 20, 70)); 
tdeviceno.setBounds(450,100, 50,50); 

tdevicetext.setPreferredSize(new Dimension(100, 100));
tdevicetext.setForeground(new Color(120, 90, 40));
//tdevicetext.setBackground(new Color(100, 20, 70)); 
tdevicetext.setBounds(300,100, 400,50); 

lpktreceived.setPreferredSize(new Dimension(500, 500));
lpktreceived.setForeground(new Color(120, 90, 40));
lpktreceived.setBackground(new Color(100, 20, 70)); 
lpktreceived.setBounds(100,400, 400,100); 

lpktdropped.setPreferredSize(new Dimension(250, 100));
lpktdropped.setForeground(new Color(120, 90, 40));
lpktdropped.setBackground(new Color(100, 20, 70)); 
lpktdropped.setBounds(400,550, 50,100);

lpktdroppedT.setPreferredSize(new Dimension(250, 100));
lpktdroppedT.setForeground(new Color(120, 90, 40));
lpktdroppedT.setBackground(new Color(100, 20, 70)); 
lpktdroppedT.setBounds(100,550, 250,100);  

lpktdropbyinterface.setPreferredSize(new Dimension(250, 100));
lpktdropbyinterface.setForeground(new Color(120, 90, 40));
lpktdropbyinterface.setBackground(new Color(100, 20, 70)); 
lpktdropbyinterface.setBounds(400,650, 50,100); 

lpktdropbyinterfaceT.setPreferredSize(new Dimension(250, 100));
lpktdropbyinterfaceT.setForeground(new Color(120, 90, 40));
lpktdropbyinterfaceT.setBackground(new Color(100, 20, 70)); 
lpktdropbyinterfaceT.setBounds(100,650, 250,100); 

lpktcaptured.setPreferredSize(new Dimension(250, 100));
lpktcaptured.setForeground(new Color(120, 90, 40));
lpktcaptured.setBackground(new Color(100, 20, 70)); 
lpktcaptured.setBounds(400,600, 50,100);

lpktcapturedT.setPreferredSize(new Dimension(250, 100));
lpktcapturedT.setForeground(new Color(120, 90, 40));
lpktcapturedT.setBackground(new Color(100, 20, 70)); 
lpktcapturedT.setBounds(100,600,250,100);








ldevice.setPreferredSize(new Dimension(500, 8500));
ldevice.setForeground(new Color(120, 90, 40));
ldevice.setBackground(new Color(100, 20, 70)); 
ldevice.setBounds(300,500, 650,650);



try{
List<PcapNetworkInterface> allDevs = null;
    try {
      allDevs = Pcaps.findAllDevs();
nifsdevice = allDevs;
    } catch (PcapNativeException e) {
      throw new IOException(e.getMessage());
    }

    if (allDevs == null || allDevs.isEmpty()) {
      throw new IOException("No NIF to capture.");
    }


//next function
StringBuilder sb = new StringBuilder(200);
    int nifIdx = 0;
    for (PcapNetworkInterface nif : allDevs) {
      sb.append("NIF[").append(nifIdx).append("]: ").append(nif.getName()).append(LINE_SEPARATOR);
//numbers[nifIdx] = nif.getName();


	
      if (nif.getDescription() != null) {
        sb.append("      : description: ").append(nif.getDescription()).append(LINE_SEPARATOR);
      }

      /*for (LinkLayerAddress addr : nif.getLinkLayerAddresses()) {
        sb.append("      : link layer address: ").append(addr).append(LINE_SEPARATOR);
      }*/

      for (PcapAddress addr : nif.getAddresses()) {
        sb.append("      : address: ").append(addr.getAddress()).append(LINE_SEPARATOR);
      }



	
      nifIdx++;

    }
    sb.append(LINE_SEPARATOR);
devices = sb.toString();


StringBuilder sb1 = new StringBuilder(200);
    int nifIdx1 = 0;
    for (PcapNetworkInterface nif : allDevs) {
      sb1.append(nif.getName()).append(LINE_SEPARATOR);
	//sb1.append("[").append(nifIdx).append("]: ").append(nif.getName()).append(LINE_SEPARATOR);
      	
      nifIdx1++;

    }
    sb1.append(LINE_SEPARATOR);
newdevices = sb1.toString();
    //write(sb.toString());
//device = selectNetworkInterface();

//listbox


String[] numbers = newdevices.split(LINE_SEPARATOR);
JLabel l= new JLabel("select the device"); 
l.setBounds(100,600, 500,500);
        
  listBox = new JComboBox<>(numbers);
listBox.setBounds(300,200, 350,25);


        
  listBox = new JComboBox<>(numbers);
listBox.setBounds(300,200, 350,25);


      /*listBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      listBox.setVisibleRowCount(-1); // to keep all values visible
      listBox.setSelectedIndex(3);
      listBox.addListSelectionListener(new ListSelectionListener() {		
         @Override
         public void valueChanged(ListSelectionEvent e) {
            JList list = (JList)e.getSource();
 devindex = list.getSelectedIndex();
input = Integer.toString(devindex);			
            JOptionPane.showMessageDialog(f,list.getSelectedIndex());
         }
      });*/

listBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		devindex =listBox.getSelectedIndex();
                input = Integer.toString(devindex);			
            JOptionPane.showMessageDialog(f,"Selected Device  "+listBox.getSelectedIndex());
            }
        });


panel.add(l);
panel.add(listBox);




lpktreceived.setText("<html>"+sb.toString()+"</html>");
//tdevicetext.setText("Enter device number");
}catch (IOException ea) {
            ea.printStackTrace();
        }
  


  final  JButton b=new JButton("START");  
    b.setBounds(50,100,95,30);

JButton b1=new JButton("ANALYSE PACKETS");  
    b1.setBounds(100,100,105,30);

panel.add(tdevicetext);

panel.add(b);
panel.add(b1);
panel.add(label);
//panel.add(lpktreceived);
//panel.add(lpktdropped);
//panel.add(lpktdropbyinterface);
//panel.add(lpktcaptured);
//panel.add(lpktdroppedT);
//panel.add(lpktdropbyinterfaceT);


f.getContentPane().add(BorderLayout.NORTH, panel);
        //frame.getContentPane().add(BorderLayout.NORTH, mb);
        //frame.getContentPane().add(BorderLayout.EAST, ta);


b1.addActionListener(new ActionListener(){  
public void actionPerformed(ActionEvent e){  
           label.setText("Packet capturing process stops");

//gui.analyePackets();  
MenuEx2 m1 = new MenuEx2();

}  
});  

    b.addActionListener(new ActionListener(){  
public void actionPerformed(ActionEvent e){  
            label.setText("Packet capturing process starts"); 
jb=new JProgressBar(0,2000);    
jb.setBounds(40,40,160,30);         
jb.setValue(0);    
jb.setStringPainted(true);
jb.setString("loading started");  
f.add(jb);



  
try{
// The code we had before
 /*try{        
device1 = doSelect(nifsdevice);
}catch (IOException e2) {
            e2.printStackTrace();
        }*/

//next functi0on

int nifIdx = 0;
try{
    while (true) {
      //write("Select a device number to capture packets, or enter 'q' to quit > ");

      //input = tdeviceno.getText().toString();
System.out.println("nput"+input);
System.out.println("You check: " + nifsdevice);


      /*if ((input = read()) == null) {
        continue;
      }

      if (input.equals("q")) {
        //return null;
//System.out.println("oooput"+nifIdx);
      }*/

      try {
        nifIdx = Integer.parseInt(input);
System.out.println("oooput"+nifIdx);
        if (nifIdx < 0 || nifIdx >= nifsdevice.size()) {
          write("Invalid input." + LINE_SEPARATOR);
          continue;
        } else {
          break;
        }
      } catch (NumberFormatException e1) {
        write("Invalid input." + LINE_SEPARATOR);
        continue;
      }
    }

    //return nifs.get(nifIdx);
}catch (IOException ea0) {
            ea0.printStackTrace();
        }
    device = nifsdevice.get(nifIdx);

System.out.println("Youcheck: " + device);

choosedevice = device.toString();
System.out.println("You chose check: " + choosedevice);
        System.out.println("You chose: " + device);

        // New code below here
        if (device == null) {
            System.out.println("No device chosen.");
            System.exit(1);
        }
createPackets();
//compareFiles();	

/*String[] analysisType = {"Decision Tree", "Baysean Network", "Neural Network", "Random Forest"};

JLabel l1= new JLabel("select the classifier"); 
l1.setBounds(600,600, 500,500);

decisionBox = new JComboBox<>(analysisType);
decisionBox.setBounds(800,200, 350,25);
f.add(l1);
f.add(decisionBox); 
decisionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		compareFiles();			
            JOptionPane.showMessageDialog(f,decisionBox.getSelectedIndex());
            }
        });*/


final JPanel panelcard = new JPanel();
      panelcard.setBackground(Color.CYAN);
      panelcard.setSize(400,400);

      CardLayout layout = new CardLayout();
      layout.setHgap(0);
      layout.setVgap(0);
      panel.setLayout(layout);        
      
      JPanel buttonPanel = new JPanel(new FlowLayout());
      buttonPanel.add(new JButton("Analyse Packets"));
      buttonPanel.add(new JButton("Cancel"));    

      JPanel textBoxPanel = new JPanel(new FlowLayout());
      textBoxPanel.add(new JLabel("Number of Packets Received :"));
      textBoxPanel.add(new JLabel(pktreceivedS));

JPanel textBoxPanel1 = new JPanel(new FlowLayout());
	textBoxPanel1.add(new JLabel("Number of Packets Dropped :"));
      textBoxPanel1.add(new JLabel(pktdroppedS));

JPanel textBoxPanel2 = new JPanel(new FlowLayout());

	textBoxPanel2.add(new JLabel("Number of Packets Dropped by Interface:"));
      textBoxPanel2.add(new JLabel(pktdropbyinterfaceS));

JPanel textBoxPanel3 = new JPanel(new FlowLayout());

	textBoxPanel3.add(new JLabel("Number of Packets Captured:"));
      textBoxPanel3.add(new JLabel(pktcapturedS));

	panelcard.add("Button", buttonPanel);
      	panelcard.add("Text", textBoxPanel);
panelcard.add("Text", textBoxPanel1);
panelcard.add("Text", textBoxPanel2);
panelcard.add("Text", textBoxPanel3);

f.getContentPane().add(BorderLayout.CENTER, panelcard);

b.setVisible(false);
listBox.setVisible(false);

//lpktreceived.setText(pktreceivedS);
lpktdroppedT.setText("Number of Packets dropped");

//lpktdropbyinterfaceT.setText("Number of packets dropped by interface");

lpktcapturedT.setText("Number of packets captured");
lpktdropped.setText(pktdroppedS);

//lpktdropbyinterface.setText(pktdropbyinterfaceS);

lpktcaptured.setText(pktcapturedS);





}catch (PcapNativeException | NotOpenException ex) {
            ex.printStackTrace();
        }

        }  
    });

//b.setVisible(false);








//f.add(panel);
//f.add(panel1);

/*f.add(tdeviceno); 
f.add(tdevicetext);     
    f.add(ldevice); f.add(b1);
f.add(b);f.add(label); f.add(lpktreceived);f.add(lpktdropped);f.add(lpktdropbyinterface);f.add(lpktcapturedT);f.add(lpktcaptured);f.add(lpktdroppedT);f.add(lpktdropbyinterfaceT);*/ 

  
        
  
        // set the size of frame 
        f.setSize(300, 300); 
  
        f.show(); 


        

        
    }
}
