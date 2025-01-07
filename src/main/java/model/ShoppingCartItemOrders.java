package model;

import java.util.List;

public class ShoppingCartItemOrders {
    public int paymentId;
    public int quantity ;
    public int orderId ;
    public List<Integer> listSizeId ;

    public ShoppingCartItemOrders(int paymentId, int quantity, int orderId, List<Integer> listSizeId) {
        this.paymentId = paymentId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.listSizeId = listSizeId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<Integer> getListSizeId() {
        return listSizeId;
    }
}
