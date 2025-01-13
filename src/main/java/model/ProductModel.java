package model;

import java.util.*;

public class ProductModel {
    private int id;

    private String name;

    private double price;
    private float discount;

    private int groupProductId;
    private  int productCategoryId;

    private int purchaseQuantity;

    private List<ColorModel> ColorModels =  new ArrayList<ColorModel>();

    public ProductModel() {

    }

    public ProductModel( String name, double price, float discount , int productCategoryId, int groupProductId) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.groupProductId = groupProductId;
        this.productCategoryId = productCategoryId;


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

    public  ProductModel(String name, double price, float discount, int groupProductId) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.groupProductId = groupProductId;

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

    public int getGroupProductId() {
        return groupProductId;
    }
    public void setGroupProductId(int groupProductId) {
        this.groupProductId = groupProductId;
    }
    public int getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }
    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }





}
