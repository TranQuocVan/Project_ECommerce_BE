package model;

import java.sql.Date;
import java.sql.Timestamp; // Đảm bảo import đúng java.sql.Timestamp
import java.time.LocalDate;

public class StatusModel {
    private int id;              // ID của trạng thái (khóa chính)
    private String name;         // Tên của trạng thái
    private int orderId;         // ID của đơn hàng liên quan
    private String description;  // Mô tả trạng thái
    private Timestamp timeline;

    // Constructor không tham số
    public StatusModel() {
    }

    // Constructor có tham số
    public StatusModel(int id, String name, int orderId, String description, Timestamp timeline) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
        this.description = description;
        this.timeline = timeline;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
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

    public Timestamp getTimeline() {
        return timeline;
    }
    public void setTimeline(Timestamp timeline) {
        this.timeline = timeline;
    }
}
