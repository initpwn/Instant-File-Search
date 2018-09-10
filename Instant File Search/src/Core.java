package src;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;

public class Core extends Thread {	
	public JFrame frame;
	public JTextField textField;
	static public JTable table;
	public static JLabel lblNewLabel;
	int columnCount,colCount,c=0;
	JButton btnSrch;
	JCheckBox chckbxByExtension ;
	ResultSet rs ;
	String usrser;
	String sql_whole;
	String sql_any;
	String sql_contains;
	String sql_byExt;
	String sql_GetDup;
	Db dbObj = new Db();
	private JCheckBox chckbxWhole;
	private JCheckBox chckbxContains;
	private JCheckBox chckbxAny;
	private JCheckBox chckbxGetDuplicates;
	int chkBxNo=0,clkCount=0;
	String createMtab;
	String selDrive;
	String dropTabs;
	static int sleepMin=6000;
	private JTextField textField_1;
	static DefaultTableModel model;
	private JLabel lblNewLabel_1;
	static JScrollPane scrollPane;
	IndexFiles indObj = new IndexFiles();
	private JList list;
	static String FPath;
	@SuppressWarnings({ "deprecation", "static-access" })
	public void run() {
		System.out.println("inside core thread");
		Thread.currentThread().setPriority(MIN_PRIORITY);				
		for(;;) {
			try {
				System.out.println("inside core thread for loop");
				if(indObj.chkIndex) {
					lblNewLabel_1.setText("Indexing process is running... Please wait.");
					btnSrch.setEnabled(false);
					System.out.println("inside core thread for loop bth set false"+ " : "+indObj.chkIndex);
				}
				if(!indObj.chkIndex) {
					System.out.println("inside core thread for loop bth set false"+ " : "+indObj.chkIndex);			
					btnSrch.setEnabled(true);
					lblNewLabel_1.setText("Next indexing in "+indObj.sleepMin/10000+" minutes.");
				}
				Thread.currentThread().sleep(2000);
				} 
				catch (InterruptedException e) {										
					DebugConsole.dbgWindow.add("E: "+e+"\n");
					//e.printStackTrace();
			}					
		}
	}
	
