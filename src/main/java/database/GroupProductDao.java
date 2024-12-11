package database;

import model.GroupProductModel;
import model.ProductCategoryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupProductDao {
    public List<GroupProductModel> getAllGroupProduct() {
        List<GroupProductModel> list = new ArrayList<>();

        String sql = "SELECT * FROM GroupProducts";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng GroupProductModel từ kết quả truy vấn
                GroupProductModel groupProductModel = new GroupProductModel(rs.getString("name"));
                groupProductModel.setId(rs.getInt("groupProductId")); // Sửa nếu cột chính không phải "productCategoryId"
                list.add(groupProductModel); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean addGroupProduct(GroupProductModel groupProductModel) {
        String sql = "INSERT INTO GroupProducts (name, image) VALUES (?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, groupProductModel.getName());
            st.setBinaryStream(2, groupProductModel.getImage());

            // Kiểm tra nếu số dòng bị ảnh hưởng là 1
            return st.executeUpdate() == 1;

        } catch (SQLException e) {
            System.err.println("Error while adding group product: " + e.getMessage());
            return false;
        }
    }

}
