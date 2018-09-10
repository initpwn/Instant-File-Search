package src;

import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

import java.awt.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class DebugConsole extends JFrame {
	public static List dbgWindow=new List();
	public static List dbgWindowFile=new List();	
	private JPanel contentPane;
	public static void getFullStackTraceToFile(String e) {
		dbgWindowFile.add(e);
	}
	public DebugConsole() {
		
		super.setVisible(true);
		super.setTitle("Debug Window");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1201, 716);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1178, 13, -1173, 645);
		contentPane.add(scrollPane);		
		dbgWindow.setMultipleSelections(true);
		dbgWindow.setBounds(10, 42, 1162, 616);
		contentPane.add(dbgWindow);
		JButton btnShowConfigs = new JButton("Show All Config");
		btnShowConfigs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"Database: Oracle 11g XE"+"\nJDBC Class='oracle.jdbc.driver.OracleDriver'\r\n" + 
						"		Connection String='jdbc:oracle:thin:@localhost:1521:xe'\r\n" + 
						"		Database User Name='test' (Explicitly created by DBA)\r\n" + 
						"		Username Password='test' (Explicitly created by DBA)\r\n" + 
						"		Master Table Record='MTAB_FS' (Implicitly created by Application)\r\n" + 
						"		Main Table Record='TAB_FS' (Implicitly created by Application)", "Current Configurations", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnShowConfigs.setBounds(0, 0, 148, 25);
		contentPane.add(btnShowConfigs);
		
		JButton btnSave = new JButton("Save Log File");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text="";
				FileDialog fDialog = new FileDialog((Frame) btnSave.getParent().getParent().getParent().getParent(), "Save", FileDialog.SAVE);
		        fDialog.setVisible(true);
		        String path = fDialog.getDirectory() + fDialog.getFile();		        
		        int l=dbgWindowFile.getItemCount();
		        for(int i =0;i<l;i++) {
		        	text+=dbgWindowFile.getItem(i);
		        }
		        try (PrintWriter out = new PrintWriter(path+".log")) {
		            out.println(text);
		        } catch (FileNotFoundException e1) {					
					//e1.printStackTrace();
			        StringWriter sw = new StringWriter();
			        e1.printStackTrace(new PrintWriter(sw));
			        String fe = sw.toString();
			        getFullStackTraceToFile("::CRITICAL\n"+fe);
					dbgWindow.add("E: "+e1+"::CRITICAL\n");
				}
		        if(!path.equals("nullnull"))
		        	JOptionPane.showMessageDialog(null, "Log file saved "+path+".log\n\nOpen repository issue tracker and upload the log file.", "Log file write successful", JOptionPane.NO_OPTION);
			}
		});
		btnSave.setBounds(173, 0, 111, 25);
		contentPane.add(btnSave);
		
		JButton btnOpen = new JButton("Open Issue Tracker");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					Desktop.getDesktop().browse(new URL("https://github.com/initpwn/Instant-File-Search/issues/").toURI());
				} catch (MalformedURLException e1) {
			        StringWriter sw = new StringWriter();
			        e1.printStackTrace(new PrintWriter(sw));
			        String fe = sw.toString();
			        getFullStackTraceToFile("::CRITICAL\n"+fe);
					dbgWindow.add("E: "+e1+"::CRITICAL\n");
					//e1.printStackTrace();
				} catch (IOException e1) {
			        StringWriter sw = new StringWriter();
			        e1.printStackTrace(new PrintWriter(sw));
			        String fe = sw.toString();
			        getFullStackTraceToFile("::CRITICAL\n"+fe);
					dbgWindow.add("E: "+e1+"::CRITICAL\n");
					//e1.printStackTrace();
				} catch (URISyntaxException e1) {
			        StringWriter sw = new StringWriter();
			        e1.printStackTrace(new PrintWriter(sw));
			        String fe = sw.toString();
			        getFullStackTraceToFile("::CRITICAL\n"+fe);
					dbgWindow.add("E: "+e1+"::CRITICAL\n");
					//e1.printStackTrace();
				}
			}
		});
		btnOpen.setBounds(310, 0, 148, 25);
		contentPane.add(btnOpen);
	}
}
