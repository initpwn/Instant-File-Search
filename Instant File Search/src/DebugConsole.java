package src;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.List;

public class DebugConsole extends JFrame {
	public static List dbgWindow=new List();
	private JPanel contentPane;
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
		//scrollPane.add(dbgWindow);
		contentPane.add(dbgWindow);
		
	}
}
