import java.sql.*;

public class DataBaseHandler {

    private static DataBaseHandler handler = null;
    private static Statement stmt = null;
    private static PreparedStatement pstatement = null;

    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
    }

    public static Connection getDBConnection()
    {
        Connection connection = null;
        String dburl = "jdbc:mysql://localhost:3306/login_db?serverTimezone=UTC";


        String userName = "root";
        String password = "password";

        try
        {
            connection = DriverManager.getConnection(dburl, userName, password);

        } catch (Exception e){
            e.printStackTrace();
        }

        return connection;
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = getDBConnection().createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }
    public static boolean validateLogin(String username, String password){

        getInstance();
        String query = "SELECT * FROM users WHERE UserName = '" + username + "' AND Password = '" + password + "'";
        
        System.out.println(query);

        ResultSet result = handler.execQuery(query);
        try {
            if (result.next()) {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean changePassword(String username, String password) {

        try {
            String query = "UPDATE admin SET Password = '" + password + "' WHERE Username = '" + username + "'";

            System.out.println(query);

            pstatement = getDBConnection().prepareStatement(query);
            int res = pstatement.executeUpdate();

            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}