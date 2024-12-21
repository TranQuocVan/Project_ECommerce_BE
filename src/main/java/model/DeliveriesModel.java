package model;

public class DeliveriesModel
{
    int id ;
    float fee ;
    String name ;

    public DeliveriesModel(int id, float fee, String name) {
        this.id = id;
        this.fee = fee;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public float getFee() {
        return fee;
    }

    public String getName() {
        return name;
    }
}
