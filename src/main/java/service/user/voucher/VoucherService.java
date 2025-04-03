package service.user.voucher;

import database.VoucherDAO;
import model.VoucherModel;

import java.util.List;

public class VoucherService {
    private final VoucherDAO voucherDAO = new VoucherDAO();

    public List<VoucherModel> getAllVoucherShipping(){
        return voucherDAO.getAllVoucherShipping();
    }

    public List<VoucherModel> getAllVoucherItems(){
        return voucherDAO.getAllVoucherItems();
    }

    public float calculateDiscountShippingFee(int voucherId, int deliveryId) {
        return voucherDAO.calculateDiscountShippingFee(voucherId, deliveryId);
    }

    public float calculateDiscountItemsFee(int voucherId, List<Integer> listSizeId){
        return voucherDAO.calculateDiscountItemsFee(voucherId, listSizeId);
    }
}
