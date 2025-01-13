package model;

import java.util.*;

public class ProductModel {
    private int id;

    private String name;

    private String nameSize ;
    private String productGroupName ;
    private String productCategoryName ;
    private String colorName ;
    private int stock ;

    private float price;
    private float discount;

    private int groupProductId;
    private  int productCategoryId;

    private int purchaseQuantity;
    private int colorId ;
    private String hexCode ;


    private List<ColorModel> ColorModels =  new ArrayList<ColorModel>();

    public ProductModel() {

    }

    public ProductModel( String name, float price, float discount , int productCategoryId, int groupProductId) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.groupProductId = groupProductId;
        this.productCategoryId = productCategoryId;


    }

    public ProductModel(String name, float price, float discount) {
        this.name = name;
        this.price = price;
        this.discount = discount;

    }
    public ProductModel(int id,String name, float price, float discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;

    }

    public  ProductModel(String name, float price, float discount, int groupProductId) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public String getNameSize() {
        return nameSize;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setNameSize(String nameSize) {
        this.nameSize = nameSize;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getStock() {
        return stock;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
