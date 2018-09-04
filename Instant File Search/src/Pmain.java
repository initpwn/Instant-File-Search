package src;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SplashScreen;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.EventQueue;

public class Pmain {

	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {	new startUp();
						
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 
}

class proc{
	public proc() {
		Core window = new Core();
		//window.start();
	//	IndexFiles obj = new IndexFiles();
	//	obj.start();
		window.start();
		Tray tobj = new Tray();
		tobj.start();
		window.frame.setVisible(true);
	}
}



 class startUp extends JWindow {

    static boolean isRegistered;
    private static JProgressBar progressBar = new JProgressBar();
    private static SplashScreen execute;
    private static int count;
    private static Timer timer1;

    public startUp() {
		IndexFiles obj = new IndexFiles();
		obj.start();
        Container container = getContentPane();
        getContentPane().setLayout(null);
        progressBar.setBackground(Color.LIGHT_GRAY);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 204, 102));
        progressBar.setBounds(55, 180, 250, 15);
        progressBar.setMaximum(550);
        container.add(progressBar);        
        JLabel lblInstantFileSearch = new JLabel("Instant File Searcher.");
        lblInstantFileSearch.setForeground(new Color(0, 51, 0));
        lblInstantFileSearch.setBounds(88, 144, 199, 30);
        getContentPane().add(lblInstantFileSearch);
        lblInstantFileSearch.setFont(new Font("Times New Roman", Font.BOLD, 21));                
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\initpwn\\Pictures\\icon_bi.png"));
        lblNewLabel.setLabelFor(progressBar);
        lblNewLabel.setBounds(62, 14, 225, 129);
        getContentPane().add(lblNewLabel);
        loadProgressBar();
        setSize(352, 215);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadProgressBar() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                count++;
                progressBar.setValue(count);
                System.out.println(count);
                if (count == 550) {
                	new proc();
                	dispose();
                    timer1.stop();
                }
            }
            private void createFrame() throws HeadlessException {
                JFrame frame = new JFrame();
                frame.setSize(500, 500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        };
        timer1 = new Timer(50, al);
        timer1.start();
    }

    public static void main(String[] args) {
         new startUp();
    }
};
