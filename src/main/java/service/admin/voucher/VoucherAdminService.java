package service.admin.voucher;

import database.VoucherDAO;
import model.VoucherModel;

import java.util.List;

public class VoucherAdminService {
    private final VoucherDAO voucherDAO;

    public VoucherAdminService(VoucherDAO voucherDAO) {
        this.voucherDAO = voucherDAO;
    }

    public List<VoucherModel> getAllVouchers() {
        return voucherDAO.getAllVouchers();
    }

    public void addVoucher(VoucherModel voucher){
        voucherDAO.addVoucher(voucher);
    }

    public VoucherModel updateVoucher(VoucherModel voucher){
        return voucherDAO.updateVoucher(voucher);
    }

    public void deleteVoucher(int voucherId){
        voucherDAO.deleteVoucher(voucherId);
    }
}
