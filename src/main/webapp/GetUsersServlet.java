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

@WebServlet("/get_users")
public class GetUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<User> users = new ArrayList<>();
        users.add(new User(1, "john_doe"));
        users.add(new User(2, "alice_smith"));

        String json = new Gson().toJson(users);
        out.print(json);
        out.flush();
    }
}

class User {
    int id;
    String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
}

