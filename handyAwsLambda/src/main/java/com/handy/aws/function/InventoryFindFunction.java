package com.handy.aws.function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

public class InventoryFindFunction implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object input, Context context) {
        Gson json = new Gson();
        String prodString = json.toJson(input);
        
        
//        
        Product product = null;
		product = json.fromJson(prodString, Product.class);
		ObjectMapper mapper = new ObjectMapper();
		Product p = null;
		try {
			p = mapper.readValue(prodString, Product.class);
			context.getLogger().log("Input: " + p.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return getProductById(p.getId()).toString();
//		return product.toString();
    }

	private Product getProductById(int productId) {
		S3Client s3Client = S3Client.builder().region(Region.US_EAST_1).build();
		
		ResponseInputStream<?> objectData = s3Client
				.getObject(GetObjectRequest.builder().bucket("handy-inventory-data-store")
//						.key("someText.txt").build());
						.key("handy-tool-catalog.json").build());
		InputStreamReader reader = new InputStreamReader(objectData);
		BufferedReader bfReader = new BufferedReader(reader);
		 
		Gson gson = new Gson();
		Product[] products = gson.fromJson(bfReader, Product[].class);
		
		for(Product prod : products) {
			if(Integer.compare(productId,prod.getId())==0) {
				return prod;
			}
		}
        return new Product(0);
	}

	
	/*
	 * private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
	 * 
	 * public LambdaFunctionHandler() {}
	 * 
	 * // Test purpose only. LambdaFunctionHandler(AmazonS3 s3) { this.s3 = s3; }
	 * 
	 * @Override public String handleRequest(S3Event event, Context context) {
	 * context.getLogger().log("Received event: " + event);
	 * 
	 * // Get the object from the event and show its content type String bucket =
	 * event.getRecords().get(0).getS3().getBucket().getName(); String key =
	 * event.getRecords().get(0).getS3().getObject().getKey(); try { S3Object
	 * response = s3.getObject(new GetObjectRequest(bucket, key)); String
	 * contentType = response.getObjectMetadata().getContentType();
	 * context.getLogger().log("CONTENT TYPE: " + contentType); return contentType;
	 * } catch (Exception e) { e.printStackTrace();
	 * context.getLogger().log(String.format(
	 * "Error getting object %s from bucket %s. Make sure they exist and" +
	 * " your bucket is in the same region as this function.", key, bucket)); throw
	 * e; } }
	 */
}
