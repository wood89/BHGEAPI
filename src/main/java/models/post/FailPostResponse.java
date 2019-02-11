package models.post;

public class FailPostResponse {

    public String message;

    public FailPostResponse() {
    }

    public FailPostResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FailPostResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
