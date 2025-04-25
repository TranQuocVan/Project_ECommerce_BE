package model.request;

public class UserPublicKeyModel {
    private String gmail;
    private String publishKey;

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getGmail() {
        return gmail;
    }

    public void setPublicKey(String publishKey) {
        this.publishKey = publishKey;
    }
    public String getPublicKey() {
        return publishKey;
    }
}

