package jdbcSample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TodoReset")
public class TodoReset extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        HttpSession session = request.getSession(false);
        SessionUtil.forwardToLoginIfSessionInvalid(request, response);

    	String sql = "DELETE FROM tasks";
    	try (Connection conn = DatabaseUtil.getConnection();
    	     PreparedStatement stmt = conn.prepareStatement(sql)) {
    	    stmt.executeUpdate();
    	    stmt.executeUpdate("ALTER TABLE tasks AUTO_INCREMENT = 1");
    	    request.setAttribute("message", "タスクリストをリセットしました。");
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	    request.setAttribute("error", "タスクのリセットに失敗しました: " + e.getMessage());
    	}
        // 修正: 正しいURLパスを指定
        RequestDispatcher dispatcher = request.getRequestDispatcher("/TodoMain.jsp");
        dispatcher.forward(request, response);
    }
}