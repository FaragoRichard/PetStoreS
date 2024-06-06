package com.example.sandbox.businessProcesses;

import com.example.sandbox.Common;
import com.example.sandbox.util.Tools;
import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

//import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;

public class PetLifeCycle extends Common {
    private PetBody petBody;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass(alwaysRun = true)
    public void setUp() {

        petBody = new PetBody();

        petBody.setId(Tools.generateRandomNumber());
        petBody.setName("Hydra");
        petBody.setStatus("available");
        petBody.setTags(new ArrayList<>());
        petBody.setCategory(new Item());
        petBody.setPhotoUrls(new ArrayList<>());
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Create new pet", priority = 1)
    public void CreatePet() throws JsonProcessingException {
        String json = mapper.writeValueAsString(petBody);

        Response response = postUrl(newPet, json);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Find pet by ID", priority = 2)
    public void GetPetByID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = getUrlWithPath(petById, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Update pet info", priority = 3)
    public void UpdatePet() throws JsonProcessingException {
        String newName = "Lernaean Hydra";
        petBody.setName(newName);
        String json = mapper.writeValueAsString(petBody);
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = putUrl(newPet, json);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");

        // Verify updated pet
        Response verifyUpdate = getUrlWithPath(petById, pathParams);
        JsonNode jsonNode = mapper.readTree(verifyUpdate.getBody().asString());

        Assert.assertEquals(verifyUpdate.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(jsonNode.get("name").textValue(), newName,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Find pet by ID", priority = 4)
    public void DeletePetByID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = deleteUrl(petById, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");

        // Verify delete pet
        Response verifyUpdate = getUrlWithPath(petById, pathParams);
        Assert.assertEquals(verifyUpdate.getStatusCode(),404,"Invalid response code");
    }
}
