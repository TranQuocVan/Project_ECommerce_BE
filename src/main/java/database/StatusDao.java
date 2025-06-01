package database;

import model.StatusModel;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class StatusDao {
    public boolean  addStatus(StatusModel status) {
        try (Connection con = JDBCUtil.getConnection()) {
            con.setAutoCommit(true);  // Tự động commit

            String sql = "INSERT INTO statuses (name, orderId, description, timeline) VALUES (?, ?, ?, ?)";
            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, status.getName());
                st.setInt(2, status.getOrderId());
                st.setString(3, status.getDescription());
                st.setTimestamp(4, status.getTimeline());

                int rowsAffected = st.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false; // Trả về false nếu có lỗi
    }


    //check
    public List<StatusModel> getStatusesByOrderId(int orderId) {
        String sql = "SELECT * FROM statuses  WHERE orderId = ?";
        List<StatusModel> statuses = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            // Thiết lập tham số cho câu lệnh SQL
            st.setInt(1, orderId);

            try (ResultSet rs = st.executeQuery()) {
                // Duyệt qua từng bản ghi trong kết quả truy vấn
                while (rs.next()) {
                    StatusModel status = new StatusModel();
                    status.setId(rs.getInt("statusId"));
                    status.setName(rs.getString("name"));
                    status.setOrderId(rs.getInt("orderId"));
                    status.setDescription(rs.getString("description"));
                    status.setTimeline(rs.getTimestamp("timeline")); // Gán giá trị vào status


//                    // Lấy `startDate` và `endDate`
//                    status.setStartDate(rs.getTimestamp("startDate")); // Sử dụng Timestamp
//                    Timestamp endDate = rs.getTimestamp("endDate");
//                    if (endDate != null) {
//                        status.setEndDate(endDate);
//                    }

                    statuses.add(status); // Thêm đối tượng StatusModel vào danh sách
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console
        }

        return statuses; // Trả về danh sách các StatusModel
    }

    public List<StatusModel> getAllStatuses() {
        String sql = "SELECT * FROM statuses";
        List<StatusModel> statuses = new ArrayList<>();

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                StatusModel status = new StatusModel();
                status.setId(rs.getInt("statusId"));
                status.setName(rs.getString("name"));
                status.setOrderId(rs.getInt("orderId"));
                status.setDescription(rs.getString("description"));

                // timeline là DATE => lấy bằng getDate, rồi convert thành Timestamp
                Date sqlDate = rs.getDate("timeline"); // java.sql.Date
                if (sqlDate != null) {
                    status.setTimeline(new Timestamp(sqlDate.getTime()));
                }

                statuses.add(status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statuses;
    }







}
