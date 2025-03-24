package model.request;

public class UpdateQuantityRequest {
    private int idSize;
    private int quantity;
    private boolean isDecreaseQuantity = false; // Mặc định là false nếu không có

    public int getIdSize() {
        return idSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isDecreaseQuantity() {
        return isDecreaseQuantity;
    }
}
