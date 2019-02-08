package models;

public class PostObject {
    public String name;
    public Integer value;
    public Long time;
    public Boolean isOnline;

    public PostObject(){
    }

    public PostObject(String fName, Integer fValue, Long fTime){
        this.name = fName;
        this.value = fValue;
        this.time = fTime;
    }

    public PostObject(String fName, Integer fValue, Long fTime, Boolean fIsOnline){
        this.name = fName;
        this.value = fValue;
        this.time = fTime;
        this.isOnline = fIsOnline;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "PostObject{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", time=" + time +
                ", isOnline=" + isOnline +
                '}';
    }
}
