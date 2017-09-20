package todolist.balvinder.com.todolist.util;

/**
 * Created by balvinder on 18/9/17.
 */


import java.util.List;

public class ApiResponse {

    private String _id;
    private String name;
    private Integer v;
    private List<String> status = null;
    private String Created_date;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return Created_date;
    }

    public void setCreatedDate(String createdDate) {
        this.Created_date = createdDate;
    }
}
