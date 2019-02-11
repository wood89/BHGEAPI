import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.FailPostResponse;
import models.PostObject;
import models.SuccessPostResponse;
import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

public class BHGETest extends TestBase {

    private String partGetUrl = "/gets";
    private String partPostUrl = "/posts";

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://my-json-server.typicode.com/wood89/BHGEAPI")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL).build();

    public static String PET_ENDPOINT = "https://my-json-server.typicode.com/wood89/BHGEAPI" + "/posts";

    @Test
    public void getSuccessRequestTest(){
        REQUEST.get(partGetUrl)
                .then()
                    .statusCode(200)
                .and()
                    .body(TIME, everyItem(equalTo(1538041571929L)))
                    .body(NAME, hasItem("sensor2"))
                    .body(VALUE, everyItem(equalTo(700)));
    }

    @Test
    public void postSuccessRequestTest() {
        JSONObject postRequest = getPostRequest();

        System.out.println(postRequest);

        Response createUserResponse = REQUEST
                .body("{\n" +
                        "      \"id\": 71,\n" +
                        "      \"data\": [\n" +
                        "        {\n" +
                        "          \"name\": \"sensor1\",\n" +
                        "          \"value\": 500,\n" +
                        "          \"time\": 1538041571928,\n" +
                        "          \"isOnline\": true\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"name\": \"sensor2\",\n" +
                        "          \"value\": 700,\n" +
                        "          \"time\": 1538041571929,\n" +
                        "          \"isOnline\": false\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }")
                .post(partPostUrl);

        createUserResponse
                .then()
                    .assertThat().statusCode(201);

        SuccessPostResponse successPostResponse = new SuccessPostResponse(OK, OK);

        REQUEST.get("/postSuccess")
                .then()
                  .statusCode(200)
                .and()
                  .body(CODE, hasItem(successPostResponse.getCode()))
                  .body(DESCRIPTION, hasItem(successPostResponse.getDescription()));

        
//        assertEquals(successPostResponse.getCode(), createUserResponse.jsonPath().getString(CODE));
//        assertEquals(successPostResponse.getDescription(), createUserResponse.jsonPath().getString(DESCRIPTION));


//         given(requestSpecification)
//                .body("{\n" +
//                        "      \"id\": 60,\n" +
//                        "      \"data\": [\n" +
//                        "        {\n" +
//                        "          \"name\": \"sensor1\",\n" +
//                        "          \"value\": 500,\n" +
//                        "          \"time\": 1538041571928,\n" +
//                        "          \"isOnline\": true\n" +
//                        "        },\n" +
//                        "        {\n" +
//                        "          \"name\": \"sensor2\",\n" +
//                        "          \"value\": 700,\n" +
//                        "          \"time\": 1538041571929,\n" +
//                        "          \"isOnline\": false\n" +
//                        "        }\n" +
//                        "      ]\n" +
//                        "    }")
//                .post(PET_ENDPOINT)
//                .then().statusCode(201);

//        given(requestSpecification)
//                .pathParam("id", "59")
//                .get(PET_ENDPOINT + "/{id}")
//                .then()
//                .body("id", CoreMatchers.equalTo(59))
//                .extract().body().jsonPath()
//                .prettyPrint();
    }


    @Test
    public void postFailRequestTest() {
        JSONObject postRequest = getPostRequest();

        Response createUserResponse = REQUEST
                .body(postRequest)
                .post(partPostUrl);

        createUserResponse
                .then()
                .assertThat().statusCode(400);

        FailPostResponse failPostResponse = new FailPostResponse(VALIDATION_ERROR_MESSAGE);
        assertEquals(failPostResponse.getMessage(), createUserResponse.jsonPath().getString(MESSAGE));
    }

    private JSONObject getPostRequest() {
        PostObject firstPostObject = new PostObject("sensor1", 500, 1538041571928L, true);
        PostObject secondPostObject = new PostObject("sensor2", 700, 1538041571929L, false);

        JSONObject firstObject = getJsonObject(firstPostObject);
        JSONObject secondObject = getJsonObject(secondPostObject);

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        JSONObject postRequest = new JSONObject();
        postRequest.put(ID, 1);
        postRequest.put(DATA, requestBody);

        return postRequest;
    }


    private JSONObject getJsonObject(PostObject postObject) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(NAME, postObject.getName());
        jsonObject.put(VALUE, postObject.getValue());
        jsonObject.put(TIME, postObject.getTime());
        jsonObject.put(IS_ONLINE, postObject.getOnline());

        return jsonObject;
    }
}
