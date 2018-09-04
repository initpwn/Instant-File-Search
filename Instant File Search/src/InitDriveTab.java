package src;

import java.sql.ResultSet;

public class InitDriveTab {
	String str1;
	public void initDrives(char str) {
				Db dbObj = new Db();
				ResultSet rs;
				String str1="select count(*) from tab where tname ='"+str+"'";
				rs=dbObj.sel(str1);
				try {
					while(rs.next()) {					
						if(rs.getInt(1)==0) {							
							str1="insert into "+dbObj.DBTab1+" values('"+str+"')";
							dbObj.sop("insdie initdrive");
							dbObj.idu(str1);
						}
					}
				} catch (Exception e) {
						dbObj.sop(""+e);
				}
		}
}
