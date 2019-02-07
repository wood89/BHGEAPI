package com.bhge.project;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

public class RestAssuredTestArticle {

    @Test
    public void postSuccessRequestTest() {

        JSONObject firstObject = new JSONObject();
        firstObject.put("name", "sensor1");
        firstObject.put("value", 500);
        firstObject.put("time", 1538041571928L);
        firstObject.put("isOnline", true);

        JSONObject secondObject = new JSONObject();
        secondObject.put("name", "sensor2");
        secondObject.put("value", 700);
        secondObject.put("time", 1538041571929L);
        secondObject.put("isOnline", false);

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        System.out.println(requestBody);


        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://url/post");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        String successCode = response.jsonPath().get("code");
        String successDescription = response.jsonPath().get("description");
        Assert.assertEquals(successCode, "ok");
        Assert.assertEquals(successDescription, "ok");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void postFailRequestTest() {

        JSONObject firstObject = new JSONObject();
        firstObject.put("name", "sensor1");
        firstObject.put("value", 500);
        firstObject.put("time", 1);
        firstObject.put("isOnline", true);

        JSONObject secondObject = new JSONObject();
        secondObject.put("name", "sensor2");
        secondObject.put("value", 700);
        secondObject.put("time", 1538041571929L);
        secondObject.put("isOnline", false);

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);


        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        request.body(requestBody.toString());
        Response response = request.post("https://url/post");

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 400);
        String message = response.jsonPath().get("message");
        Assert.assertEquals(message, "”validation error");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void getSuccessRequestTest() throws JSONException {
        Response response = get("https://url/get?from=1538041571929&to=1538041571930");
        JSONArray jsonResponse = new JSONArray(response.asString());
        String time = jsonResponse.getJSONObject(0).getString("time");
        String name = jsonResponse.getJSONObject(0).getString("name");
        String value = jsonResponse.getJSONObject(0).getString("value");
        Assert.assertEquals(Long.parseLong(time), 1538041571929L);
        Assert.assertEquals(name, "sensor2");
        Assert.assertEquals(Integer.parseInt(value), 700);


//        given()
//                .get("https://url/get?from=1538041571929&to=1538041571930")
//                .then()
//                .body("[0].time", Matchers.equalTo(1538041571929L))
//                .body("[0].name", Matchers.equalTo("sensor2"))
//                .body("[0].value", Matchers.equalTo(700));
    }
}
