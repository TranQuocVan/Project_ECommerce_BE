package model;

public class DeliveryModel {
    private int id;
    private float fee;
    private String name;

    public DeliveryModel() {}

    public DeliveryModel(int id, float fee, String name) {
        this.id = id;
        this.fee = fee;
        this.name = name;
    }
}