	public Core() {
		frame = new JFrame("Instant File Search");
		model = new DefaultTableModel();
		table = new JTable();
		scrollPane = new JScrollPane();
		frame.setBounds(100, 100, 1237, 638);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// <- prevent closing
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        frame.setExtendedState(JFrame.ICONIFIED);
		    }
		});
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		    	String sep="";
		        JTable table1 =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table1.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table1.getSelectedRow() != -1) {
		        	System.out.println("Row: "+table1.getSelectedRow()+"Col:"+ table1.getSelectedColumn());
		        	if(table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn()+1).toString().charAt((table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn()+1).toString()).length()-1)=='\\') {
	        			sep="";
	        	}else
	        		sep="\\";
		        	if(table1.getSelectedColumn()==0) {
		        		FPath = table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn()+1)+sep+table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn());
		        		System.out.println("path:"+FPath);
		        	}
		        	else
		        	if(table1.getSelectedColumn()==1) {
		        		FPath = ""+table1.getValueAt(table1.getSelectedRow(),table1.getSelectedColumn());
		        		System.out.println("path:"+FPath);
		        	}
		        	else
		        	if(table1.getSelectedColumn()==2) {}
		        	try {
		        	//	FPath=FPath.replaceAll("\\\\", "\\");
		        		String run = "explorer.exe "+"\""+FPath+"\"";
		        		System.out.println(run);
						Runtime.getRuntime().exec(run);
					} 
		        	catch (IOException e) {						
						//e.printStackTrace();
		        		DebugConsole.dbgWindow.add("E: "+e+"\n");
					}
		        	
		        }
		    }
		});
		lblNewLabel = new JLabel(" ");
		frame.setResizable(false);
		textField_1 = new JTextField();
		textField_1.setToolTipText("Enter No. of minutes, the file index should take place.");
		textField_1.setText("6");
		textField_1.setBounds(114, -1, 29, 22);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		textField = new JTextField();
		textField.setToolTipText("Enter the file name.");
		textField.setBounds(12, 36, 416, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);		
		btnSrch = new JButton("Search");
		btnSrch.setToolTipText("Start searching");
		btnSrch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				try {
					String qry = chkUsrOpt();
					rs = dbObj.sel(qry);
					if(rs!=null) {
						rs.last();
						colCount=rs.getRow();
						rs.beforeFirst();
						lblNewLabel.setText(""+colCount+" files found");						
						fillTable(rs);	
					}
				}
				catch (Exception e1) {
					//e1.printStackTrace();		
					DebugConsole.dbgWindow.add("E: "+e+"\n");
				}				
			}
		});							
		btnSrch.setBounds(440, 35, 99, 25);
		frame.getContentPane().add(btnSrch);
		lblNewLabel.setBounds(12, 59, 416, 25);
		frame.getContentPane().add(lblNewLabel);		
		chckbxWhole = new JCheckBox("Exact Match");
		chckbxWhole.setToolTipText("Searches for the exact file name.");
		chckbxWhole.setSelected(true);
		chckbxWhole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkBxNo = 0;
				chckbxContains.setSelected(false);
				chckbxAny.setSelected(false);
				chckbxByExtension.setSelected(false);
				chckbxGetDuplicates.setSelected(false);
			}
		});
		
		chckbxWhole.setBounds(609, -2, 106, 25);
		frame.getContentPane().add(chckbxWhole);		
		chckbxContains = new JCheckBox("File name Contains");
		chckbxContains.setToolTipText("Searches for file name which contains the specified string.");
		chckbxContains.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkBxNo = 1;
				chckbxWhole.setSelected(false);
				chckbxAny.setSelected(false);
				chckbxByExtension.setSelected(false);
				chckbxGetDuplicates.setSelected(false);
			}
		});
		
		chckbxContains.setBounds(609, 28, 174, 25);
		frame.getContentPane().add(chckbxContains);		
		chckbxAny = new JCheckBox("Any Extension");
		chckbxAny.setToolTipText("Searches the specified file which may have multiple extensions.");
		chckbxAny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkBxNo = 2;
				chckbxWhole.setSelected(false);
				chckbxGetDuplicates.setSelected(false);
				chckbxContains.setSelected(false);
				chckbxByExtension.setSelected(false);
			}
		});
		
		chckbxAny.setBounds(609, 59, 110, 25);
		frame.getContentPane().add(chckbxAny);		
		chckbxByExtension = new JCheckBox("By Extension");
		chckbxByExtension.setToolTipText("Searches for the files with specified extension.");
		chckbxByExtension.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkBxNo = 3;
				chckbxWhole.setSelected(false);
				chckbxContains.setSelected(false);
				chckbxAny.setSelected(false);			
				chckbxGetDuplicates.setSelected(false);
			}
		});
								
		chckbxByExtension.setBounds(808, -2, 111, 25);
		frame.getContentPane().add(chckbxByExtension);
		JButton btnIndexEvery = new JButton("Index in next");
		btnIndexEvery.setForeground(Color.BLACK);
		btnIndexEvery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btnIndexEvery.getText().equals(""))
						btnIndexEvery.setText(""+0);
				sleepMin=(Integer.parseInt(textField_1.getText())*10000);
				indObj.sleepMinSetter((int)sleepMin);				
				lblNewLabel_1.setText("Next indexing in "+sleepMin/10000+" minutes.");
			}
		});
		btnIndexEvery.setToolTipText("Start indexing files now.");
		btnIndexEvery.setHorizontalAlignment(SwingConstants.LEFT);
		btnIndexEvery.setBounds(0, -2, 110, 25);
		frame.getContentPane().add(btnIndexEvery);		
		JLabel lblMinutes = new JLabel("Minutes");
		lblMinutes.setBounds(158, 2, 56, 16);
		frame.getContentPane().add(lblMinutes);
		lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(249, 2, 361, 16);
		frame.getContentPane().add(lblNewLabel_1);		
		chckbxGetDuplicates = new JCheckBox("Get Duplicates");		
		chckbxGetDuplicates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chkBxNo = 4;
				textField.setText("");
				chckbxWhole.setSelected(false);
				chckbxContains.setSelected(false);
				chckbxByExtension.setSelected(false);
				chckbxAny.setSelected(false);		
			}
		});
		chckbxGetDuplicates.setBounds(808, 35, 121, 25);
		frame.getContentPane().add(chckbxGetDuplicates);	
	}
	
	public String chkUsrOpt(){
		String usrser1 = textField.getText();
		usrser1=usrser1.replaceAll("[*]", "");
		 sql_whole = "select \"File Name\",\"File Location\",\"File Type\", \"File Size\" from "+dbObj.DBTab2+" where \"File Name\" = '"+usrser1.toLowerCase()+"'";
		 sql_any = "select \"File Name\",\"File Location\",\"File Type\", \"File Size\"  from "+dbObj.DBTab2+" where  \"File Name\" like'"+usrser1.toLowerCase()+"%'";
		 sql_contains = "select \"File Name\",\"File Location\",\"File Type\", \"File Size\" from "+dbObj.DBTab2+" where \"File Name\" like '%"+usrser1.toLowerCase()+"%'";	
		 sql_byExt= "select \"File Name\",\"File Location\",\"File Type\", \"File Size\" from "+dbObj.DBTab2+" where \"File Type\" ='"+usrser1.toUpperCase()+"'";
		 sql_GetDup="select distinct \"File Name\",\"File Location\",\"File Type\", \"File Size\" from "+dbObj.DBTab2+" where \"File Name\" in (select \"File Name\" from "+dbObj.DBTab2+" group by \"File Name\" having count(*)>1) order by 1";
		if(chkBxNo==1) 
			return(sql_contains);		
		if(chkBxNo==2) 
			return(sql_any);			
		if(chkBxNo==0)
			return(sql_whole);
		if(chkBxNo==3)
			return(sql_byExt);
		if(chkBxNo==4) 
			return(sql_GetDup);
		return null;	
	}

	public void fillTable(ResultSet rs) throws SQLException {
		table.setModel(buildTableModel(rs));
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(false);
		table.setDefaultEditor(Object.class, null);
		scrollPane.setBounds(22, 83, 1195, 710);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		int columnIndex;
		model.setRowCount(0);
		table.setModel(model);
	    ResultSetMetaData metaData = rs.getMetaData();	
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    columnNames.add("File Name");
	    columnNames.add("File Location");
	    columnNames.add("File Type");
	    columnNames.add("File Size (Bytes)");
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	            vector.add(rs.getObject(columnIndex));	        
	        data.add(vector);
	    }
	    model = new DefaultTableModel(data, columnNames);
	    rs.close();
	    return model;
	}
}
