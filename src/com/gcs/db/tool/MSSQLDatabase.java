package com.gcs.db.tool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MSSQLDatabase {
	private static final String SPACE=" ";
	public static Connection getDatabaseConnection(){
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection("jdbc:sqlserver://192.168.12.5;user=sa;password=Gemini123;database=COSEC;");
			
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
	
	public static long getAutoIncrementValue(String tableName){
		return getAutoIncrementValue(tableName,"DBKEY");
	}
	
	public static long getAutoIncrementValue(String tableName, String columnName){
		Connection conn = MSSQLDatabase.getDatabaseConnection();
		long a =0;
		if(conn!=null){
			Statement sta=null;
			ResultSet rs=null;
			try{
				sta = conn.createStatement();
				String Sql = "select max("+columnName+") from "+tableName;
				rs = sta.executeQuery(Sql);
				if(rs.next()){
					a=rs.getLong(1);
				}
			}catch(SQLException ex){
				ex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				MSSQLDatabase.closeObject(rs);
				MSSQLDatabase.closeObject(sta);
				MSSQLDatabase.closeObject(conn);
			}
		}
		return a+1;
	}
	
	public static void getTriggersInfo(){
		
	}
	
	public static Map<String,MSSQLColumnMetadata> getTableMetaData(String tableName){
		Map<String,MSSQLColumnMetadata> map = null;
		Connection conn = MSSQLDatabase.getDatabaseConnection();
		if(conn!=null){
			Statement sta=null;
			ResultSet rs=null;
			try {
				sta = conn.createStatement();
				String Sql = "select * from "+tableName;
				rs = sta.executeQuery(Sql);
				/*while (rs.next()) {
					System.out.println(rs.getString("MIRI_ACCOUNT_NUMBER"));
				}*/
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				//System.out.println(columnCount);
				map = new HashMap<String,MSSQLColumnMetadata>();
				for(int i=1;i<=columnCount;i++){
	                //System.out.println(rsmd.getColumnName(i) + " => " +rsmd.getColumnTypeName(i));					
					MSSQLColumnMetadata data=new MSSQLColumnMetadata();
					data.setColumnCatalogueName(rsmd.getCatalogName(i));
					data.setColumnClassName(rsmd.getColumnClassName(i));
					data.setColumnDisplaySize(rsmd.getColumnDisplaySize(i));
					data.setColumnLabel(rsmd.getColumnLabel(i));
					data.setColumnName(rsmd.getColumnName(i));
					data.setColumnType(rsmd.getColumnType(i));
					data.setColumnTypeName(rsmd.getColumnTypeName(i));
					data.setPrecission(rsmd.getPrecision(i));
					data.setScale(rsmd.getScale(i));
					data.setSchemaName(rsmd.getSchemaName(i));
					data.setTableName(rsmd.getTableName(i));
					data.setAutoIncrement(rsmd.isAutoIncrement(i));
					data.setCaseSensitive(rsmd.isCaseSensitive(i));
					data.setCurrency(rsmd.isCurrency(i));
					data.setDefinitelyWritable(rsmd.isDefinitelyWritable(i));					
					data.setNullable(rsmd.isNullable(i)==ResultSetMetaData.columnNullable);
					data.setReadOnly(rsmd.isReadOnly(i));
					data.setSearchable(rsmd.isSearchable(i));
					data.setSigned(rsmd.isSigned(i));
					data.setWritable(rsmd.isWritable(i));
					map.put(data.getColumnName(),data);
	            }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				MSSQLDatabase.closeObject(rs);
				MSSQLDatabase.closeObject(sta);
				MSSQLDatabase.closeObject(conn);
			}
		}
		/*
		-7 	BIT
		-6 	TINYINT
		-5 	BIGINT
		-4 	LONGVARBINARY 
		-3 	VARBINARY
		-2 	BINARY
		-1 	LONGVARCHAR
		0 	NULL
		1 	CHAR
		2 	NUMERIC
		3 	DECIMAL
		4 	INTEGER
		5 	SMALLINT
		6 	FLOAT
		7 	REAL
		8 	DOUBLE
		12 	VARCHAR
		91 	DATE
		92 	TIME
		93 	TIMESTAMP
		1111  	OTHER
		*/
		return map;
	}
	
	public static Map<String,Map<String,MSSQLColumnMetadata>> getAllTablesMetadata(){
		Map<String,Map<String,MSSQLColumnMetadata>> map = new HashMap<String,Map<String,MSSQLColumnMetadata>>();
		List<String> excludeTables = new ArrayList<String>();
		excludeTables.add("sysdiagrams");
		List<String> tables = MSSQLDatabase.getAllTableMetadata("MIRISYSTEMS","dbo","%",excludeTables);
		for(int i=0;i<tables.size();i++){
			map.put(tables.get(i), MSSQLDatabase.getTableMetaData(tables.get(i)));
		}
		return map;
	}
	
	public static List<String> getColumnNamesForInsertQuery(String tableName, Map<String,MSSQLColumnMetadata> tableMetaData){
		return getColumnNamesForInsertQuery(tableName,tableMetaData,false);
	}
	
	public static List<String> getColumnNamesForInsertQuery(String tableName, Map<String,MSSQLColumnMetadata> tableMetaData,  boolean isDBKEYReqd){
		List<String> columnList = new ArrayList<String>();
		Iterator <Map.Entry<String,MSSQLColumnMetadata>> itr = tableMetaData.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<String,MSSQLColumnMetadata> entry = itr.next();
			String columnName = entry.getKey();//.toUpperCase();
			
			if(!(columnName.equals("DBKEY") || entry.getValue().isAutoIncrement()))columnList.add(columnName);
			else if(isDBKEYReqd && columnName.equals("DBKEY"))columnList.add(columnName);
		}
		return columnList;
	}
	
	/*public static String getInsertStatement(String tableName, List<String> columnList){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(tableName.toLowerCase()).append(" (");
		for(int i=1;i<=columnList.size();i++){
			sb.append("`").append(columnList.get(i-1)).append("`");
			if(i<columnList.size())sb.append(",");
		}		
		sb.append(") VALUES (");
		for(int i=1;i<=columnList.size();i++){
			sb.append("?");
			if(i<columnList.size())sb.append(",");
		}		sb.append(")");
		return sb.toString();
	}*/
	
	
	public static String getMySQLCreateTableSyntax(String tableName){
		tableName=tableName.toLowerCase();
		StringBuilder sb = new StringBuilder();
		sb.append("-- CREATE TABLE for ").append(tableName).append("\n\n");
		Map<String,MSSQLColumnMetadata> tableMetaData = getTableMetaData(tableName);
		Iterator<Entry<String,MSSQLColumnMetadata>> itr = tableMetaData.entrySet().iterator();
		int columnSize = tableMetaData.size();
		sb.append("CREATE TABLE `").append(tableName).append("` (\n");
		MSSQLColumnMetadata primary = null;
		while(itr.hasNext()){
			Entry<String,MSSQLColumnMetadata> entry = itr.next();
			sb.append("\t").append(getMySQLColumnTypeString(entry.getValue()));
			--columnSize;
			if(columnSize>0)sb.append(",");
			sb.append("\n");
			if(entry.getValue().isAutoIncrement())primary=entry.getValue();
		}
		if(primary!=null){
			sb.append("\t,").append("PRIMARY KEY (`").append(primary.getColumnName()).append("`)\n");
		}
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n\n");
		return sb.toString();
	}
	
	public static String getMySQLColumnTypeString(MSSQLColumnMetadata data){
		StringBuilder sb = new StringBuilder();
		if(data.getColumnName().trim().toUpperCase().equals("DBKEY")){data.setAutoIncrement(true);data.setNullable(false);}
		sb.append("`").append(data.getColumnName()).append("`").append(SPACE);
		if(data.getColumnTypeName().trim().toLowerCase().equals("nchar")){
			if(data.getColumnDisplaySize()>500){
				sb.append("longtext CHARACTER SET ucs2");				
			}else{
				sb.append("char(").append(data.getColumnDisplaySize()).append(") CHARACTER SET ucs2");
			}			
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("nvarchar")){
			if(data.getColumnDisplaySize()>65535){
				sb.append("longtext CHARACTER SET ucs2");				
			}else{
				sb.append("varchar(").append(data.getColumnDisplaySize()).append(") CHARACTER SET ucs2");
			}			
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("varchar")){
			if(data.getColumnDisplaySize()>65535){
				sb.append("text");				
			}else{
				sb.append("varchar(").append(data.getColumnDisplaySize()).append(")");
			}
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("int")){
				sb.append("int(11) unsigned");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("smallint")){
			sb.append("smallint(6)");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("bigint")){
			sb.append("bigint(20)");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("xml")){
			sb.append("longtext");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("date")){
			sb.append("date");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("datetime")){
			sb.append("datetime");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("varbinary")){
			sb.append("longblob");
		}else if(data.getColumnTypeName().trim().toLowerCase().equals("uniqueidentifier")){
			sb.append("char(38)");
		}
		
		if(data.isNullable())sb.append(" DEFAULT NULL");
		else sb.append(" NOT NULL");
		
		//if(data.isAutoIncrement())sb.append(" AUTO_INCREMENT");
		return sb.toString();
	}
	
	@SuppressWarnings("unused")
	public static List<String> getAllTableMetadata(String catalogPattern, String schemaPattern, String tablePattern, List<String> excludeTables){
		List<String> tables = new ArrayList<String>();
		Connection conn = MSSQLDatabase.getDatabaseConnection();
		if(conn!=null){
			ResultSet rs=null;
			try{
				String[] types =  {"TABLE"};
				DatabaseMetaData md = conn.getMetaData();
				rs = md.getTables(catalogPattern, schemaPattern, tablePattern, types);
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				//for(int i=1;i<=columnCount;i++)System.out.println(rsmd.getColumnName(i));
				while (rs.next()) {
					String tableName = rs.getString(3);
					if(!excludeTables.contains(tableName))tables.add(tableName);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				MSSQLDatabase.closeObject(rs);
				MSSQLDatabase.closeObject(conn);
			}
		}
		return tables;
	}
	
	
	/*public static String getInsertStatement(String tableName, List<String> columnList){
		  return getInsertStatement(tableName,columnList,null);
		 }*/
		 public static String getInsertStatement(String tableName, List<String> columnList,String columnName){
		  StringBuilder sb = new StringBuilder();
		  sb.append("INSERT INTO ").append(tableName.toLowerCase()).append(" (");
		  for(int i=1;i<=columnList.size();i++){
		   sb.append("`").append(columnList.get(i-1)).append("`");
		   if(i<columnList.size())sb.append(",");
		  }
		  boolean withSelect = columnName!=null;
		  if(withSelect)
		   sb.append(") SELECT ");
		  else
		  sb.append(") VALUES (");
		  for(int i=1;i<=columnList.size();i++){
		   sb.append("?");
		   if(i<columnList.size())sb.append(",");
		  }  
		  if(withSelect){
		   sb.append(" WHERE ").append(columnName).append(" NOT IN (SELECT ").append(columnName).append(" FROM ").append(tableName).append(")");
		  }else
		  sb.append(")");
		  return sb.toString();
		 }
		 public static String getInsertStatement(String tableName, List<String> columnList){
				return getInsertStatement(tableName,columnList,null);
			}
			/*public static String getInsertStatement(String tableName, List<String> columnList,String columnName){
				return getInsertStatement(tableName,columnList,null,"=");
			}*/
			public static String getInsertStatement(String tableName, List<String> columnList,String columnName, String condition){
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO ").append(tableName.toLowerCase()).append(" (");
				for(int i=1;i<=columnList.size();i++){
					sb.append("`").append(columnList.get(i-1)).append("`");
					if(i<columnList.size())sb.append(",");
				}
				boolean withSelect = columnName!=null;
				if(withSelect)
					sb.append(") SELECT ");
				else
				sb.append(") VALUES (");
				for(int i=1;i<=columnList.size();i++){
					sb.append("?");
					if(i<columnList.size())sb.append(",");
				}		
				
				if(withSelect){
					sb.append(" from dual WHERE NOT EXISTS (SELECT 1 FROM ").append(tableName).append(" WHERE ").append(columnName).append(condition).append(" ?").append(")");
				}else sb.append(") ");
				
				return sb.toString();
			}
			
}
