package model.request;

public class StockRequest {
    private int idSize;
    private int quantity = 1; // Mặc định là 1 nếu không có

    public int getIdSize() {
        return idSize;
    }

    public int getQuantity() {
        return quantity;
    }
}
