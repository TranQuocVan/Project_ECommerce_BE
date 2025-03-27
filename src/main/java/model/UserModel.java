package model;

public class UserModel {
    private int id;
    private String gmail;
    private String password;
    private String role;
    String rememberMeToken;
    long facebook_id;

    public UserModel(int id ,String gmail,String role) {
        super();
        this.gmail = gmail;
        this.role = role;
        this.id = id;
    }
    public UserModel(int id ,String gmail, String password, String role) {
        super();
        this.gmail = gmail;
        this.password = password;
        this.role = role;
        this.id = id;
    }
    public UserModel(String gmail, String password, String role) {
        super();
        this.gmail = gmail;
        this.password = password;
        this.role = role;
    }

    public UserModel(String gmail, String password) {
        super();
        this.gmail = gmail;
        this.password = password;
    }

    public UserModel() {

    }

    public long getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(long facebook_id) {
        this.facebook_id = facebook_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getGmail() {
        return gmail;
    }
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getRememberMeToken() {
        return rememberMeToken;
    }

    public void setRememberMeToken(String rememberMeToken) {
        this.rememberMeToken = rememberMeToken;
    }

}
