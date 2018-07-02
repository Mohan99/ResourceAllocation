package com.gcs.db.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabase {

	
	
	public static Connection getDatabaseConnection(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection("jdbc:mysql://192.168.12.25:3306/gcs_biometric","root","Gemini@123$");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.13.44:3306/gcsreportsdb","user","root");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void closeObject(Object obj){
		if(obj!=null){
			try{
				if(obj instanceof Connection){
					Connection conn = (Connection)obj;
					if(!conn.isClosed()){
						conn.close();
					}
					conn=null;
				}else if(obj instanceof Statement){
					((Statement)obj).close();
				}else if(obj instanceof ResultSet){
					((ResultSet)obj).close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
	
	/*
 	Mx_ATDEventTrn -IndexNo
	Mx_UserMst     -UserID
	Mx_DoorCommon   - DSeqNo
	Mx_MasterControllerBasicCfg
	Mx_DoorControllerDet
	
 */
//	public static void main(String[] args){
//		String tableName="Mx_ATDEventTrn";
//		Map<String,MSSQLColumnMetadata> tableMetadata=MSSQLDatabase.getTableMetaData(tableName);
//		List<String> columnList = MSSQLDatabase.getColumnNamesForInsertQuery(tableName,tableMetadata);
//		String insertQuery = MSSQLDatabase.getInsertStatement(tableName,columnList);
//		System.out.println(insertQuery);
//		Statement sta=null;
//		PreparedStatement pstmt="insert into";
//		Connection mySqlConn=getDatabaseConnection();
//		Connection msSqlConn=MSSQLDatabase.getDatabaseConnection();
//			ResultSet rs=null;
//			try{
//				sta=msSqlConn.createStatement();
//				String Sql = "select * from "+tableName;
//				rs = sta.executeQuery(Sql);
//				
//				while(rs.next()){
//					
//				}
//				
//				
//				
//				
//			}catch(Exception ex){
//				ex.printStackTrace();
//			}finally{
//				MSSQLDatabase.closeObject(rs);
//				MSSQLDatabase.closeObject(sta);				
//			}
//			MSSQLDatabase.closeObject(msSqlConn);
//		MySQLDatabase.closeObject(mySqlConn);
//	}
	
}
