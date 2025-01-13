package model;


import java.util.*;

public class ColorModel {

    private int id;

    private String name;

    private  String hexCode;

    List<SizeModel> sizeModels = new ArrayList<SizeModel>();

    List<ImageModel> imageModels = new ArrayList<ImageModel>();

    public ColorModel() {

    }
    public ColorModel(String name , String hexCode) {
        this.name = name;
        this.hexCode = hexCode;
    }

    public  ColorModel(String name , String hexCode, List<SizeModel> sizeModels) {
        this.name = name;
        this.hexCode = hexCode;
        this.sizeModels = sizeModels;
    }

    public  ColorModel(int id,String name , String hexCode) {
        this.id = id;
        this.name = name;
        this.hexCode = hexCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SizeModel> getSizeModels() {
        return sizeModels;
    }

    public void setSizeModels(List<SizeModel> sizeModels) {
        this.sizeModels = sizeModels;
    }

    public String getName() {
        return name;
    }

    public String getHexCode() {
        return hexCode;
    }

    public List<ImageModel> getImageModels() {
        return imageModels;
    }

    public void setImageModels(List<ImageModel> imageModels) {
        this.imageModels = imageModels;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }

    public void setName(String name) {
        this.name = name;
    }


}

