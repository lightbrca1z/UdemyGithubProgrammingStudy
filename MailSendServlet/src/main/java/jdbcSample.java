

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbcSample {
	private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mailsendservlet?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root"; // あなたのMySQLユーザー名
    private static final String JDBC_PASSWORD = "Keeper2020"; // あなたのMySQLパスワード

    public static void main(String[] args) {
        try {
            // JDBCドライバーのロード（必要に応じて）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // データベース接続
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("データベース接続成功！");

            // 接続を閉じる
            connection.close();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバーが見つかりません。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベース接続に失敗しました。");
            e.printStackTrace();
        }
    }
}
