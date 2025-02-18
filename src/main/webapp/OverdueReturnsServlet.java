import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/overdue_returns")
public class OverdueReturnsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<OverdueBook> overdueBooks = new ArrayList<>();
        overdueBooks.add(new OverdueBook(1, 101, 1001, "2025-02-10"));
        overdueBooks.add(new OverdueBook(2, 102, 1002, "2025-02-12"));

        String json = new Gson().toJson(overdueBooks);
        out.print(json);
        out.flush();
    }
}

class OverdueBook {
    int issueId, bookId, userId;
    String dueDate;

    public OverdueBook(int issueId, int bookId, int userId, String dueDate) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.userId = userId;
        this.dueDate = dueDate;
    }
}
