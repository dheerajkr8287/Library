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

@WebServlet("/active_issues")
public class ActiveIssuesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<Issue> issues = new ArrayList<>();
        issues.add(new Issue(1, 101, 1001, "2025-02-15"));
        issues.add(new Issue(2, 102, 1002, "2025-02-16"));

        String json = new Gson().toJson(issues);
        out.print(json);
        out.flush();
    }
}

class Issue {
    int issueId, bookId, userId;
    String issueDate;

    public Issue(int issueId, int bookId, int userId, String issueDate) {
        this.issueId = issueId;
        this.bookId = bookId;
        this.userId = userId;
        this.issueDate = issueDate;
    }
}
