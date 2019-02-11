import io.restassured.response.Response;
import models.post.FailPostResponse;
import models.post.PostObject;
import models.post.SuccessPostResponse;
import models.post.UserInfo;
import org.junit.Test;

import java.util.Arrays;

import static constants.Constants.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Every.everyItem;
import static org.hamcrest.core.IsEqual.equalTo;

public class BHGETest extends TestBase {

    private String partGetUrl = "/gets";
    private String partPostUrl = "/posts";
    private String postSuccessUrl = "/postSuccess";
    private String postFailedUrl = "/postFailed";

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

        Response createUserResponse = REQUEST
                .body("{\n" +
                        "      \"id\": 1,\n" +
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

        REQUEST.get(postSuccessUrl)
                .then()
                  .statusCode(200)
                .and()
                  .body(CODE, hasItem(successPostResponse.getCode()))
                  .body(DESCRIPTION, hasItem(successPostResponse.getDescription()));
    }


    @Test
    public void postFailRequestTest() {
//        JSONObject postRequest = getPostRequest();
//
//        Response createUserResponse = REQUEST
//                .body(postRequest)
//                .post(partPostUrl);
//
//        createUserResponse
//                .then()
//                .assertThat().statusCode(400);
//
//        FailPostResponse failPostResponse = new FailPostResponse(VALIDATION_ERROR_MESSAGE);
//        assertEquals(failPostResponse.getMessage(), createUserResponse.jsonPath().getString(MESSAGE));


        Response createUserResponse = REQUEST
                .body("{\n" +
                        "      \"id\": 2,\n" +
                        "      \"data\": [\n" +
                        "        {\n" +
                        "          \"name\": \"sensor1\",\n" +
                        "          \"value\": 500,\n" +
                        "          \"isOnline\": true\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }")
                .post(partPostUrl);

        createUserResponse
                .then()
                .assertThat().statusCode(201);

        FailPostResponse failPostResponse = new FailPostResponse(VALIDATION_ERROR_MESSAGE);

        REQUEST.get(postFailedUrl)
                .then()
                .statusCode(200)
                .and()
                .body(CODE, hasItem(failPostResponse.getMessage()));
    }

    private PostObject getPostRequest() {
        UserInfo firstUser = new UserInfo("sensor1", 500, 1538041571928L, true);
        UserInfo secondUser = new UserInfo("sensor2", 700, 1538041571929L, false);

        return new PostObject(1, Arrays.asList(firstUser, secondUser));
    }
}
