package database;

import model.TypeVoucherModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeVoucherDAO {
    public void addTypeVoucher(TypeVoucherModel typeVoucher) {
        String sql = "INSERT INTO TypeVoucher (typeName, description) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, typeVoucher.getTypeName());
            st.setString(2, typeVoucher.getDescription());

            st.executeUpdate();
            System.out.println("Thêm loại voucher thành công!");
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm loại voucher:");
            e.printStackTrace();
        }
    }

    public TypeVoucherModel updateTypeVoucher(TypeVoucherModel typeVoucher) {
        String sql = "UPDATE TypeVoucher SET typeName = ?, description = ? WHERE typeVoucherId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, typeVoucher.getTypeName());
            st.setString(2, typeVoucher.getDescription());
            st.setInt(3, typeVoucher.getTypeVoucherId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                return typeVoucher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteTypeVoucher(int typeVoucherId) {
        String sql = "DELETE FROM TypeVoucher WHERE typeVoucherId = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, typeVoucherId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TypeVoucherModel> getAllTypeVouchers() {
        List<TypeVoucherModel> typeVouchers = new ArrayList<>();
        String sql = "SELECT * FROM typevoucher";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                TypeVoucherModel typeVoucher = new TypeVoucherModel(
                        rs.getInt("typeVoucherId"),
                        rs.getString("typeName"),
                        rs.getString("description")
                );
                typeVouchers.add(typeVoucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeVouchers;
    }

    // Lấy loại voucher bằng Id
    public TypeVoucherModel getTypeVoucherById(int typeVoucherId) {
        TypeVoucherModel typeVoucher = null;
        String sql = "SELECT * FROM TypeVoucher WHERE typeVoucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, typeVoucherId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    typeVoucher = new TypeVoucherModel(
                            rs.getInt("typeVoucherId"),
                            rs.getString("typeName"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeVoucher;
    }
}
