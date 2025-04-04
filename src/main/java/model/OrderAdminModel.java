package model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderAdminModel {
    int id;
    String paymentName ;
    Date orderDate ;
    String deliveryAddress ;
    float totalPrice ;;
    String deliveryName ;
    int quantity ;
    int sizeId ;


    public OrderAdminModel(int id, String paymentName, java.sql.Date orderDate, String deliveryAddress, float totalPrice, String deliveryName, int quantity, int sizeId) {
        this.id = id;
        this.paymentName = paymentName;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.deliveryName = deliveryName;
        this.quantity = quantity;
        this.sizeId = sizeId;
    }

    public int getId() {
        return id;
    }

    public String getPaymentName() {
        return paymentName;
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

    public String getDeliveryName() {
        return deliveryName;
    }



    public int getQuantity() {
        return quantity;
    }

    public int getSizeId() {
        return sizeId;
    }
    public String getFormattedTotalPrice() {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(totalPrice) + " đ";
    }

    @Override
    public String toString() {
        return "OrderAdminModel{" +
                "id=" + id +
                ", paymentName='" + paymentName + '\'' +
                ", orderDate=" + orderDate +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", deliveryName='" + deliveryName + '\'' +
                ", quantity=" + quantity +
                ", sizeId=" + sizeId +
                '}';
    }
    public String getFormattedOrderDate() {
        if (orderDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(orderDate);
        }
        return null;
    }
}
