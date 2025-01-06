package model;

public class ShoppingCartItemOrders {
    public int paymentId;
    public int quantity ;
    public int orderId ;
    public int sizeId ;
    public ShoppingCartItemOrders(int paymentId, int quantity, int orderId, int sizeId) {
        this.paymentId = paymentId;
        this.quantity = quantity;
        this.orderId = orderId;
        this.sizeId = sizeId;
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

    public int getSizeId() {
        return sizeId;
    }
}
