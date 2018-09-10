package src;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
public class Db
{
	Connection objCon;
	Statement objSt,smptTemp1;
	String strError;
	String fileName,dirPath;
	String url;
	String UserName,UserPswd;
	String DBTab1,DBTab2;
	String DriverPath;
	//Core coreObj = new Core();
	@SuppressWarnings({ "resource", "resource", "resource" })
	public Db()
	{
		DriverPath="oracle.jdbc.driver.OracleDriver";
		url="jdbc:oracle:thin:@localhost:1521:xe";
		UserName="test";
		UserPswd="test";
		DBTab1="MTAB_FS";
		DBTab2="TAB_FS";
		try
  		{  
			Class.forName(DriverPath);  
			objCon=DriverManager.getConnection(url,UserName,UserPswd);    	
  			objSt = objCon.createStatement(
  				    ResultSet.TYPE_SCROLL_INSENSITIVE,
  				    ResultSet.CONCUR_READ_ONLY
  				);
  			smptTemp1 = objCon.createStatement(
  				    ResultSet.TYPE_SCROLL_INSENSITIVE,
  				    ResultSet.CONCUR_READ_ONLY
  				);
  		}
		catch(Exception e)
		{
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        String fe = sw.toString();
	        DebugConsole.getFullStackTraceToFile("::CRITICAL\n"+fe);
			DebugConsole.dbgWindow.add("E: "+e+"::CRITICAL\n");
			strError = e.toString();
		}
	}
public void setTabs() {
	String str1="select count(*) from tab where tname = '"+DBTab1+"'";
	String str2="select count(*) from tab where tname = '"+DBTab2+"'";
	
	try {
		ResultSet rs=sel(str1);
		while(rs.next()) {					
			if(rs.getInt(1)==0) {
				str1="create table "+DBTab1+" (DRIVES varchar(2) primary key)";
				rs=sel(str1);
				str2="create table "+DBTab2+" (FDRIVE varchar(2) references "+DBTab1+"(DRIVES), \"File Name\" varchar(250),\"File Location\" varchar(250),\"File Type\" varchar(10),\"File Size\" varchar(250))";
				rs=sel(str2);
				sop(str2);
			}
			
		}		
	} catch (Exception e) {
			//I'm super lazy
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String fe = sw.toString();
        DebugConsole.getFullStackTraceToFile("::REGULAR\n"+fe);
		DebugConsole.dbgWindow.add("E: "+e+"::REGULAR\n");
	}
}
	public void idu(String sql)
	{
		try
  		{  
			objSt.executeUpdate(sql);
		}
		catch(Exception e)
		{     
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String fe = sw.toString();
			DebugConsole.getFullStackTraceToFile("::CRITICAL\n"+fe);
			DebugConsole.dbgWindow.add("E: "+e+"::CRITICAL\n");
			strError = e.toString();
		}
	}
	
	public ResultSet selT(String sql)
	{	
		ResultSet objRs = null;
		try
  		{  sop(sql);
			objRs = smptTemp1.executeQuery(sql);	
		}
		catch(Exception e)
		{
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        String fe = sw.toString();
	        DebugConsole.getFullStackTraceToFile("::MINIMAL\n"+fe);
			DebugConsole.dbgWindow.add("E: "+e+"::MINIMAL\n");
			strError = e.toString();
		}
		return objRs;
	}
	
	public ResultSet sel(String sql)
	{	
		ResultSet objRs = null;
		try
  		{  sop(sql);
			objRs = objSt.executeQuery(sql);	
		}
		catch(Exception e)
		{
	        StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
	        String fe = sw.toString();
	        DebugConsole.getFullStackTraceToFile("::MINIMAL\n"+fe);
			DebugConsole.dbgWindow.add("E: "+e+"::MINIMAL\n");
			strError = e.toString();
		}
		return objRs;
	}
	
	public String getError()
	{
		
		return strError;
	}

	public void setFiles(String path,String fileP,String fSize) {
		int strLen = fileP.length();
		int p=-1;
		String ext;
		for (int i = 0 ; i<strLen ; i++)
	        if (fileP.charAt(i) == '.')
	        	p=i;
		if(p==-1)
			ext = "FILE";
		if(p==0)
			ext=fileP.substring(1,strLen);
		else
			ext=fileP.substring(p+1,strLen);
		System.out.println(fileP+": "+path +" ext: "+ext);
		String insFiles = "insert into "+DBTab2+" values('"+path.charAt(0)+"','"+fileP.toLowerCase()+"','"+path+"','"+ext.toUpperCase()+"','"+fSize+"')";
		sop(insFiles);
		idu(insFiles);
	}
	public void insFile(String str1) {
		int strLen = str1.length();
		int p=-1;
		String ext;
		for (int i = 0 ; i<strLen ; i++)
	        if (str1.charAt(i) == '.')
	        	p=i;
		if(p==-1)
			ext = "FILE";
		if(p==0)
			ext=str1.substring(1,strLen);
		else
			ext=str1.substring(p+1,strLen);
		System.out.println(str1+": "+dirPath +" ext: "+ext);
		String insFiles = "insert into "+DBTab2+" values('"+dirPath.charAt(0)+"','"+str1.toLowerCase()+"','"+dirPath+"','"+ext.toUpperCase()+"')";
		idu(insFiles);
	}
	public void sop(String str) {
		System.out.println(str);
	}
}