package model;

import java.sql.Timestamp;

public class Order
{
    int id;
    int paymentId ;
    Timestamp orderDate ;
    String deliveryAddress ;
    float totalPrice ;
    int userId ;
    int deliveryId ;

    public Order(int paymentId, Timestamp orderDate, String deliveryAddress, float totalPrice, int userId, int deliveryId) {

        this.paymentId = paymentId;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.deliveryId = deliveryId;
    }

    public Order(Timestamp orderDate, float totalPrice, int userId) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.userId = userId;
    }
    public Order(Timestamp orderDate, int userId) {
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public int getUserId() {
        return userId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }


}
