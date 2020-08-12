package com.lambda.stepfunction.demo;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.AWSStepFunctionsClientBuilder;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.google.gson.Gson;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

    @Override
    public String handleRequest(Object event, Context context) {
        context.getLogger().log("Received event: " + event.toString());
        Regions region = Regions.US_EAST_1;
//        		fromName(System.getenv("AWS_REGION"));
        
     // Instantiate AWSStepFunctionsClientBuilder to build the client
        AWSStepFunctionsClientBuilder builder = AWSStepFunctionsClientBuilder.standard()
                                                  .withRegion(region);
        
     // Build the client, which will ultimately do the work
        AWSStepFunctions client = builder.build();
        
    //  Construct the JSON input for state machine
//        JSONObject sfnInput = new JSONObject(event.toString());
//        sfnInput.put("id", 102);
        Gson json = new Gson();
//        Create a request to start execution with needed parameters
        StartExecutionRequest request = new StartExecutionRequest()
                                              .withStateMachineArn("arn:aws:states:us-east-1:436778736952:stateMachine:Helloworld")
//                                              .withName("FromLabmda")
                                              .withInput(json.toJson(event));
        
     // Start the state machine and capture response
        StartExecutionResult result = client.startExecution(request);
        context.getLogger().log("Response : " + result.getSdkResponseMetadata());;
        return result.getSdkResponseMetadata().toString();
    }
}