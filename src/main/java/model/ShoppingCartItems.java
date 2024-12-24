package model;

public class ShoppingCartItems {

    int id ;
    int quantity ;
    int sizeId;
    int userId ;


    public ShoppingCartItems(int quantity, int sizeId, int userId) {

        this.quantity = quantity;
        this.sizeId = sizeId;
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductDetailId(int sizeId) {
        this.sizeId = sizeId;
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



    public int getUserId() {
        return userId;
    }
}
