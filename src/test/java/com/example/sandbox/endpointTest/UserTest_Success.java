package com.example.sandbox.endpointTest;

import com.example.sandbox.Common;
import com.example.sandbox.util.Tools;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class UserTest_Success extends Common {
    private String userJson;
    private int generatedId;
    private String username;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        generatedId = Tools.generateRandomNumber();
        username = "HydraSlayer";
        password = UUID.randomUUID().toString();
        userJson ="[\n" +
                "  {\n" +
                "    \"id\": "+generatedId+",\n" +
                "    \"username\": \""+username+"\",\n" +
                "    \"firstName\": \"string\",\n" +
                "    \"lastName\": \"string\",\n" +
                "    \"email\": \"string\",\n" +
                "    \"password\": \""+password+"\",\n" +
                "    \"phone\": \"string\",\n" +
                "    \"userStatus\": 0\n" +
                "  }\n" +
                "]";
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Create new user with list", priority = 1)
    public void CreateUsersList_Success_ValidData(){
        Response response = postUrl(createWithList, userJson);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Create new user with array", priority = 1)
    public void CreateUsersArray_Success_ValidData(){
        Response response = postUrl(createWithArray, userJson);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Get user by name", priority = 2)
    public void GetUserByName_Success_ValidUsername(){
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("username", username);

        Response response = getUrlWithPath(user, pathParams);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Update username", priority = 3)
    public void UpdateUserByName_Success_ValidUsername(){
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("username", username);

        userJson = """
                {
                  "id": 0,
                  "username": "HydraSlayer",
                  "firstName": "asdasdasd",
                  "lastName": "asdasdasd",
                  "email": "asdasdasdasd",
                  "password": "asdasdasdasd",
                  "phone": "asdasdasda",
                  "userStatus": 0
                }""";

        Response response = putUrl(user, userJson, pathParams);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Login User", priority = 3)
    public void LoginUser_Success_ValidData(){
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("username", username);
        queryParams.put("password", password);

        Response response = getUrl(login, queryParams);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE, REGRESSION}, description = "Logout User", priority = 4)
    public void LogoutUser_Success_ValidData(){
        Response response = getUrl(logout);
        Assert.assertEquals(response.getStatusCode(),200,"Invalid response code");
    }
}
