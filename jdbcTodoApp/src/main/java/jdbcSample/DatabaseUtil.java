package jdbcSample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/mysqlpractice";
    private static final String USER = "root";
    private static final String PASSWORD = "password2020";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBCドライバのロード
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
