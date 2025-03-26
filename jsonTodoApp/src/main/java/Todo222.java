import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/Todo222")
public class Todo222 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String getFilePath() {
    	String filePath = "C:/pleiades/2024-12/workspace/Todo111/src/main/webapp/WEB-INF/tasks.json";
        System.out.println("Using file path: " + filePath); // デバッグ用ログ
        return filePath;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        JSONArray tasks = readTasksFromFile();

        try (PrintWriter out = response.getWriter()) {
            out.write(tasks.toString(4));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        String task = request.getParameter("task");
        if (task == null || task.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"タスクが空です\"}");
            return;
        }

        JSONArray tasks = readTasksFromFile();

        JSONObject newTask = new JSONObject();
        newTask.put("id", UUID.randomUUID().toString());
        newTask.put("task", task);
        tasks.put(newTask);

        if (!writeTasksToFile(tasks)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"タスクの保存に失敗しました\"}");
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        try (PrintWriter out = response.getWriter()) {
            out.write(tasks.toString(4));
        }
    }

    private JSONArray readTasksFromFile() {
        Path path = Path.of(getFilePath());
        if (!Files.exists(path)) {
            return new JSONArray();
        }
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            return new JSONArray(content);
        } catch (Exception e) {
            System.err.println("Failed to read tasks.json: " + e.getMessage()); // エラー出力
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private boolean writeTasksToFile(JSONArray tasks) {
        Path path = Path.of(getFilePath());
        System.out.println("Writing tasks to file: " + path.toString()); // デバッグ用ログ
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(tasks.toString(4));
            System.out.println("tasks.json successfully updated."); // デバッグ用ログ
            return true;
        } catch (IOException e) {
            System.err.println("Failed to write tasks.json: " + e.getMessage()); // エラー出力
            e.printStackTrace();
            return false;
        }
    }
}
