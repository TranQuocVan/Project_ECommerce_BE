package model;

import java.sql.Timestamp;

public class UserVoucherModel {
    private int userVoucherId;
    private int userId;
    private int voucherId;
    private Timestamp usedAt;

    public UserVoucherModel() {}

    public UserVoucherModel(int userVoucherId, int userId, int voucherId, Timestamp usedAt) {
        this.userVoucherId = userVoucherId;
        this.userId = userId;
        this.voucherId = voucherId;
        this.usedAt = usedAt;
    }

    public int getUserVoucherId() {
        return userVoucherId;
    }

    public void setUserVoucherId(int userVoucherId) {
        this.userVoucherId = userVoucherId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Timestamp getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Timestamp usedAt) {
        this.usedAt = usedAt;
    }
}
