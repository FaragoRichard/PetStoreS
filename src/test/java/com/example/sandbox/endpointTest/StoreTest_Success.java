package com.example.sandbox.endpointTest;

import com.example.sandbox.Common;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class StoreTest_Success extends Common {
    //private ObjectMapper mapper = new ObjectMapper();

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Create new order")
    public void CreateOrder_Success_ValidData() {
        String json = """
                {
                  "id": 1,
                  "petId": 1,
                  "quantity": 1,
                  "shipDate": "2024-06-05T17:57:47.766Z",
                  "status": "placed",
                  "complete": true
                }""";

        Response response = postUrl(order, json);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Get inventory")
    public void GetInventory_Success_ValidData() throws JsonProcessingException {
        Response response = getUrl(inventory);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }
}
