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

@WebServlet("/books")
public class BooksServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<Book> books = new ArrayList<>();
        books.add(new  Book(1, "Java Programming", "James Gosling", "Available"));
        books.add(new Book(2, "Effective Java", "Joshua Bloch", "Issued"));
        books.add(new Book(3, "Spring in Action", "Craig Walls", "Available"));

        String json = new Gson().toJson(books);
        out.print(json);
        out.flush();
    }
}

class Book {
    int id;
    String title, author, status;

    public Book(int id, String title, String author, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public Book(String title, String dueDate) {
    }
}
