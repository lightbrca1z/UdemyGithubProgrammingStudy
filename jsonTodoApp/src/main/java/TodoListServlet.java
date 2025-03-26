import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/TodoList")
public class TodoListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String getFilePath() {
        return "C:/pleiades/2024-12/workspace/Todo111/src/main/webapp/WEB-INF/tasks.json";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        JSONArray tasks = readTasksFromFile();

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>");
        html.append("<html><head><meta charset='UTF-8'><title>Todo List</title></head><body>");
        html.append("<h1>Todo List</h1>");
        html.append("<ul>");

        for (int i = 0; i < tasks.length(); i++) {
            JSONObject task = tasks.getJSONObject(i);
            html.append("<li>").append(task.getString("task")).append("</li>");
        }

        html.append("</ul>");
        html.append("</body></html>");

        response.getWriter().write(html.toString());
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
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
