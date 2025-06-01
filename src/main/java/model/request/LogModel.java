package model.request;

public class LogModel {
    private int id;
    private int userId;
    private String action;
    private String tableName;
    private String dataBefore;
    private String dataAfter;
    private String ipAddress;
    private String timestamp;  // giả sử bảng logs có cột timestamp

    // Constructor đầy đủ (đúng tên class)
    public LogModel(int id, int userId, String action, String tableName,
                    String dataBefore, String dataAfter, String ipAddress, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.action = action;
        this.tableName = tableName;
        this.dataBefore = dataBefore;
        this.dataAfter = dataAfter;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    // Constructor mặc định (nếu cần)
    public LogModel() {}

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataBefore() {
        return dataBefore;
    }

    public void setDataBefore(String dataBefore) {
        this.dataBefore = dataBefore;
    }

    public String getDataAfter() {
        return dataAfter;
    }

    public void setDataAfter(String dataAfter) {
        this.dataAfter = dataAfter;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
