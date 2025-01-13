package model;

public class StatusAdminModel {
    int id ;
    String name ;
    int orderId ;
    String description ;

    public StatusAdminModel(int id, String name, int orderId, String description) {
        this.id = id;
        this.name = name;
        this.orderId = orderId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDescription() {
        return description;
    }
}
