package database;

import model.VoucherModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {

    // Lấy tất cả voucher
    public List<VoucherModel> getAllVouchers() {
        List<VoucherModel> vouchers = new ArrayList<>();
        String sql = "SELECT * FROM voucher";
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

    // Lấy danh sách voucher bằng loại voucher
    public List<VoucherModel> getVouchersByTypeVoucher(int typeVoucherId) {
        List<VoucherModel> list = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE typeVoucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, typeVoucherId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                VoucherModel v = new VoucherModel();
                v.setVoucherId(rs.getInt("voucherId"));
                v.setTypeVoucherId(rs.getInt("typeVoucherId"));
                v.setDiscountPercent(rs.getFloat("discountPercent"));
                v.setDiscountMaxValue(rs.getFloat("discountMaxValue"));
                v.setStartDate(rs.getString("startDate"));
                v.setEndDate(rs.getString("endDate"));
                v.setQuantity(rs.getInt("quantity"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy voucher bằng id
    public VoucherModel getVoucherById(int voucherId) {
        String query = "SELECT * FROM voucher WHERE voucherId = ?";
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
    public void addVoucher(VoucherModel voucher) {
        String sql = "INSERT INTO voucher (typeVoucherId, discountPercent, discountMaxValue, startDate, endDate, quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, voucher.getTypeVoucherId());
            st.setFloat(2, voucher.getDiscountPercent());
            st.setFloat(3, voucher.getDiscountMaxValue());
            st.setString(4, voucher.getStartDate());
            st.setString(5, voucher.getEndDate());
            st.setInt(6, voucher.getQuantity());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật voucher
    public VoucherModel updateVoucher(VoucherModel voucher) {
        String sql = "UPDATE voucher SET typeVoucherId = ?, discountPercent = ?, discountMaxValue = ?, startDate = ?, endDate = ?, quantity = ? WHERE voucherId = ?";
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
            if (rowsAffected > 0) {
                return voucher; // Trả về chính voucher vừa được cập nhật
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu update thất bại
    }

    // Xóa voucher
    public void deleteVoucher(int voucherId) {
        String sql = "DELETE FROM voucher WHERE voucherId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, voucherId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    // Tính toán tiền giảm giá khi sử dụng voucher shipping
    public float calculateDiscountShippingFee(int voucherId, int deliveryId) {
        String sql = "SELECT v.discountPercent, v.discountMaxValue, d.fee " +
                "FROM voucher v " +
                "JOIN typevoucher tv ON v.typeVoucherId = tv.typeVoucherId " +
                "JOIN deliveries d ON d.deliveryId = ? " +
                "WHERE v.voucherId = ? AND tv.typeName = 'shipping'";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // Set các tham số cho truy vấn
            st.setInt(1, deliveryId);  // Gắn giá trị deliveryId vào câu truy vấn
            st.setInt(2, voucherId);   // Gắn giá trị voucherId vào câu truy vấn

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int discountPercent = rs.getInt("discountPercent");
                int discountMaxValue = rs.getInt("discountMaxValue");
                int fee = rs.getInt("fee");  // Lấy giá trị fee từ bảng Deliveries

                // Tính toán giá trị giảm giá ship
                int discountValue = fee * discountPercent / 100;
                return Math.min(discountValue, discountMaxValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Nếu không tìm thấy voucher hoặc không phải shipping, không giảm giá
    }

    // Tính toán giảm giá khi sử dụng voucher items
    public float calculateDiscountItemsFee(int voucherId, List<Integer> listSizeId){
        float totalPrice = 0;

        // Tính tổng giá trị đơn hàng từ danh sách sản phẩm
        for (Integer sizeId : listSizeId) {
            String sql = """
            SELECT p.price, p.discount, spc.quantity
            FROM sizes s 
            LEFT JOIN shoppingCartItems spc ON spc.sizeId = s.sizeId 
            LEFT JOIN colors c ON c.colorId = s.colorId 
            LEFT JOIN products p ON c.productId = p.productId 
            WHERE spc.sizeId = ?
        """;

            try (Connection con = JDBCUtil.getConnection();
                 PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, sizeId);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    float price = rs.getFloat("price");
                    int discount = rs.getInt("discount");
                    int quantity = rs.getInt("quantity");

                    // Tính giá sau khi giảm giá của sản phẩm
                    float discountedPrice = price - (price * discount / 100);

                    // Tính tổng tiền các sản phẩm
                    totalPrice += discountedPrice * quantity;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return 0; // Trả về 0 nếu có lỗi SQL
            }
        }

        // Truy vấn thông tin voucher
        String voucherSql = "SELECT typeVoucherId, discountPercent, discountMaxValue FROM voucher WHERE voucherId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(voucherSql)) {
            st.setInt(1, voucherId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int typeVoucherId = rs.getInt("typeVoucherId");
                float discountPercent = rs.getFloat("discountPercent");
                float discountMaxValue = rs.getFloat("discountMaxValue");

                // Chỉ áp dụng giảm giá nếu typeVoucherId == 2
                if (typeVoucherId == 2) {
                    float discountAmount = totalPrice * (discountPercent / 100);
                    return Math.min(discountAmount, discountMaxValue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Nếu không hợp lệ, không có giảm giá
    }

}

