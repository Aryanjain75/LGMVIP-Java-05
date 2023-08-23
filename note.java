package swingjavatpoint;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.awt.*;  
import java.text.*;  
import java.util.*; 
  
public class note extends JFrame implements ActionListener,Runnable{  
	private JFrame f;  
	private JMenuBar mb;  
	private JMenu file,edit,help,time;  
	private JMenuItem cut,copy,paste,selectAll,open,colormypad,changefontcolor,countwords,b,save,fontLabel;  
	private JTextArea ta;    
	 JScrollPane scrollPane;
	 JSpinner fontSizeSpinner;
	 JButton fontColorButton;
	 JComboBox<String> fontBox;
	 
	 JMenuBar menuBar;
	 JMenu fileMenu;
	 JMenuItem openItem;
	 JMenuItem saveItem;
	 JMenuItem exitItem;
	Thread t=null;  
	int hours=0, minutes=0, seconds=0;  
	String timeString = "";
      
	note(){  
		MyJComponent com = new MyJComponent();  
        // create a basic JFrame  
        JFrame.setDefaultLookAndFeelDecorated(true);
		f=new JFrame();  
		countwords=new JMenuItem("count words");
		colormypad=new JMenuItem("color my pad");
		changefontcolor=new JMenuItem("color the font");
		open=new JMenuItem("Open File");  
		cut=new JMenuItem("cut");  
		copy=new JMenuItem("copy");  
		paste=new JMenuItem("paste");  
		selectAll=new JMenuItem("selectAll");  
		save=new JMenuItem("Save file");
		fontLabel = new JMenuItem("Font: ");


		 t = new Thread((Runnable) this);  
	        t.start();   

		cut.addActionListener(this);  
		copy.addActionListener(this);  
		paste.addActionListener(this);  
		selectAll.addActionListener(this);  
		open.addActionListener(this); 
		colormypad.addActionListener(this);
		changefontcolor.addActionListener(this);
		countwords.addActionListener(this);
		save.addActionListener(this);
		mb=new JMenuBar();  
		mb.setBounds(0,0,500,30); 
		
		
	        
	  
	        
		file=new JMenu("File");  
		edit=new JMenu("Edit");  
		help=new JMenu("Help");  
		time=new JMenu();
		file.add(open);
		file.add(save);
		edit.add(countwords);
		edit.add(changefontcolor);
		edit.add(colormypad);
        edit.addSeparator();  
		edit.add(cut); 
		edit.add(copy);
		edit.add(paste);
		edit.add(selectAll);  
 
		mb.add(file); 
		mb.add(edit); 
		mb.add(help); 
		mb.add(time);
		ta=new JTextArea();  
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setFont(new Font("Arial",Font.PLAIN,20));
		ta.setBounds(5,30,500,500);  
		  
		scrollPane = new JScrollPane(ta);
		scrollPane.setPreferredSize(new Dimension(500,500));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		  
		
		f.add(mb);
		f.add(ta);  
  
		f.setLayout(null);  
		f.setSize(500,500);  
		f.setVisible(true);  
	}  
	 
	 public void run() {  
	      try {  
	         while (true) {  
	  
	            Calendar cal = Calendar.getInstance();  
	            hours = cal.get( Calendar.HOUR_OF_DAY );  
	            if ( hours > 12 ) hours -= 12;  
	            minutes = cal.get( Calendar.MINUTE );  
	            seconds = cal.get( Calendar.SECOND );  
	  
	            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");  
	            Date date = cal.getTime();  
	            timeString = formatter.format( date );  
	  
	            printTime();  
	  
	            t.sleep( 1000 );  // interval given in milliseconds  
	         }  
	      }  
	      catch (Exception e) { }  
	 }  
	  
	public void printTime(){  
	time.setText(timeString);  
	}  
	   void openFile(){  
		   final JFileChooser fileChooser = new JFileChooser();
		   fileChooser.setCurrentDirectory(new File("."));
		   final FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
		   fileChooser.setFileFilter(filter);
		   
		   final int response = fileChooser.showOpenDialog(null);
		   
		   if(response == JFileChooser.APPROVE_OPTION) {
		    final File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
		    Scanner fileIn = null;
		    
		    try {
		     fileIn = new Scanner(file);
		     if(file.isFile()) {
		      while(fileIn.hasNextLine()) {
		       final String line = fileIn.nextLine()+"\n";
		       ta.append(line);
		      }
		     }
		    } catch (final FileNotFoundException e1) {

		     e1.printStackTrace();
		    }
		    finally {
		     fileIn.close();
		    }
		   }
          
}  
  
void displayContent(String fpath){  
try{  
BufferedReader br=new BufferedReader(new FileReader(fpath));  
String s1="",s2="";  
              
while((s1=br.readLine())!=null){  
s2+=s1+"\n";  
}  
ta.setText(s2);  
br.close();  
}catch (Exception e) {e.printStackTrace();  }  
}  
  
  
public void actionPerformed(ActionEvent e) {  
	 String com = e.getActionCommand();  
	  
	 if(e.getSource()==save) {
		   final JFileChooser fileChooser = new JFileChooser();
		   fileChooser.setCurrentDirectory(new File("."));
		   
		   final int response = fileChooser.showSaveDialog(null);
		   
		   if(response == JFileChooser.APPROVE_OPTION) {
		    File file;
		    PrintWriter fileOut = null;
		    
		    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
		    try {
		     fileOut = new PrintWriter(file);
		     fileOut.println(ta.getText());
		    } 
		    catch (final FileNotFoundException e1) {
		     e1.printStackTrace();
		    }
		    finally {
		     fileOut.close();
		    }   
		   }
		  }
	if(e.getSource()==countwords)
	{
		try {
			String text=ta.getText();  
		    String words[]=text.split("\\s");
		    JOptionPane.showMessageDialog(null, "Words:"+words.length+"\n characters:"+text.length()  ,"counting words",JOptionPane.OK_OPTION);
		}
		catch(Exception e1)
		{
			System.out.println(e1);
		}
	}
	if(e.getSource()==colormypad)
	{
		Color c=JColorChooser.showDialog(this,"Choose",Color.CYAN);  
	    ta.setBackground(c);
	}
	if(e.getSource()==changefontcolor)
	{
		Color c=JColorChooser.showDialog(this,"Choose",Color.CYAN);  
	    ta.setForeground(c);
	}
	if(e.getSource()==open){  
		openFile();  
		}  
if(e.getSource()==cut)  
ta.cut();  
if(e.getSource()==paste)  
ta.paste();  
if(e.getSource()==copy)  
ta.copy();  
if(e.getSource()==selectAll)  
ta.selectAll();  
}  
  
public static void main(String[] args) {  
    new note();  
}  
}  