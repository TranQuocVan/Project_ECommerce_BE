package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserPublicKeyDao {

    public boolean insertUserPublicKeyByGmail(String gmail, String publicKey, String keyType) throws SQLException {
        String getUserIdSql = "SELECT userId FROM users WHERE gmail = ?";
        String insertKeySql = "INSERT INTO UserPublicKeys (userId, public_key, key_type) VALUES (?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement getUserSt = con.prepareStatement(getUserIdSql)) {

            getUserSt.setString(1, gmail);

            try (ResultSet rs = getUserSt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("userId");

                    try (PreparedStatement insertKeySt = con.prepareStatement(insertKeySql)) {
                        insertKeySt.setInt(1, userId);
                        insertKeySt.setString(2, publicKey);
                        insertKeySt.setString(3, keyType);
                        int rowsInserted = insertKeySt.executeUpdate();
                        return rowsInserted > 0;
                    }

                } else {
                    throw new SQLException("User with gmail " + gmail + " not found.");
                }
            }

        }
    }


    public String getLatestUserPublicKeyByGmail(String gmail) throws SQLException {
        String getUserIdSql = "SELECT userId FROM users WHERE gmail = ?";
        String getKeySql = """
        SELECT public_key
        FROM UserPublicKeys
        WHERE userId = ?
        ORDER BY created_at DESC
        LIMIT 1
        """;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement getUserSt = con.prepareStatement(getUserIdSql)) {

            getUserSt.setString(1, gmail);
            ResultSet rs = getUserSt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");

                try (PreparedStatement getKeySt = con.prepareStatement(getKeySql)) {
                    getKeySt.setInt(1, userId);
                    ResultSet keyRs = getKeySt.executeQuery();

                    if (keyRs.next()) {
                        return keyRs.getString("public_key");
                    } else {
                        return null; // Không có key
                    }
                }
            } else {
                throw new SQLException("User with gmail " + gmail + " not found.");
            }
        }
    }

    public boolean hasPublicKeyByGmail(String gmail) throws SQLException {
        String getUserIdSql = "SELECT userId FROM users WHERE gmail = ?";
        String checkKeySql = "SELECT 1 FROM UserPublicKeys WHERE userId = ? LIMIT 1";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement getUserSt = con.prepareStatement(getUserIdSql)) {

            getUserSt.setString(1, gmail);
            ResultSet rs = getUserSt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");

                try (PreparedStatement checkKeySt = con.prepareStatement(checkKeySql)) {
                    checkKeySt.setInt(1, userId);
                    ResultSet keyRs = checkKeySt.executeQuery();
                    return keyRs.next(); // Có ít nhất 1 bản ghi
                }
            } else {
                throw new SQLException("User with gmail " + gmail + " not found.");
            }
        }
    }



}
