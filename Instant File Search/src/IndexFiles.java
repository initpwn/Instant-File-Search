package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileVisitor;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.FileSystems;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.filechooser.*;

class Setpaths{
	Db dbObj = new Db();
	
	String dirPath,fileName,driveName,getDriveSQL;
	public Setpaths(){
		 FileVisitor<Path> simpleFileVisitor = new SimpleFileVisitor<Path>() {
		      @Override
		      public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs)throws IOException {
		        dbObj.setDirPath((dir.toFile().getPath()).toString());
		        return FileVisitResult.CONTINUE;
		      }
		       
		      @Override
		      public FileVisitResult visitFile(Path visitedFile,BasicFileAttributes fileAttributes) throws IOException {
	        
		        dbObj.setFileName((visitedFile.getFileName()).toString());
		        return FileVisitResult.CONTINUE;
		      }
		    };
		   
		    FileSystem fileSystem = FileSystems.getDefault();
		    getDriveSQL="select drives from "+dbObj.DBTab1;
		    ResultSet prs=dbObj.selT(getDriveSQL);
		    try {
				while(prs.next()) {
					Path rootPath = fileSystem.getPath(prs.getString(1)+":\\");
					try {
						Files.walkFileTree(rootPath, simpleFileVisitor);
					}
					catch(AccessDeniedException e){
						
					}
					catch (IOException ioe) {
							ioe.printStackTrace();
				     }
				}
			} 
		    catch (Exception e) {
					e.printStackTrace();
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
		for(File path:paths)
		{
		    String path1 = path.toString();
		    String path_desc = fsv.getSystemTypeDescription(path).toString();		   
		     if(path_desc.equals("Local Disk")){
		   			 System.out.println(path1.charAt(0));
		   			 insDrive="insert into "+dbObj.DBTab1+" values('"+path1.charAt(0)+"')";
		   			 dbObj.sop("insetred dirves"+insDrive);
		   			 dbObj.idu(insDrive);
			}
		}
	}
}

public class IndexFiles extends Thread {
	static boolean chkIndex;
	Db dbObj = new Db();
	static int sleepMin =15000;
	public void indexFiles() {		
	}
	public void sleepMinSetter(int i) {
		sleepMin=i;
	}
	public void run() {		
		for(;;) {	
			chkIndex=true;
			String dropTabs="drop table "+dbObj.DBTab1;
			dbObj.sel(dropTabs);
			dropTabs="drop table "+dbObj.DBTab2;
			dbObj.sel(dropTabs);
			new SetDrives();
			new Setpaths();			
			try {
				Thread.currentThread().setPriority(MAX_PRIORITY);
				chkIndex=false;
				Thread.currentThread().sleep(sleepMin);				
			} catch (InterruptedException e) {			
					e.printStackTrace();
			}
		}		
  }
}