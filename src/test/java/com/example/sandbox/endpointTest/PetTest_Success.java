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

import java.util.*;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetTest_Success extends Common {

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

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Create new pet", priority = 1)
    public void CreatePet_Success_ValidData() throws JsonProcessingException {
        String json = mapper.writeValueAsString(petBody);

        Response response = postUrl(newPet, json);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Find pet by ID", priority = 2)
    public void GetPetByID_Success_ValidID() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = getUrlWithPath(petById, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Find pet by Status", priority = 2)
    public void GetPetsByStatus_Success_ValidStatus() {
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("status","available");

        Response response = getUrl(findByStatus, queryParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Update pet info", priority = 3)
    public void UpdatePet_Success_ValidIDAndData() {
        Map<String, String> pathParams = new TreeMap<>();
        Map<String, String> formParams = new TreeMap<>();

        petBody.setName("Lernaean Hydra");
        pathParams.put("petId", String.valueOf(petBody.getId()));
        formParams.put("name", "Lernaean Hydra");

        Response response = postUrl(petById, formParams, pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Upload image for existing pet", priority = 4)
    public void UploadPetImage_Success_ValidIDAndImage() {
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("petId", String.valueOf(petBody.getId()));

        Response response = postFile(uploadImage, "Hydra.png", pathParams);

        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }
}
