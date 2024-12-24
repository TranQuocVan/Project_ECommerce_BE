package model;

import java.sql.Date;

public class OrderModel
{
    int id;
    int paymentId ;
    Date orderDate ;
    String deliveryAddress ;
    float totalPrice ;
    int userId ;
    int deliveryId ;

    public OrderModel(int paymentId, Date orderDate, String deliveryAddress, float totalPrice, int userId, int deliveryId) {

        this.paymentId = paymentId;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.deliveryId = deliveryId;
    }

    public OrderModel(Date orderDate,float totalPrice,int userId) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.userId = userId;
    }
    public OrderModel(Date orderDate,int userId) {
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Date getOrderDate() {
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
