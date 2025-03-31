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

    public boolean addVoucher(VoucherModel voucher) {
        try {
            return voucherDAO.addVoucher(voucher);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi (có thể thay bằng Logger)
            return false;
        }
    }

    public boolean updateVoucher(VoucherModel voucher) {
        try {
            return voucherDAO.updateVoucher(voucher);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVoucher(int voucherId) {
        try {
            return voucherDAO.deleteVoucher(voucherId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VoucherModel> getAllVoucherShipping() {
        return voucherDAO.getAllVoucherShipping();
    }

    public List<VoucherModel> getAllVoucherItems() {
        return voucherDAO.getAllVoucherItems();
    }
}
