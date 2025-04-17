package service.user.voucher;

import database.TypeVoucherDAO;
import model.TypeVoucherModel;
import model.VoucherModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeVoucherService {
    private final TypeVoucherDAO typeVoucherDAO;

    public TypeVoucherService(TypeVoucherDAO typeVoucherDAO) {
        this.typeVoucherDAO = typeVoucherDAO;
    }

    public void addTypeVoucher(TypeVoucherModel typeVoucher) {
        typeVoucherDAO.addTypeVoucher(typeVoucher);
    }

    public TypeVoucherModel updateTypeVoucher(TypeVoucherModel typeVoucher){
        return typeVoucherDAO.updateTypeVoucher(typeVoucher);
    }

    public void deleteTypeVoucher(int typeVoucherId){
        typeVoucherDAO.deleteTypeVoucher(typeVoucherId);
    }

    public List<TypeVoucherModel> getAllTypeVouchers(){
        return typeVoucherDAO.getAllTypeVouchers();
    }

    public Map<Integer, String> getTypeVoucherDescriptions(List<VoucherModel> allVouchers) {
        Map<Integer, String> typeVoucherDescriptions = new HashMap<>();

        // Duyệt qua tất cả vouchers và lấy description từ TypeVoucherModel
        for (VoucherModel voucher : allVouchers) {
            int typeVoucherId = voucher.getTypeVoucherId();
            if (!typeVoucherDescriptions.containsKey(typeVoucherId)) {
                // Lấy description từ database chỉ khi chưa có trong map
                TypeVoucherModel typeVoucher = typeVoucherDAO.getTypeVoucherById(typeVoucherId);
                if (typeVoucher != null) {
                    typeVoucherDescriptions.put(typeVoucherId, typeVoucher.getDescription());
                }
            }
        }
        return typeVoucherDescriptions;
    }
}
