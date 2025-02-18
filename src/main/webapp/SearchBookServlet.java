import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SearchBookServlet")
public class SearchBookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<book> books = new ArrayList<>();

        DatabaseMetaData DatabaseConnection = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(new book(rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getString("isbn")));
            }

            String json = new Gson().toJson(books);
            response.sendRedirect("search_books.html?books=" + URLEncoder.encode(json, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("search_books.html?error=1");
        }
    }
}
