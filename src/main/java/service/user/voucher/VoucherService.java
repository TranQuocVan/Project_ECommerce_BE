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

    public int getDiscountShippingFee(int voucherId, int fee) {
        return voucherDAO.getDiscountShippingFee(voucherId, fee);
    }
}
