package com.datasource.rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MySqlDataSource {
	
	private static DataSource dataSource= null;
	private static Context context = null;
	
	public static DataSource getDataSource(){
		
		if(dataSource!=null)
			return dataSource;
		
		if(context == null){
			try {
				context = new InitialContext();
				dataSource = (DataSource)context.lookup("New MySQL");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return dataSource;
	}
	

}
