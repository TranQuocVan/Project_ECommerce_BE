package model;

public class TypeVoucherModel {
    private int typeVoucherId;
    private String typeName;

    public TypeVoucherModel() {}

    public TypeVoucherModel(int typeVoucherId, String typeName) {
        this.typeVoucherId = typeVoucherId;
        this.typeName = typeName;
    }

    public int getTypeVoucherId() {
        return typeVoucherId;
    }

    public void setTypeVoucherId(int typeVoucherId) {
        this.typeVoucherId = typeVoucherId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "TypeVoucherModel{" +
                "typeVoucherId=" + typeVoucherId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
