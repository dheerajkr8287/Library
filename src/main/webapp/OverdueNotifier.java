import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/OverdueNotifier")
public class OverdueNotifier extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseMetaData DatabaseConnection = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String sql = "SELECT users.email, books.title, issued_books.due_date FROM issued_books " +
                    "JOIN users ON issued_books.user_id = users.id " +
                    "JOIN books ON issued_books.book_id = books.id " +
                    "WHERE issued_books.due_date < CURDATE()";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String email = rs.getString("email");
                String bookTitle = rs.getString("title");
                String dueDate = rs.getString("due_date");

                sendEmail(email, "Overdue Book Notice", "Your book '" + bookTitle + "' was due on " + dueDate + ". Please return it ASAP.");
            }

            response.getWriter().write("Notifications sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmail(String to, String subject, String message) {
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
    }
}
