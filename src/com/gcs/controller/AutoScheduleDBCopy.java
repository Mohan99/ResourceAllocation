package com.gcs.controller;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import com.gcs.db.tool.MSSQLColumnMetadata;
import com.gcs.db.tool.MSSQLDatabase;
import com.gcs.db.tool.MySQLDatabase;
import com.gcs.dbDao.EmployeeDao;

public class AutoScheduleDBCopy {
	@Autowired
	@Qualifier("employeeDao")
	private EmployeeDao empDao;
	
	//@Scheduled(cron=" 16 19 * * * ?")
	@Scheduled(fixedDelay =180000000)
	 public void dataCopy()
	    {   
		System.out.println("cron started");
		 boolean isDeleted= empDao.deleteSwipeData();
	    
		 if(isDeleted) {
			 System.out.println("data deleted");
	 syncDBTable("mx_atdeventtrn", null, 500);
		 }
	        System.out.println("Method executed at every day seconds. Current time is :: "+ new Date());
	    
	    }
	
	
	
	 public EmployeeDao getEmpDao() {
		return empDao;
	}



	public void setEmpDao(EmployeeDao empDao) {
		this.empDao = empDao;
	}



	private static void syncDBTable(String tableName,String compareColumnName, int batchSize){
		 
		Connection mySqlConn = MySQLDatabase.getDatabaseConnection();
		  
		  Connection conn = MSSQLDatabase.getDatabaseConnection();		  
		 
		Map<String,MSSQLColumnMetadata> metaDataMap = MSSQLDatabase.getTableMetaData(tableName);
		  List<String> columns=MSSQLDatabase.getColumnNamesForInsertQuery(tableName,metaDataMap);
		  String insertQry = MSSQLDatabase.getInsertStatement(tableName,columns,compareColumnName);
		
		  
		 
		  if(conn!=null){
		   Statement sta=null;
		   ResultSet rs=null;
		   PreparedStatement ps = null;
		   try {
		    sta = conn.createStatement();
		    String Sql = "";
		    Sql="select * from "+tableName;
		    int count=0;
		    rs = sta.executeQuery(Sql);
		    while(rs.next()){
		     ++count;
		     if(ps==null){
		      mySqlConn.setAutoCommit(false);
		      ps=mySqlConn.prepareStatement(insertQry);
		     }
		     for(int i=0;i<columns.size();i++){
		      String columnName = columns.get(i);
		      //System.out.println(columnName);
		      MSSQLColumnMetadata metaData = null;
		      if(metaDataMap.containsKey(columnName))metaData=metaDataMap.get(columnName);
		      else metaData=metaDataMap.get(columnName.toLowerCase());
		      if(metaData!=null)
		      {
		      if(metaData.getColumnType()==1 
		        || metaData.getColumnType()==12 
		        || metaData.getColumnType()==-1  
		        || metaData.getColumnType()==-9        
		        || metaData.getColumnType()==-16
		        ){
		       ps.setString(i+1, rs.getString(columns.get(i)));
		      }else if(metaData.getColumnType()==-15){
		       ps.setCharacterStream(i+1, new StringReader(rs.getString(columnName)), metaData.getColumnDisplaySize());
		      }
		      else if(metaData.getColumnType()==4 || metaData.getColumnType()==5||metaData.getColumnType()==2){
		       ps.setInt(i+1, rs.getInt(columns.get(i)));
		      }else if(metaData.getColumnType()==-4 || metaData.getColumnType()==-3||metaData.getColumnType()==-2){
		       ps.setBytes(i+1, rs.getBytes(columns.get(i)));
		      }else if(metaData.getColumnType()==93 || metaData.getColumnType()==91 || metaData.getColumnType()==92){
		       ps.setTimestamp(i+1, rs.getTimestamp(columnName));
		      }else if(metaData.getColumnType()==-5){
		       ps.setLong(i+1, rs.getLong(columnName));
		      }
		      else{
		       System.out.println("Column "+columnName + ": data type not found ("+metaData.getColumnType()+ ","+metaData.getColumnTypeName()+")");
		      }
		     }
		      else{
		    	  System.out.println("Empty metadata found");
		      }
		     }
		     if(batchSize > 10){
		      ps.addBatch();
		    //  ps.clearParameters();
		      if( count%batchSize==0){
		       ps.executeBatch();
		       mySqlConn.commit();
		       
		      }
		     }else{
		      ps.executeUpdate();
		     }
		    }
		    if(ps!=null && batchSize >10){
		     if(count%batchSize!=0)ps.executeBatch();     
		    }
		    if(count>0){
		     mySqlConn.commit();
		    }
		    System.out.println("Total "+count + " rows committed into database table "+tableName);
		   }catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }finally{
		    MSSQLDatabase.closeObject(rs);
		    MSSQLDatabase.closeObject(sta);
		    MSSQLDatabase.closeObject(conn);
		    MySQLDatabase.closeObject(ps);
		    MySQLDatabase.closeObject(mySqlConn);
		   }
		  }
		  
		 }

