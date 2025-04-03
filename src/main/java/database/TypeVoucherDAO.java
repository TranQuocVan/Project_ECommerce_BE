package database;

import model.TypeVoucherModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeVoucherDAO {
    public boolean addTypeVoucher(TypeVoucherModel typeVoucher) {
        String sql = "INSERT INTO TypeVoucher (typeName) VALUES (?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, typeVoucher.getTypeName());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateTypeVoucher(TypeVoucherModel typeVoucher) {
        String sql = "UPDATE TypeVoucher SET typeName = ? WHERE typeVoucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, typeVoucher.getTypeName());
            st.setInt(2, typeVoucher.getTypeVoucherId());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTypeVoucher(int typeVoucherId) {
        String sql = "DELETE FROM TypeVoucher WHERE typeVoucherId = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, typeVoucherId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TypeVoucherModel> getAllTypeVouchers() {
        List<TypeVoucherModel> typeVouchers = new ArrayList<>();
        String sql = "SELECT * FROM TypeVoucher";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                TypeVoucherModel typeVoucher = new TypeVoucherModel(
                        rs.getInt("typeVoucherId"),
                        rs.getString("typeName")
                );
                typeVouchers.add(typeVoucher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeVouchers;
    }
}
