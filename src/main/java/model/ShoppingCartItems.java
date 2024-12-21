package model;

public class ShoppingCartItems {

    int id ;
    int quantity ;
    int productDetailId;
    int orderId ;
    int userId ;

    public ShoppingCartItems(int id, int quantity, int productDetailId) {
        this.id = id;
        this.quantity = quantity;
        this.productDetailId = productDetailId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
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

    public int getProductDetailId() {
        return productDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }
}
