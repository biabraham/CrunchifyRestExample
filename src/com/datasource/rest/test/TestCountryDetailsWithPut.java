package com.datasource.rest.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestCountryDetailsWithPut {

	public static void main(String[] args) {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		BufferedReader br = null;
		String line = null;
		HttpPut put = new HttpPut("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/v1/countries/PLW");
		put.addHeader("accept", "application/json");
		
		try {
			response = httpClient.execute(put);
			if(response.getStatusLine().getStatusCode()!=200){
				System.out.println("HTTP Error. Error Code is "+response.getStatusLine().getStatusCode());
			}
			
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
			
			httpClient.getConnectionManager().shutdown();;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub

	}

}
