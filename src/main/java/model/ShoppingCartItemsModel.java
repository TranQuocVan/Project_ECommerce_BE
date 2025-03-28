package model;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ShoppingCartItemsModel {
    private int sizeId;
    private String nameProduct;
    private float price;
    private String nameColor ;
    private String nameSize ;
    private int stock;
    private int quantity;
    private int discount;
    private float discountPrice;

    public ShoppingCartItemsModel(int sizeId,int discount, String nameProduct, float price, String nameColor, String nameSize, int stock, int quantity, float discountPrice) {
        this.nameProduct = nameProduct;
        this.discount = discount;
        this.price = price;
        this.nameColor = nameColor;

        this.sizeId = sizeId;
        this.nameSize = nameSize;
        this.stock = stock;
        this.quantity = quantity;
        this.discountPrice = discountPrice;
    }

    public float getDiscountPrice() {
        return discountPrice;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public float getPrice() {
        return price;
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getFormattedPrice() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("#,###", symbols);
        return df.format(price) + "đ";
    }

    public String getNameColor() {
        return nameColor;
    }


    public String getNameSize() {
        return nameSize;
    }

    public int getStock() {
        return stock;
    }
    public int getQuantity() {
        return quantity;
    }

    public int getSizeId() {
        return sizeId;
    }
}
