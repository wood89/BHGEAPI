import io.restassured.response.Response;
import models.FailPostResponse;
import models.PostObject;
import models.SuccessPostResponse;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

public class BHGETest extends TestBase {

    @Test
    public void getSuccessRequestTest(){
        REQUEST.get("/get?from=1538041571929&to=1538041571930")
                .then()
                    .statusCode(200)
                .and()
                    .body("time", equalTo(1538041571929L))
                    .body("name", equalTo( "sensor2"))
                    .body("value", equalTo(700));
    }

    @Test
    public void postSuccessRequestTest() {
        PostObject firstPostObject = new PostObject("sensor1", 500, 1538041571928L, true);
        PostObject secondPostObject = new PostObject("sensor2", 700, 1538041571929L, false);

        JSONObject firstObject = new JSONObject();
        firstObject.put("name", firstPostObject.getName());
        firstObject.put("value", firstPostObject.getValue());
        firstObject.put("time", firstPostObject.getTime());
        firstObject.put("isOnline", firstPostObject.getOnline());

        JSONObject secondObject = new JSONObject();
        secondObject.put("name", secondPostObject.getName());
        secondObject.put("value", secondPostObject.getValue());
        secondObject.put("time", secondPostObject.getTime());
        secondObject.put("isOnline", secondPostObject.getOnline());

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        Response createUserResponse = REQUEST
                .body(requestBody)
                .post("/post");

        createUserResponse
                .then()
                    .assertThat().statusCode(201);

        SuccessPostResponse successPostResponse = new SuccessPostResponse("ok", "ok");
        assertEquals(successPostResponse.getCode(), createUserResponse.jsonPath().getString("code"));
        assertEquals(successPostResponse.getDescription(), createUserResponse.jsonPath().getString("description"));
    }


    @Test
    public void postFailRequestTest() {
        PostObject firstPostObject = new PostObject("sensor1", 500, 1L, true);
        PostObject secondPostObject = new PostObject("sensor2", 700, 1538041571929L, false);

        JSONObject firstObject = new JSONObject();
        firstObject.put("name", firstPostObject.getName());
        firstObject.put("value", firstPostObject.getValue());
        firstObject.put("time", firstPostObject.getTime());
        firstObject.put("isOnline", firstPostObject.getOnline());

        JSONObject secondObject = new JSONObject();
        secondObject.put("name", secondPostObject.getName());
        secondObject.put("value", secondPostObject.getValue());
        secondObject.put("time", secondPostObject.getTime());
        secondObject.put("isOnline", secondPostObject.getOnline());

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        Response createUserResponse = REQUEST
                .body(requestBody)
                .post("/post");

        createUserResponse
                .then()
                .assertThat().statusCode(400);

        FailPostResponse failPostResponse = new FailPostResponse("validation error");
        assertEquals(failPostResponse.getMessage(), createUserResponse.jsonPath().getString("message"));
    }
}
