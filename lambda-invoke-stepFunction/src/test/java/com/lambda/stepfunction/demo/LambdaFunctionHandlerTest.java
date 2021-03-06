package com.lambda.stepfunction.demo;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
@RunWith(MockitoJUnitRunner.class)
public class LambdaFunctionHandlerTest {

    private final String CONTENT_TYPE = "image/jpeg";
    private S3Event event;

//    @Mock
//    private AmazonS3 s3Client;
//    @Mock
//    private S3Object s3Object;

//    @Captor
//    private ArgumentCaptor<GetObjectRequest> getObjectRequest;

//    @Before
//    public void setUp() throws IOException {
////        event = TestUtils.parse("/s3-event.put.json", S3Event.class);
//
//        // TODO: customize your mock logic for s3 client
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentType(CONTENT_TYPE);
//        when(s3Object.getObjectMetadata()).thenReturn(objectMetadata);
//        when(s3Client.getObject(getObjectRequest.capture())).thenReturn(s3Object);
//    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();
        JSONObject sfnInput = new JSONObject();
        sfnInput.put("key1", "hello");
        String output = handler.handleRequest(sfnInput, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("World", output);
        
    }
}
