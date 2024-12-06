package model;

import java.util.*;

public class ProductModel {
    private int id;

    private String name;

    private double price;
    private float discount;

    private List<ColorModel> ColorModels =  new ArrayList<ColorModel>();

    public ProductModel( String name, double price, float discount , List<ColorModel> ColorModels) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.ColorModels = ColorModels;


    }

    public ProductModel(String name, double price, float discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;

    }
    public ProductModel(int id,String name, double price, float discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ColorModel> getColorModels(){
        return ColorModels;
    }
    public void setColorModels(List<ColorModel> ColorModels) {
        this.ColorModels = ColorModels;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public float getDiscount() {
        return discount;
    }
}
