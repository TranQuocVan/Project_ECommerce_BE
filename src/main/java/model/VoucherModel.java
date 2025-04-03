package model;

public class VoucherModel {
    private int voucherId;
    private int typeVoucherId;
    private float discountPercent;
    private float discountMaxValue;
    private String startDate;
    private String endDate;
    private int quantity;

    public VoucherModel() {}

    public VoucherModel(int voucherId, int typeVoucherId, float discountPercent, float discountMaxValue, String startDate, String endDate, int quantity) {
        this.voucherId = voucherId;
        this.typeVoucherId = typeVoucherId;
        this.discountPercent = discountPercent;
        this.discountMaxValue = discountMaxValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getTypeVoucherId() {
        return typeVoucherId;
    }

    public void setTypeVoucherId(int typeVoucherId) {
        this.typeVoucherId = typeVoucherId;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public float getDiscountMaxValue() {
        return discountMaxValue;
    }

    public void setDiscountMaxValue(float discountMaxValue) {
        this.discountMaxValue = discountMaxValue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "VoucherModel{" +
                "voucherId=" + voucherId +
                ", typeVoucherId=" + typeVoucherId +
                ", discountPercent=" + discountPercent +
                ", discountMaxValue=" + discountMaxValue +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
