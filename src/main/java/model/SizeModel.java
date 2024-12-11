package model;

public class SizeModel {

    private int id;

    private String size;

    private  int stock;
    public SizeModel() {

    }

    public SizeModel(String size, int stock) {
        this.size = size;
        this.stock = stock;
    }
    public SizeModel(int id,String size, int stock) {
        this.id = id;
        this.size = size;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
}
