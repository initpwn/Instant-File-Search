# Instant-File-Search

Another simple file search utility based of indexing, build on Java.

# THIS PROJECT IS NO LONGER ACTIVELY MAINTAINED.

This software is released under the MIT license.

Copyright (c) 2018, Joseph Philip

* Make sure that you're using a Windows NT machine.
* Install Oracle 11g XE.
* Create a user & make appropriate changes in src/Db.java.
* Compile with java compiler.

***

Before moving forward, please make sure that the config in src/Db file is compatible with your settings.

Current settings in config file:

0) Database: Oracle 11g XE.

src/Db,java:
1) DriverPath="oracle.jdbc.driver.OracleDriver";
2) url="jdbc:oracle:thin:@localhost:1521:xe";
3) UserName="test";
4) UserPswd="test";
5) DBTab1="MTAB_FS";
6) DBTab2="TAB_FS";

1) DriverPath:
	The driver path of OracleJDBC driver class.
	Please make sure that you've imported the JDBC Oracle driver.
2) url:
	Connection string for JDBC.
	Make sure that the current port & service name is correct.
3) UserName: 
	A User of database
4) UserPswd:
	Password of the user
5) DBTab_1 & DBTab_2:
	Table(s) used for storing records.

If you want any changes to the config, change it on src/Db.java 

