package model;

import java.io.InputStream;

public class ImageModel {

    private int id;

    private  InputStream image;
    private String imageBase64;

    public ImageModel() {

    }

    public ImageModel(InputStream image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getImageBase64() {
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
