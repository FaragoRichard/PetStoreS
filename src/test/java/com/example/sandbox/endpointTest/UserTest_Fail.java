package com.example.sandbox.endpointTest;

import com.example.sandbox.Common;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.TreeMap;

import static com.example.sandbox.util.constans.Tags.REGRESSION;
import static com.example.sandbox.util.constans.Tags.SMOKE;

public class UserTest_Fail extends Common {

    @BeforeTest
    public void userSetup(){
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("username", "ThereIsAbsolutelyNoWayThisUserExists");

        Response response = getUrlWithPath(user, pathParams);
        Assert.assertEquals(response.getStatusCode(),404,"Invalid response code");
    }
    @Test(enabled = true, groups = {REGRESSION}, description = "Get user by name", priority = 2)
    public void GetUserByName_Fail_NonExistentUsername(){
        Map<String, String> pathParams = new TreeMap<>();
        pathParams.put("username", "ThereIsAbsolutelyNoWayThisUserExists");

        Response response = getUrlWithPath(user, pathParams);
        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),404, "Invalid response code");
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "Login user", priority = 2)
    public void LoginUser_Fail_InvalidData(){
        Map<String, String> queryParams = new TreeMap<>();
        queryParams.put("username", "ThereIsAbsolutelyNoWayThisUserExists");
        queryParams.put("password", "VerySecurePassword");

        Response response = getUrl(login, queryParams);
        Assert.assertNotEquals(response.getStatusCode(),200,"Invalid response code");
        Assert.assertEquals(response.getStatusCode(),400,"Invalid response code");
    }
}
