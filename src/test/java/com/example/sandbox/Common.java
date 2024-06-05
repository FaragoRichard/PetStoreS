package com.example.sandbox;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Common extends Endpoints {

    //----------------------------------GET----------------------------------
    public Response getUrl(String endpoint){


        return given()
                .relaxedHTTPSValidation()
                .and()
                .log().everything()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }
    public Response getUrlWithPath(String endpoint, Map<String, String> pathParams){
        return given()
                .relaxedHTTPSValidation()
                .pathParams(pathParams)
                .and()
                .log().everything()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    public Response getUrlWithPath(String endpoint, Map<String, String> queryParam, Map<String, String> pathParams){
        return given()
                .relaxedHTTPSValidation()
                .params(queryParam)
                .pathParams(pathParams)
                .and()
                .log().all()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    public Response getUrl(String endpoint, Map<String, String> queryParam ){


        return given()
                .relaxedHTTPSValidation()
                .params(queryParam)
                .and()
                .log().everything()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }
    public Response getUrl(String endpoint,Map<String, String> headers,Map<String, String> queryParam ){


        return given()
                .relaxedHTTPSValidation()
                .params(queryParam)
                .headers(headers)
                .and()
                .log().all()
                .when()
                .get(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    //----------------------------------POST----------------------------------
    public Response postUrl(String endpoint,String body){


        return given()
                .relaxedHTTPSValidation()
                .contentType("application/json; charset=UTF-8")
                .body(body)
                .and()
                .log().everything()
                .when()
                .post(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    public Response putUrl(String endpoint, String body, Map<String, String> pathParams ){
        return given()
                .relaxedHTTPSValidation()
                .pathParams(pathParams)
                .contentType("application/json")
                .body(body)
                .and()
                .log().everything()
                .when()
                .put(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();
    }

    public Response postUrl(String endpoint, Map<String, String> formParams, Map<String, String> pathParams ){


        return given()
                .relaxedHTTPSValidation()
                .formParams(formParams)
                .pathParams(pathParams)
                .contentType("application/x-www-form-urlencoded")
                .and()
                .log().everything()
                .when()
                .post(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    public Response postFile(String endpoint, String filePath, Map<String, String> pathParams ){


        return given()
                .relaxedHTTPSValidation()
                .multiPart("file", new File(filePath))
                .pathParams(pathParams)
                .contentType(ContentType.MULTIPART)
                .accept(ContentType.JSON)
                .and()
                .log().everything()
                .when()
                .post(baseUrl+endpoint)
                .then()
                .log()
                .all()
                .extract().response();

    }

    //----------------------------------PUT----------------------------------

    //----------------------------------DELETE----------------------------------
}

