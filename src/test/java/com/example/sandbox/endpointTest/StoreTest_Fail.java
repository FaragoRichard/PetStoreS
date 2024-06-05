package com.example.sandbox.endpointTest;

import com.example.sandbox.Common;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.example.sandbox.util.constans.Tags.SMOKE;

public class StoreTest_Fail extends Common {
    //private ObjectMapper mapper = new ObjectMapper();

    @Test(enabled = true, groups = {SMOKE}, description = "Create new order")
    public void CreateOrder_Fail_InvalidId() {
        String json = "{\n" +
                "  \"id\": asdasda,\n" + // Id should be invalid
                "  \"petId\": 1,\n" +
                "  \"quantity\": 1,\n" +
                "  \"shipDate\": \"2024-06-05T17:57:47.766Z\",\n" +
                "  \"status\": \"placed\",\n" +
                "  \"complete\": true\n" +
                "}";

        Response response = postUrl(order, json);
        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),400,"Invalid response code");
    }
}
