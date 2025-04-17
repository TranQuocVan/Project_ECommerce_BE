package model;

public class TypeVoucherModel {
    private int typeVoucherId;
    private String typeName;
    private String description;

    public TypeVoucherModel() {}

    public TypeVoucherModel(int typeVoucherId, String typeName, String description) {
        this.typeVoucherId = typeVoucherId;
        this.typeName = typeName;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TypeVoucherModel{" +
                "typeVoucherId=" + typeVoucherId +
                ", typeName='" + typeName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
