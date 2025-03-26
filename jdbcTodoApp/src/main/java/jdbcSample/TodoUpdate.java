package jdbcSample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TodoUpdate")
public class TodoUpdate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        SessionUtil.forwardToLoginIfSessionInvalid(request, response);

        int userId = (int) session.getAttribute("userId");
        List<Task> tasks = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, task FROM tasks WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("task")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "タスクの取得に失敗しました");
        }

        request.setAttribute("tasks", tasks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("TodoMain.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");
        String task = request.getParameter("task");

        if (task == null || task.trim().isEmpty()) {
            request.setAttribute("error", "タスクが空です");
            doGet(request, response);
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO tasks (user_id, task) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, task);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) throw new SQLException("タスクの保存に失敗しました、行が追加されませんでした。");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setAttribute("generatedId", generatedKeys.getInt(1));
                } else {
                    throw new SQLException("タスクの保存に失敗しました、IDが生成されませんでした。");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "タスクの保存に失敗しました");
        }

        doGet(request, response);
    }
}
