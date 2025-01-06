package model;

import java.sql.Timestamp;

public class OrderModel {
    int id;
    String paymentName ;
    Timestamp orderDate ;
    String deliveryAddress ;
    float totalPrice ;;
    String deliveryName ;
    String nameStatus ;


    public OrderModel(int id, String paymentName, Timestamp orderDate, String deliveryAddress, float totalPrice, String deliveryName) {
        this.id = id;
        this.paymentName = paymentName;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.deliveryName = deliveryName;

    }

    public int getId() {
        return id;
    }

    public String getPaymentName() {
        return paymentName;
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

    public String getDeliveryName() {
        return deliveryName;
    }

    public String getNameStatus() {
        return nameStatus;
    }
}
