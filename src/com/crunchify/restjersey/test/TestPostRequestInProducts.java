package com.crunchify.restjersey.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class TestPostRequestInProducts {

	public static void main(String[] args) {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpPost post = null;
		StringEntity input = null;
		BufferedReader br = null;
		String line;
		
		
		post = new HttpPost("http://localhost:8080/CrunchifyRestJerseyExample/crunchify/products");
		post.addHeader("accept", "application/json");
		
		try {
			input = new StringEntity("{\"name\":\"product 10\",\"description\":\"prd 10 description\"}");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		post.setEntity(input);
		// TODO Auto-generated method stub
		
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(response.getStatusLine().getStatusCode()!=201){
			throw new RuntimeException("HTTP ERROR - "+response.getStatusLine().getStatusCode());
		}
		
		try {
			 br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		httpClient.getConnectionManager().shutdown();
	}

}
