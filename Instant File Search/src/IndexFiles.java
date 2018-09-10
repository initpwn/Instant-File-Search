package src;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import javax.swing.filechooser.*;

class Setpaths{
	Db dbObj = new Db();	
	String dirPath,fileName,driveName,getDriveSQL;
	static String fp=" ",fs=" ",fz=" ";
	File fileBool;	
	Setpaths(){
		getDriveSQL="select drives from "+dbObj.DBTab1;
	    ResultSet prs=dbObj.selT(getDriveSQL);
	    try {
	    	while(prs.next()) {
				Path path= Paths.get(prs.getString(1)+":\\");
					if(Files.isReadable(path) && Files.isWritable(path)) {
						Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
							@Override	     
							public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
								fileBool = file.toFile();							
									fp=file.getParent().toString();													
									System.out.println("FP: "+file.getParent());
									fs=(file.getFileName()).toString();	 
									fz = ""+(attrs.size());
									dbObj.sop("file size:"+fz);
									dbObj.setFilesTest(fp,fs,fz);
									return FileVisitResult.CONTINUE;																							
							}
						});
					}
	    	}
	    
	    } 
	    catch (Exception e) {
	    	//THIS CODE HATES ACCESS DENIED EXCEPTION.
	    	//e.printStackTrace();	
	    	//SO, PLEASE DON'T EVER EVER HANDLE IT.	    
	   // 	DebugConsole.dbgWindow.add("E: "+e+"\n");
	    }
	 }
}

class SetDrives{
	Db dbObj = new Db();
	public SetDrives(){
		File[] paths;
		String insDrive;
		FileSystemView fsv = FileSystemView.getFileSystemView();
		paths = File.listRoots();
		for(File path:paths){
		    String path1 = path.toString();
		    String path_desc = fsv.getSystemTypeDescription(path).toString();		   
		     if(path_desc.equals("Local Disk")){
		   			 System.out.println(path1.charAt(0));
		   			 if(path1.charAt(0)!='C') {
		   				 insDrive="insert into "+dbObj.DBTab1+" values('"+path1.charAt(0)+"')";
		   				 dbObj.sop("insetred dirves"+insDrive);
		   			 	dbObj.idu(insDrive);
		   			 }
			}
		}
	}
}

public class IndexFiles extends Thread {
	static boolean chkIndex;
	Db dbObj = new Db();
	static int sleepMin =10000;
	public void indexFiles() {		
		//Honestly, It Does Nothing.
	}
	public void sleepMinSetter(int i) {
		sleepMin=i;
	}
	public void run() {		
		for(;;) {	
			chkIndex=true;
			
			String dropTabs="drop table "+dbObj.DBTab2;
			dbObj.sel(dropTabs);			
			dropTabs="drop table "+dbObj.DBTab1;
			dbObj.sel(dropTabs);
			
			dbObj.setTabs();
			new SetDrives();
			new Setpaths();			
			try {
				Thread.currentThread().setPriority(MAX_PRIORITY);
				chkIndex=false;
				Thread.currentThread().sleep(sleepMin);				
			} catch (InterruptedException e) {			
					//e.printStackTrace();
					//Interrupts are out-of-scope exception, so ignored. ^_^
					DebugConsole.dbgWindow.add("E: "+e+"\n");
			}
		}		
  }
}