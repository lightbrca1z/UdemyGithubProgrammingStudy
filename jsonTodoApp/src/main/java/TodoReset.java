import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;

@WebServlet("/TodoReset")
public class TodoReset extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String getFilePath() {
        String filePath = "C:/pleiades/2024-12/workspace/Todo111/src/main/webapp/WEB-INF/tasks.json";
        System.out.println("Using file path: " + filePath); // デバッグ用ログ
        return filePath;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        if (!resetTasksFile()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"タスクのリセットに失敗しました\"}");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\":\"タスクがリセットされました\"}");
    }

    private boolean resetTasksFile() {
        Path path = Path.of(getFilePath());
        System.out.println("Resetting tasks file: " + path.toString()); // デバッグ用ログ
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(new JSONArray().toString(4)); // 空のJSON配列を書き込む
            System.out.println("tasks.json successfully reset."); // デバッグ用ログ
            return true;
        } catch (IOException e) {
            System.err.println("Failed to reset tasks.json: " + e.getMessage()); // エラー出力
            e.printStackTrace();
            return false;
        }
    }
}
