package models.post;

import java.util.List;

public class PostObject {

    private Integer id;
    private List<UserInfo> data;

    public PostObject() {
    }

    public PostObject(Integer id, List<UserInfo> data) {
        this.id = id;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<UserInfo> getData() {
        return data;
    }

    public void setData(List<UserInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PostObject{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
