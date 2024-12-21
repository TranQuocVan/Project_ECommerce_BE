package model;

public class ShoppingCartItems {

    int id ;
    int quantity ;
    int sizeId;
    int orderId ;
    int userId ;

    public ShoppingCartItems(int id, int quantity, int sizeId) {
        this.id = id;
        this.quantity = quantity;
        this.sizeId = sizeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShoppingCartItems(int quantity, int sizeId, int orderId, int userId) {
        this.quantity = quantity;
        this.sizeId = sizeId;
        this.orderId = orderId;
        this.userId = userId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductDetailId(int sizeId) {
        this.sizeId = sizeId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSizeId() {
        return sizeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }
}
