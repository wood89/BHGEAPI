import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import io.restassured.response.Response;
import models.FailPostResponse;
import models.PostObject;
import models.SuccessPostResponse;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static constants.Constants.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

public class BHGETest extends TestBase {

    private String partGetUrl = "/get?from=1538041571929&to=1538041571930";
    private String partPostUrl = "/post";

    @Test
    public void getSuccessRequestTest(){
        REQUEST.get(partGetUrl)
                .then()
                    .statusCode(200)
                .and()
                    .body(TIME, equalTo(1538041571929L))
                    .body(NAME, equalTo( "sensor2"))
                    .body(VALUE, equalTo(700));
    }

    @Test
    public void postSuccessRequestTest() {
        PostObject firstPostObject = new PostObject("sensor1", 500, 1538041571928L, true);
        PostObject secondPostObject = new PostObject("sensor2", 700, 1538041571929L, false);

        JSONObject firstObject = getJsonObject(firstPostObject);
        JSONObject secondObject = getJsonObject(secondPostObject);

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        Response createUserResponse = REQUEST
                .body(requestBody)
                .post(partPostUrl);

        createUserResponse
                .then()
                    .assertThat().statusCode(201);

        SuccessPostResponse successPostResponse = new SuccessPostResponse(OK, OK);
        assertEquals(successPostResponse.getCode(), createUserResponse.jsonPath().getString(CODE));
        assertEquals(successPostResponse.getDescription(), createUserResponse.jsonPath().getString(DESCRIPTION));
    }


    @Test
    public void postFailRequestTest() {
        PostObject firstPostObject = new PostObject("sensor1", 500, 1L, true);
        PostObject secondPostObject = new PostObject("sensor2", 700, 1538041571929L, false);

        JSONObject firstObject = getJsonObject(firstPostObject);
        JSONObject secondObject = getJsonObject(secondPostObject);

        List<JSONObject> requestBody = new ArrayList<JSONObject>();
        requestBody.add(firstObject);
        requestBody.add(secondObject);

        Response createUserResponse = REQUEST
                .body(requestBody)
                .post(partPostUrl);

        createUserResponse
                .then()
                .assertThat().statusCode(400);

        FailPostResponse failPostResponse = new FailPostResponse(VALIDATION_ERROR_MESSAGE);
        assertEquals(failPostResponse.getMessage(), createUserResponse.jsonPath().getString(MESSAGE));
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