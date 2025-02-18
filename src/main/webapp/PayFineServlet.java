import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/pay_fine")
public class PayFineServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int fineAmount = Integer.parseInt(request.getParameter("fine_amount"));

        // Database logic to process fine payment (mocked)
        System.out.println("Fine Paid: User ID " + userId + ", Amount " + fineAmount);

        response.sendRedirect("user_home.html");
    }
}
