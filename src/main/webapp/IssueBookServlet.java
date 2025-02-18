import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/issue_book")
public class IssueBookServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("book_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));

        // Database logic to issue the book (mocked)
        System.out.println("Book Issued: Book ID " + bookId + " to User ID " + userId);

        response.sendRedirect("user_home.html");
    }
}
