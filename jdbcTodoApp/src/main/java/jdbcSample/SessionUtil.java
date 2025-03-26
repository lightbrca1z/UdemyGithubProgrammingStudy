package jdbcSample;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static Integer getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            return null;
        }
        return (Integer) session.getAttribute("userId");
    }

    public static boolean isSessionInvalid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session == null || session.getAttribute("userId") == null);
    }

    public static void setUserIdInSession(HttpServletRequest request, int userId) {
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", userId);
    }

    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // セッションが無い場合にLogin.jspへフォワードするメソッド
    public static void forwardToLoginIfSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isSessionInvalid(request)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
