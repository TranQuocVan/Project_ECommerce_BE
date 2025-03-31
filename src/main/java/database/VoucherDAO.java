package database;

import model.VoucherModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {

    // Lấy tất cả voucher
    public List<VoucherModel> getAllVouchers() {
        List<VoucherModel> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM Voucher";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                VoucherModel voucher = new VoucherModel(
                        rs.getInt("voucherId"),
                        rs.getInt("typeVoucherId"),
                        rs.getFloat("discountPercent"),
                        rs.getFloat("discountMaxValue"),
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getInt("quantity")
                );
                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    // Lấy voucher bằng id
    public VoucherModel getVoucherById(int voucherId) {
        String query = "SELECT * FROM Voucher WHERE voucherId = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, voucherId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new VoucherModel(
                            rs.getInt("voucherId"),
                            rs.getInt("typeVoucherId"),
                            rs.getFloat("discountPercent"),
                            rs.getFloat("discountMaxValue"),
                            rs.getString("startDate"),
                            rs.getString("endDate"),
                            rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }


    // Thêm voucher
    public boolean addVoucher(VoucherModel voucher) {
        String sql = "INSERT INTO Voucher (typeVoucherId, discountPercent, discountMaxValue, startDate, endDate, quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, voucher.getTypeVoucherId());
            st.setFloat(2, voucher.getDiscountPercent());
            st.setFloat(3, voucher.getDiscountMaxValue());
            st.setString(4, voucher.getStartDate());
            st.setString(5, voucher.getEndDate());
            st.setInt(6, voucher.getQuantity());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật voucher
    public boolean updateVoucher(VoucherModel voucher) {
        String sql = "UPDATE Voucher SET typeVoucherId = ?, discountPercent = ?, discountMaxValue = ?, startDate = ?, endDate = ?, quantity = ? WHERE voucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, voucher.getTypeVoucherId());
            st.setFloat(2, voucher.getDiscountPercent());
            st.setFloat(3, voucher.getDiscountMaxValue());
            st.setString(4, voucher.getStartDate());
            st.setString(5, voucher.getEndDate());
            st.setInt(6, voucher.getQuantity());
            st.setInt(7, voucher.getVoucherId());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa voucher
    public boolean deleteVoucher(int voucherId) {
        String sql = "DELETE FROM Voucher WHERE voucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, voucherId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<VoucherModel> getAllVoucherShipping() {
        return getAllVouchersByType(1); // 1 là typeVoucherId cho 'shipping'
    }

    public List<VoucherModel> getAllVoucherItems() {
        return getAllVouchersByType(2); // 2 là typeVoucherId cho 'items'
    }

    private List<VoucherModel> getAllVouchersByType(int typeVoucherId) {
        List<VoucherModel> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE typeVoucherId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, typeVoucherId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                VoucherModel voucher = new VoucherModel();
                voucher.setVoucherId(rs.getInt("voucherId"));
                voucher.setTypeVoucherId(rs.getInt("typeVoucherId"));
                voucher.setDiscountPercent(rs.getFloat("discountPercent"));
                voucher.setDiscountMaxValue(rs.getFloat("discountMaxValue"));
                voucher.setStartDate(rs.getString("startDate"));
                voucher.setEndDate(rs.getString("endDate"));
                voucher.setQuantity(rs.getInt("quantity"));

                vouchers.add(voucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vouchers;
    }


    // Lấy tiền giảm giá khi của voucher shipping
    public int getDiscountShippingFee(int voucherId, int fee) {
        String sql = "SELECT discountPercent, discountMaxValue FROM Voucher v " +
                "JOIN TypeVoucher tv ON v.typeVoucherId = tv.typeVoucherId " +
                "WHERE v.voucherId = ? AND tv.typeName = 'shipping'";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, voucherId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int discountPercent = rs.getInt("discountPercent");
                int discountMaxValue = rs.getInt("discountMaxValue");

                // Tính toán giá trị giảm giá ship
                int discountValue = fee * discountPercent / 100;
                return Math.min(discountValue, discountMaxValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Nếu không tìm thấy voucher hoặc không phải shipping, không giảm giá
    }


    // Tính toán giá tiền ship phải trả khi sử dụng voucher shipping
    public float calculateShippingFee(int deliveryId, int voucherId) {
        float fee = 0;
        String getFeeSql = "SELECT fee FROM Deliveries WHERE deliveryId = ?";
        String getVoucherSql = "SELECT discountPercent, discountMaxValue FROM Voucher v " +
                "JOIN TypeVoucher tv ON v.typeVoucherId = tv.typeVoucherId " +
                "WHERE v.voucherId = ? AND tv.typeName = 'shipping'";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement feeStmt = con.prepareStatement(getFeeSql)) {

            // Lấy phí vận chuyển ban đầu
            feeStmt.setInt(1, deliveryId);
            ResultSet feeRs = feeStmt.executeQuery();
            if (feeRs.next()) {
                fee = feeRs.getFloat("fee");
            }

            // Kiểm tra voucher hợp lệ
            try (PreparedStatement voucherStmt = con.prepareStatement(getVoucherSql)) {
                voucherStmt.setInt(1, voucherId);
                ResultSet voucherRs = voucherStmt.executeQuery();

                if (voucherRs.next()) {
                    float discountPercent = voucherRs.getFloat("discountPercent");
                    float discountMax = voucherRs.getFloat("discountMaxValue");

                    float discountValue = (fee * discountPercent) / 100;
                    discountValue = Math.min(discountValue, discountMax);
                    fee -= discountValue;
                }
            }

            // Đảm bảo phí ship không âm
            return Math.max(fee, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu có lỗi
    }


}

