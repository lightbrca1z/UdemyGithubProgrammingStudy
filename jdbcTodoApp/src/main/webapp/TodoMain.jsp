<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, jdbcSample.Task" %>
<%
    // 既にHttpSessionが暗黙オブジェクトとして利用できるため、ここで再宣言しないように修正
    if (session == null || session.getAttribute("userId") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TodoApp</title>
</head>
<body>
    <h1>TodoApp</h1>

    <%-- エラーメッセージ表示 --%>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red;"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="TodoUpdate" method="post">
        <label for="task">タスク入力:</label><br>
        <textarea id="task" name="task" rows="4" cols="50"></textarea><br>
        <input type="submit" value="送信">
    </form>

    <%
        Integer generatedId = (Integer) request.getAttribute("generatedId");
        if (generatedId != null && generatedId != -1) {
    %>
        <p>新しく追加されたタスクの ID: <%= generatedId %></p>
    <%
        }
    %>

	<h2>タスクリスト</h2>
	<ul>
	    <%
	        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
	        if (tasks != null && !tasks.isEmpty()) {
	            for (Task task : tasks) {
	    %>
	        <li>User <%= task.getId() %>: <%= task.getTask() %></li>
	    <%
	            }
	        } else {
	    %>
	        <li>タスクがありません</li>
	    <%
	        }
	    %>
	</ul>

    <form action="TodoReset" method="post">
        <button type="submit">タスクリストをリセット</button>
    </form>

    <!-- ログアウトボタン追加 -->
    <form action="Logout" method="get">
        <button type="submit">ログアウト</button>
    </form>
</body>
</html>
