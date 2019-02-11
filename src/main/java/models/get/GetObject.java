package models.get;

public class GetObject {

    public Long time;
    public String name;
    public Integer value;

    public GetObject() {
    }

    public GetObject(Long time, String name, Integer value) {
        this.time = time;
        this.name = name;
        this.value = value;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "GetObject{" +
                "time=" + time +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
