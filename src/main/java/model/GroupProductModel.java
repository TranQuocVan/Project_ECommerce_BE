package model;

import java.io.InputStream;

public class GroupProductModel {
    private int id;
    private String name;
    private InputStream image;

    public GroupProductModel(String name) {
        this.name = name;
    }
    public GroupProductModel(String name, InputStream image) {
        this.name = name;
        this.image = image;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getImage() {
        return image;
    }
    public void setImage(InputStream image) {
        this.image = image;
    }
}
