package model;

import java.sql.Timestamp;
import java.util.List;

public class Order
{
    int id;
    int paymentId ;
    Timestamp orderDate ;
    String deliveryAddress ;
    float totalPrice ;
    int userId ;
    int deliveryId ;
    int statusPayment;

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

    public Order(){}

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

    public void setId(int id) {
        this.id = id;
    }

    public int getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(int statusPayment) {
        this.statusPayment = statusPayment;
    }

    //    public List<ShoppingCartItemOrders> getShoppingCartItemOrders() {
//        return shoppingCartItemOrders;
//    }
//    public void setShoppingCartItemOrders(List<ShoppingCartItemOrders> shoppingCartItemOrders) {
//        this.shoppingCartItemOrders = shoppingCartItemOrders;
//    }


}
