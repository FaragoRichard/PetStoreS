package com.example.sandbox.businessProcesses;

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

import static com.example.sandbox.util.constans.Tags.SMOKE;

//import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;

public class PetLifeCycle extends Common {
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

    @Test(enabled = true, groups = {SMOKE}, description = "Create new pet", priority = 1)
    public void CreatePet() throws JsonProcessingException {
        String json = mapper.writeValueAsString(petBody);

        Response response = postUrl(newPet, json);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE}, description = "Find pet by ID", priority = 2)
    public void GetPetByID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = getUrlWithPath(petById, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE}, description = "Update pet info", priority = 3)
    public void UpdatePet() throws JsonProcessingException {
        petBody.setName("Lernaean Hydra");
        String json = mapper.writeValueAsString(petBody);

        Response response = putUrl(newPet, json);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE}, description = "Find pet by ID", priority = 4)
    public void DeletePetByID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = deleteUrl(petById, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }
}
