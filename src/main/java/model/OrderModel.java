package model;

import java.sql.Timestamp;
import java.util.List;

public class OrderModel {
    int id;
    String paymentName ;
    String orderDate ;
    String deliveryAddress ;
    float totalPrice ;;
    String deliveryName ;
    float deliveryPrice ;
    String nameStatus ;
    String statusPayment;
    String sign;
    String publishKey;



    private List<ProductModel> productModels ;

    private List<StatusModel> statusModels ;


    public OrderModel(int id, String paymentName, String orderDate, String deliveryAddress, float totalPrice, String deliveryName,float deliveryPrice, String statusPayment) {
        this.id = id;
        this.paymentName = paymentName;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.deliveryName = deliveryName;
        this.deliveryPrice = deliveryPrice;
        this.statusPayment = statusPayment;
    }
    public OrderModel(int id, String paymentName, String orderDate, String deliveryAddress, float totalPrice, String deliveryName, String nameStatus) {
        this.id = id;
        this.paymentName = paymentName;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.deliveryName = deliveryName;
        this.nameStatus = nameStatus;
    }

    public OrderModel() {}

    public int getId() {
        return id;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }

    public void setStatusModels(List<StatusModel> statusModels) {
        this.statusModels = statusModels;
    }
    public List<StatusModel> getStatusModels() {
        return statusModels;
    }

    public float getDeliveryPrice() {
        return deliveryPrice;
    }
    public void setDeliveryPrice(float deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getPublishKey() {
        return publishKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setPublishKey(String publishKey) {
        this.publishKey = publishKey;
    }

    public OrderModel(String publishKey,String sign){
        this.sign = sign;
        this.publishKey = publishKey;
    }
}
