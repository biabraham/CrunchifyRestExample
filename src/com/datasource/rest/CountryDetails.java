package com.datasource.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;


@Path("/v1/countries")
public class CountryDetails {
	
	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	final String DB_URL = "jdbc:mysql://localhost/world";
	final String USER = "root";
	final String PASS = "root";
	
	Connection conn = null;
	ResultSet rs = null;
	String value = null;
	String query = null;
	PreparedStatement stmt = null;
	
	List<Map<String,String>> list = new ArrayList<Map<String, String>>();
	Map<String,String> map = new HashMap<String, String>();
	
	Gson gson = new Gson();
	
	@GET
	@Produces("application/json")
	public Response getCountryList(){
		query = "select code, name, continent, region,population from world.country where indepYear>1991";
		String response = getDBOutput(query);		
		return Response.status(200).entity(response).build();
				
	}
	
		
	@Path("{code}")
	@Produces("application/json")
	@GET
	public Response getCountryWithCode(@PathParam("code") String countryCode){
		query = "select code, name, continent, region,population from world.country where upper(code)='"+countryCode+"'";
		String resp = getDBOutput(query);
		return Response.status(200).entity(resp).build();
	}
	
	@PUT
	@Path("{code}")
	public Response updateCountry(@PathParam("code") String countryCode){
		query = "select code, name, continent, region,population from world.country where upper(code)='"+countryCode+"'";
		String initResp = getDBOutput(query);
		query = "update world.country set population=112233 where upper(code)='"+countryCode+"'";
		String resp = getDBOutput(query);
		query = "select code, name, continent, region,population from world.country where upper(code)='"+countryCode+"'";
		String finalResp = getDBOutput(query);
		String output = "Before executing the put - \n\n"+initResp+"\n\nReturn code of update query is - "+resp+"\n\nAfter executing put - \n\n"+finalResp;
		//if()
		return Response.status(200).entity(output).build();
	}
	
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes(MediaType.APPLICATION_JSON)
	@POST
	//public Response insertRowsInCityTable(int id, String name, String countryCode, String district, int population){
	public Response insertRowsInCityTable(String incomingData) throws JsonParseException, JsonMappingException, IOException{
		
		System.out.println("Incoming Data - "+incomingData);
		/*Gson gson = new Gson();
		gson.fromJson(incomingData, ItemEntry.class);*/
		ObjectMapper mapper = new ObjectMapper();
		ItemEntry entry = mapper.readValue(incomingData, ItemEntry.class);
		int id1 = Integer.valueOf(entry.id);
		String name = entry.name;
		String countryCode = entry.countryCode;
		String district = entry.district;
		int population = Integer.valueOf(entry.population);
		String query = "insert into world.city values("+id1+", "+name+", "+countryCode+", "+district+", "+population+")";
		int response=999;
		try {
			response = getDBOutputForPost(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((response)>0)
			return Response.status(203).entity(response).build();
		else
			return Response.status(500).entity("Failed to insert record").build();
	}
	
	
	
	
	public int getDBOutputForPost(String query) throws Exception{
		
			value = null;
			int returnValue = 1001;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				if(conn == null){
					System.err.println("Could not establish a connection");
					//return Response.status(500).entity("Error").build();
				}
				stmt = conn.prepareStatement(query);
				returnValue = stmt.executeUpdate();
				return returnValue;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				
					if(stmt!=null)
						stmt.close();
					if(conn!=null)
						conn.close();
					System.out.println("Closed statement and connection");
			}
			
			return returnValue;
		}
		

	
	
	
	
	
	
	
	
	public String getDBOutput(String query){
		try {
			value = null;
			int returnValue = 1001;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//conn = MySqlDataSource.getDataSource().getConnection();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			if(conn == null){
				System.err.println("Could not establish a connection");
				//return Response.status(500).entity("Error").build();
			}
			stmt = conn.prepareStatement(query);
			if(query.toLowerCase().startsWith("select")){
				rs = stmt.executeQuery();
			}
			else{
				returnValue = stmt.executeUpdate();
				return String.valueOf(returnValue);
			}
						
			while(rs.next()){
				map = new HashMap<String, String>();
				map.put("code", rs.getString("Code"));
				map.put("name", rs.getString("Name"));
				map.put("continent", rs.getString("Continent"));
				map.put("region", rs.getString("Region"));
				map.put("population", rs.getString("Population"));
				list.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(stmt!=null)
					stmt.close();
				if(conn!=null)
					conn.close();
				System.out.println("Closed statement and connection");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(list.size()>0){
			value = gson.toJson(list);
			
			
		}
		 
		return value;
		
	}
	
}

class ItemEntry{
	
	public String id;
	public String name;
	public String countryCode;
	public String district;
	public String population;
	
}