//copy required data
	 @SuppressWarnings("unused")
	private static void syncDBTable(String tableName,String compareColumnName, int batchSize,String condition,String orderBy){
			System.out.println("Table " + tableName + " Synchronization Started");
			Map<String,MSSQLColumnMetadata> metaDataMap = MSSQLDatabase.getTableMetaData(tableName);
			List<String> columns=MSSQLDatabase.getColumnNamesForInsertQuery(tableName,metaDataMap);
			String insertQry = MSSQLDatabase.getInsertStatement(tableName,columns,null,condition);//compareColumnName
			Connection conn = MSSQLDatabase.getDatabaseConnection();
			Connection mySqlConn = MySQLDatabase.getDatabaseConnection();
			System.out.println(insertQry);
			if(conn!=null){
				Statement sta=null;
				ResultSet rs=null;
				PreparedStatement ps = null;
				Object obj=null;
				try {
					sta = conn.createStatement();
					String Sql = "";
					Sql="select * from Mx_ATDEventTrn where Edatetime>='2016-12-19 00:00:00.0' order by Edatetime asc";//"select * from "+tableName +(compareColumnName!=null?(" WHERE "+compareColumnName +" " + condition + "' and Edatetime<'2016-11-01 00:00:00.0' " + getMaxValue(tableName,compareColumnName)+"'"):"")+ (orderBy!=null?(" order by " + orderBy + " asc"):"");//"select * from Mx_ATDEventTrn where Edatetime>'2016-03-18 13:17:58.0' and Edatetime<'2016-11-01 00:00:00.0' order by Edatetime asc";//
					compareColumnName=null;//TODO - needs to remove
					int count=0;
					System.out.println(Sql);
					rs = sta.executeQuery(Sql);
					if(ps==null){
						mySqlConn.setAutoCommit(false);
						ps=mySqlConn.prepareStatement(insertQry);
					}
					while(rs.next()){
						++count;
						System.out.print(count+", ");					
						for(int i=0;i<columns.size();i++){
							String columnName = columns.get(i);
							//System.out.println(columnName);
							MSSQLColumnMetadata metaData = null;
							//System.out.println(metaDataMap);
							if(metaDataMap.containsKey(columnName))metaData=metaDataMap.get(columnName);
							else metaData=metaDataMap.get(columnName.toLowerCase());
							//System.out.println(columnName + " => " + metaData.getColumnType());
							setValueForPStmt(ps,rs, metaData, columnName,i+1);
							if(compareColumnName!=null && compareColumnName.equalsIgnoreCase(columnName)){
								if(condition.equals("="))setValueForPStmt(ps,rs, metaData, columnName,columns.size()+1);
								else{
									if(obj==null)obj = getMaxValue(tableName,compareColumnName,metaData);
									ps.setObject(columns.size()+1, obj);
								}
							}
							/*if(metaData.getColumnType()==1 
									|| metaData.getColumnType()==12 
									|| metaData.getColumnType()==-1  
									|| metaData.getColumnType()==-9								
									|| metaData.getColumnType()==-16
									){
								ps.setString(i+1, rs.getString(columns.get(i)));
							}else if(metaData.getColumnType()==-15){
								ps.setCharacterStream(i+1, new StringReader(rs.getString(columnName)), metaData.getColumnDisplaySize());
							}
							else if(metaData.getColumnType()==4 || metaData.getColumnType()==5||metaData.getColumnType()==2){
								ps.setInt(i+1, rs.getInt(columns.get(i)));
							}else if(metaData.getColumnType()==-4 || metaData.getColumnType()==-3||metaData.getColumnType()==-2){
								ps.setBytes(i+1, rs.getBytes(columns.get(i)));
							}else if(metaData.getColumnType()==93 || metaData.getColumnType()==91 || metaData.getColumnType()==92){
								ps.setTimestamp(i+1, rs.getTimestamp(columnName));
							}else if(metaData.getColumnType()==-5){
								ps.setLong(i+1, rs.getLong(columnName));
							}
							else{
								System.out.println("Column "+columnName + ": data type not found ("+metaData.getColumnType()+ ","+metaData.getColumnTypeName()+")");
							}*/
						}
						if(batchSize > 10){
							ps.addBatch();
							ps.clearParameters();
							if( count%batchSize==0){
								ps.executeBatch();
								mySqlConn.commit();
								System.out.println(batchSize + " records Commited");
							}
						}else{
							ps.executeUpdate();
						}
					}
					if(ps!=null && batchSize >10){
						if(count%batchSize!=0)ps.executeBatch();					
					}
					if(batchSize > 10 && count>0){
						mySqlConn.commit();
					}
					System.out.println("Total "+count + " rows committed into database table "+tableName);
				}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					MSSQLDatabase.closeObject(rs);
					MSSQLDatabase.closeObject(sta);
					MSSQLDatabase.closeObject(conn);
					MySQLDatabase.closeObject(ps);
					MySQLDatabase.closeObject(mySqlConn);
				}
			}
			System.out.println("Table " + tableName + " Synchronization Completed");
		}
	 private static void setValueForPStmt(PreparedStatement ps,ResultSet rs, MSSQLColumnMetadata metaData, String columnName,int i) throws SQLException{
			if(metaData.getColumnType()==1 
					|| metaData.getColumnType()==12 
					|| metaData.getColumnType()==-1  
					|| metaData.getColumnType()==-9								
					|| metaData.getColumnType()==-16
					){
				ps.setString(i, rs.getString(columnName));
			}else if(metaData.getColumnType()==-15){
				ps.setCharacterStream(i, new StringReader(rs.getString(columnName)), metaData.getColumnDisplaySize());
			}
			else if(metaData.getColumnType()==4 || metaData.getColumnType()==5||metaData.getColumnType()==2){
				ps.setInt(i, rs.getInt(columnName));
			}else if(metaData.getColumnType()==-4 || metaData.getColumnType()==-3||metaData.getColumnType()==-2){
				ps.setBytes(i, rs.getBytes(columnName));
			}else if(metaData.getColumnType()==93 || metaData.getColumnType()==91 || metaData.getColumnType()==92){
				ps.setTimestamp(i, rs.getTimestamp(columnName));
			}else if(metaData.getColumnType()==-5){
				ps.setLong(i, rs.getLong(columnName));
			}
			else{
				System.out.println("Column "+columnName + ": data type not found ("+metaData.getColumnType()+ ","+metaData.getColumnTypeName()+")");
			}
		}
	 private static Object getMaxValue(String tableName,String compareColumnName,MSSQLColumnMetadata metaData){
			Connection mySqlConn = MySQLDatabase.getDatabaseConnection();
			Object obj = null;
			if(mySqlConn!=null){
				Statement sta=null;
				ResultSet rs=null;
				try{
					String sql = "select max("+compareColumnName+") from "+tableName;
					sta=mySqlConn.createStatement();
					rs=sta.executeQuery(sql);
					if(rs.next()){					
						obj=rs.getObject(1);
						System.out.println("The max value: " + obj);
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}finally{
					MySQLDatabase.closeObject(rs);
					MySQLDatabase.closeObject(sta);
					MySQLDatabase.closeObject(mySqlConn);
				}
			}
			if(obj==null){
				if(metaData.getColumnType()==1 
						|| metaData.getColumnType()==12 
						|| metaData.getColumnType()==-1  
						|| metaData.getColumnType()==-9								
						|| metaData.getColumnType()==-16
						){
					obj = new String("");
				}else if(metaData.getColumnType()==-15){
					obj = new String("");
				}
				else if(metaData.getColumnType()==4 || metaData.getColumnType()==5||metaData.getColumnType()==2){
					obj = new Integer(1);
				}else if(metaData.getColumnType()==-4 || metaData.getColumnType()==-3||metaData.getColumnType()==-2){
					obj = "".getBytes();
				}else if(metaData.getColumnType()==93 || metaData.getColumnType()==91 || metaData.getColumnType()==92){
					String text = "2011-01-01 00:00:00.123456";
			        Timestamp ts = Timestamp.valueOf(text);
			        obj=ts;
				}else if(metaData.getColumnType()==-5){
					obj=new Long(1);
				}
			}
			return obj;
		}

}
