package model;

public class StatusModel {
    private int id;       // ID của trạng thái (khóa chính)
    private String name;        // Tên của trạng thái
    private int orderId;        // ID của đơn hàng liên quan
    private String description; // Mô tả trạng thái

    // Constructor không tham số
    public StatusModel() {
    }

    // Constructor có tham số
    public StatusModel(int statusId, String name, int orderId, String description) {
        this.id = statusId;
        this.name = name;
        this.orderId = orderId;
        this.description = description;
    }

    // Getter và Setter cho statusId
    public int getStatusId() {
        return id;
    }

    public void setStatusId(int statusId) {
        this.id = statusId;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho orderId
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // Getter và Setter cho description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
