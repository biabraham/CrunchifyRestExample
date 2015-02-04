package com.crunchify.restjersey.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestProducts {
	
	public static void main(String args[]){
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpGet get = new HttpGet("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/products/1?fname=Bibin");
		
		HttpPost post = new HttpPost("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/products/2");
		get.addHeader("accept", "application/json");
		
		try {
			response = httpClient.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(response.getStatusLine().getStatusCode()!=200){
			throw new RuntimeException("HTTP Error - "+response.getStatusLine().getStatusCode());
		}
		else{
			System.out.println("HTTP Response code is "+response.getStatusLine().getStatusCode());
			
			try {
				BufferedReader bf = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				while((line=bf.readLine())!=null){
					System.out.println(line);
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
