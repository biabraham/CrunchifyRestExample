package com.crunchify.restjersey.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class testCtoFService {

	public static void main(String[] args) {
		
		HttpResponse response = null;
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		HttpGet getRequest = new HttpGet("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/ctofservice");
		getRequest.addHeader("accept", "application/xml");
		
		try {
			response = httpClient.execute(getRequest);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(response.toString());
		
		if(response.getStatusLine().getStatusCode()!=200){
			throw new RuntimeException("Failed. HTTP Error Code: "+response.getStatusLine().getStatusCode());
		}
		else{
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String output;
				System.out.println(("*****************************************"));
				while((output = br.readLine())!=null){
					System.out.println(output);
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		httpClient.getConnectionManager().shutdown();
			//
		// TODO Auto-generated method stub

	}

}
