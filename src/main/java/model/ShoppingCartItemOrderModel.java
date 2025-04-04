package model;

public class    ShoppingCartItemOrderModel {

    public int orderId ;
    public  int sizeId ;
    public  int quantity ;

    public ShoppingCartItemOrderModel() {}

    public ShoppingCartItemOrderModel(int orderId, int sizeId, int quantity) {
        this.orderId = orderId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }


   public int getOrderId() {
        return orderId;
   }
   public void setOrderId(int orderId) {
        this.orderId = orderId;
   }
   public int getSizeId() {
        return sizeId;
   }
   public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
   }
   public int getQuantity() {
        return quantity;
   }
   public void setQuantity(int quantity) {
        this.quantity = quantity;
   }


}
