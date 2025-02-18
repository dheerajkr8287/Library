package com.library.servlets; // Change this according to your structure

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.library.models.LibraryBook; // Import the renamed class

@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (userId == null || userId.isEmpty()) {
            response.getWriter().write("{\"error\":\"User ID is required\"}");
            return;
        }

        List<LibraryBook> borrowedBooks = new ArrayList<>();
        int fineAmount = 0;
        String userName = "";
        String membershipType = "";

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                response.getWriter().write("{\"error\":\"Database connection failed\"}");
                return;
            }

            // Fetch user details
            String userQuery = "SELECT name, membership_type FROM users WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(userQuery)) {
                stmt.setString(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    userName = rs.getString("name");
                    membershipType = rs.getString("membership_type");
                }
            }

            // Fetch borrowed books
            String bookQuery = "SELECT books.id, books.title, books.author, books.genre FROM issued_books " +
                    "JOIN books ON issued_books.book_id = books.id WHERE issued_books.user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(bookQuery)) {
                stmt.setString(1, userId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    borrowedBooks.add(new LibraryBook(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre")
                    ));
                }
            }

            // Fetch total fine amount
            String fineQuery = "SELECT COALESCE(SUM(amount), 0) AS totalFine FROM fines WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(fineQuery)) {
                stmt.setString(1, userId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    fineAmount = rs.getInt("totalFine");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\":\"Database error occurred\"}");
            return;
        }

        // Create response object
        UserProfile userProfile = new UserProfile(userId, userName, membershipType, borrowedBooks, fineAmount);
        String jsonResponse = new Gson().toJson(userProfile);
        response.getWriter().write(jsonResponse);
    }
}

// Supporting User Profile Class
class UserProfile {
    private String userId;
    private String userName;
    private String membershipType;
    private List<LibraryBook> borrowedBooks;
    private int fineAmount;

    public UserProfile(String userId, String userName, String membershipType, List<LibraryBook> borrowedBooks, int fineAmount) {
        this.userId = userId;
        this.userName = userName;
        this.membershipType = membershipType;
        this.borrowedBooks = borrowedBooks;
        this.fineAmount = fineAmount;
    }
}
