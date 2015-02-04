package com.crunchify.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

@Path("/products")
public class ProductsService {
	@Path("{x}")
	@GET
	@Produces("application/json")
	//@Produces(MediaType.)
	public Response getResponse(@PathParam("x") int x, @QueryParam("fname") String firstName) throws JSONException{
		
		JSONObject json = new JSONObject();
		//int y= x;
		switch (x){
			case 1:
				json.put("name", "product 1");
				json.put("decscription", "prd 1 description");
				json.put("Query Param", firstName);
				break;
				
			case 2:// x = 2;
				json.put("name", "product 2");
				json.put("decscription", "prd 2 description");
				break;
				
			case 3:// x = 3;
				json.put("name", "product 3");
				json.put("decscription", "prd 3 description");
				break;	
				
			default:
				break;
		}
				
		if(json.length()!=0){
			String result = "Product details - "+json;
			return Response.status(200).entity(result).build();
			
		}else{
			String result = "Product details not found. Please check the product id";
			return Response.status(404).entity(result).build();
		}
			
		
		
	}
	
	@POST
	@Produces("application/json")
	public Response createProductInJson(String product){
		Gson gson = new Gson();
		Product pr = gson.fromJson(product, Product.class);
		
		String result = "Product created "+pr;
		return Response.status(201).entity(result).build();
		
	}

}
