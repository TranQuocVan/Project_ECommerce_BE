package model;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class ShoppingCartItemsModel {
    private String nameProduct;
    private float price;
    private String nameColor ;
    private String nameSize ;
    private int stock;
    private int quantity;

    public ShoppingCartItemsModel(String nameProduct, float price, String nameColor, String nameSize, int stock, int quantity) {
        this.nameProduct = nameProduct;
        this.price = price;
        this.nameColor = nameColor;

        this.nameSize = nameSize;
        this.stock = stock;
        this.quantity = quantity;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public float getPrice() {
        return price;
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
}
