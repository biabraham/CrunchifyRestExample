package com.datasource.rest.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



public class TestCountryDetailsWithPost {

	public static void main(String[] args) throws IOException {
		
		Client client = Client.create();
		
		WebResource webResource = client.resource("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/v1/countries");
		
		/*String response = webResource.accept(MediaType.APPLICATION_JSON).get(String.class);
		
		System.out.println(response);
		
		System.out.println("*********************************************");
		
		ClientResponse resp = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		System.out.println(resp.getStatus());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(resp.getEntityInputStream()));
		
		String line;
		while((line=br.readLine())!=null){
			System.out.println(line);
		}*/
		
		String incomingData = "{ \"id\": \"5000\", \"name\": \"Kottayam\", \"countryCode\": \"IND\", \"district\": \"Kot\",\"population\": \"100\"}";
		
		ClientResponse postResponse = webResource.entity(incomingData).post(ClientResponse.class);
		
		System.out.println(postResponse.getStatus());
		
		BufferedReader br = new BufferedReader(new InputStreamReader(postResponse.getEntityInputStream()));
		
		String line;
		while((line=br.readLine())!=null){
			System.out.println(line);
		}
		
		
		//System.out.println(resp.getEntityInputStream());

	}

}
