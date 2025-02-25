import java.sql.*;

public class DataBaseHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/login_db?serverTimezone=UTC";
    private static final String DB_USER = "root";  // Change this if needed
    private static final String DB_PASSWORD = "password";  // Change this if needed

    // Method to establish and return a database connection
    public static Connection getDBConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Validate login credentials
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM users WHERE UserName = ? AND Password = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();

            return result.next();  // If a record exists, return true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Change user password
    public static boolean changePassword(String username, String newPassword) {
        String query = "UPDATE users SET Password = ? WHERE UserName = ?";
        try (Connection conn = getDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0; // Returns true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
