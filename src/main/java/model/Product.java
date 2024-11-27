package model;

import java.io.InputStream;

public class Product {
    private String nameProduct;
    private float price;
    private int quantity;
    private int idProductCategory;
    private InputStream image;
    private String imageBase64;

    public Product(String nameProduct, float price, int quantity, int idProductCategory, InputStream image) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.quantity = quantity;
        this.idProductCategory = idProductCategory;
        this.image = image;
    }

    public Product() {

    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdProductCategory() {
        return idProductCategory;
    }

    public void setIdProductCategory(int idProductCategory) {
        this.idProductCategory = idProductCategory;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public String getImageBase64() {
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
