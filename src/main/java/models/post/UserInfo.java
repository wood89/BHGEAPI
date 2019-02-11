package models.post;

public class UserInfo {

    public String name;
    public Integer value;
    public Long time;
    public Boolean isOnline;

    public UserInfo() {
    }

    public UserInfo(String name, Integer value, Long time) {
        this.name = name;
        this.value = value;
        this.time = time;
    }

    public UserInfo(String name, Integer value, Long time, Boolean isOnline) {
        this.name = name;
        this.value = value;
        this.time = time;
        this.isOnline = isOnline;
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
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", time=" + time +
                ", isOnline=" + isOnline +
                '}';
    }
}
