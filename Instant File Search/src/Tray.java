package src;


import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

 class Tray extends Thread{
	 IndexFiles idxObj = new IndexFiles();
	 MenuItem closeItem ;
	 MenuItem aboutItem;
	 MenuItem messageItem;

	public Tray(){}
  public void run() {
	  Thread.currentThread().setPriority(MIN_PRIORITY);
    if (!SystemTray.isSupported()) {
      System.out.println("SystemTray is not supported");
      return;
    }
    SystemTray tray = SystemTray.getSystemTray();
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage("C:\\Users\\initpwn\\Pictures\\icon_bi.png");
    PopupMenu menu = new PopupMenu();
    messageItem = new MenuItem("Start Index");
    messageItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  IndexFiles obj = new IndexFiles();
    	  obj.start();
      }
    });
    menu.add(messageItem);
    
    aboutItem = new MenuItem("About");
    aboutItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    		 JOptionPane.showMessageDialog(null, "Instant File Search, a small file indexing utility, build on Java.\n"+"<HTML>Repository <FONT color=\\\"#000099\\\"><U>https://github.com/initpwn/Instant-File-Search</U></FONT>\r</HTML>","About Instant File Search",JOptionPane.CLOSED_OPTION);
      }
    });
    menu.add(aboutItem);
    

    closeItem = new MenuItem("Quit");
    closeItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
    		  closeItem.setEnabled(false);
        System.exit(0);
      }
    });
    menu.add(closeItem);
    
    TrayIcon icon = new TrayIcon(image, "Instant File Search", menu);
    icon.setImageAutoSize(true);
    try {
		tray.add(icon);
	} catch (AWTException e1) {
		e1.printStackTrace();
	}
  }
}