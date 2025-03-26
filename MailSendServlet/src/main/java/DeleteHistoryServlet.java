import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteHistory")
public class DeleteHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // データベース接続情報
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/mailsendservlet?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Keeper2020";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
            // データベース接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            // DELETE クエリ実行
            String sql = "DELETE FROM mailsend";
            stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            // 削除後に問い合わせフォームのページへリダイレクト
            response.sendRedirect("./jsp/Form.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("エラーが発生しました。");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
