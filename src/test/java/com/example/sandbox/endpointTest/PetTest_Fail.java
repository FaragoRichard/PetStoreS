package com.example.sandbox.endpointTest;

import com.example.sandbox.Common;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetTest_Fail extends Common {

    private PetBody petBody;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public void setUp() {

        petBody = new PetBody();

        petBody.setId(Tools.generateRandomNumber());
        petBody.setName("Hydra");
        petBody.setStatus("available");
        petBody.setTags(new ArrayList<>());
        petBody.setCategory(new Item());
        petBody.setPhotoUrls(new ArrayList<>());
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Create new pet")
    public void CreatePet_Fail_InvalidID() throws JsonProcessingException {
        String invalidJson = "{\n" +
                "  \"id\": asdasdasdasd,\n" + // Id should be invalid
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "  },\n" +
                "  \"name\": \"Hydra\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = postUrl(newPet, invalidJson);

        Assert.assertEquals(response.getStatusCode(),400,"Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Create new pet")
    public void CreatePet_Fail_InvalidData() throws JsonProcessingException {
        String invalidJson = "{\n" +
                "  \"id\": 0,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"Hydra\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"dead\"\n" + // Status should be invalid
                "}";

        Response response = postUrl(newPet, invalidJson);

        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),405,"Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Find pet by ID")
    public void GetPetByID_Fail_InvalidID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", "asdasdasd");

        Response response = getUrlWithPath(petById, pathParams);

        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),400,"Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Find pet by Status")
    public void GetPetsByStatus_Fail_InvalidStatus() {
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status","dead");

        Response response = getUrl(findByStatus, queryParams);

        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),400,"Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Update pet info", priority = 3)
    public void UpdatePet_Fail_InvalidData() throws JsonProcessingException {
        String json = mapper.writeValueAsString(petBody);

        Response response = postUrl(newPet, json);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");

        Map<String, String> pathParams = new TreeMap<>();
        Map<String, String> formParams = new TreeMap<>();

        pathParams.put("petId", String.valueOf(petBody.getId()));
        formParams.put("name", "Lernaean Hydra");
        formParams.put("status", "slain");

        Response response2 = postUrl(petById, formParams, pathParams);

        Assert.assertNotEquals(response2.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response2.getStatusCode(),400,"Invalid response code");
    }
}
