package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;
import org.project.shelfhelp.models.Entry;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EntryRepository {

    public static Entry createEntry(int userId, String bookId) {
        return createEntry(userId, bookId, null);
    }

    public static Entry createEntry(int userId, String bookId, String tag) {
        String insertQuery = "INSERT INTO reading_lists (user_id, book_id, is_read, tag) VALUES (?, ?, ?, ?)";

        try (var conn = DB.getConnection();
             var statement = conn.prepareStatement(insertQuery)) {

            statement.setInt(1, userId);
            statement.setString(2, bookId);
            statement.setBoolean(3, false);
            statement.setString(4, tag);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return new Entry(userId, bookId, tag);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    public static void setTag(int userId, String bookId, String tag) {
        String setTagQuery = "UPDATE reading_lists SET tag = ? WHERE user_id = ? AND book_id = ?";

        try (var conn = DB.getConnection();
             var statement = conn.prepareStatement(setTagQuery)) {

            statement.setString(1, tag);
            statement.setInt(2, userId);
            statement.setString(3, bookId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Tag updated successfully for user ID: " + userId + ", book ID: " + bookId);
            } else {
                System.out.println("No entry found for user ID: " + userId + ", book ID: " + bookId + ".");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update tag for user ID: " + userId + ", book ID: " + bookId);
        }
    }

    public static void markAsRead(int userId, String bookId) {
        String markAsReadQuery = "UPDATE reading_lists SET is_read = 1 WHERE user_id = ? AND book_id = ?";

        try (var conn = DB.getConnection();
             var statement = conn.prepareStatement(markAsReadQuery)) {

            statement.setInt(1, userId);
            statement.setString(2, bookId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Book marked as read for user ID: " + userId + ", book ID: " + bookId);
            } else {
                System.out.println("No entry found for user ID: " + userId + ", book ID: " + bookId + ". Mark as read failed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to mark book as read for user ID: " + userId + ", book ID: " + bookId + ". Error: " + e.getMessage());
        }
    }

    public static Map<String, Object> getStats(int userId) {
        Map<String, Object> stats = new HashMap<>();

        String booksQuery = "SELECT COUNT(*) AS total_books FROM reading_lists WHERE user_id = ?";
        String booksReadQuery = "SELECT COUNT(*) AS total_read FROM reading_lists WHERE user_id = ? AND is_read = 1";
        String booksByYearQuery = "SELECT books.release_year, COUNT(*) AS count FROM reading_lists rl JOIN books ON rl.book_id = books.id WHERE rl.user_id = ? AND rl.is_read = 1 GROUP BY books.release_year";
        String averageRatingQuery = "SELECT AVG(books.average_rating) AS avg_rating FROM reading_lists rl JOIN books ON rl.book_id = books.id WHERE rl.user_id = ? AND rl.is_read = 1";


        try (var conn = DB.getConnection()) {

            try (var stmt = conn.prepareStatement(booksQuery)) {
                stmt.setInt(1, userId);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.put("total_books", rs.getInt("total_books"));
                }
            }

            try (var stmt = conn.prepareStatement(booksReadQuery)) {
                stmt.setInt(1, userId);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.put("total_books_read", rs.getInt("total_read"));
                }
            }

            Map<Integer, Integer> booksByYear = new HashMap<>();
            try (var stmt = conn.prepareStatement(booksByYearQuery)) {
                stmt.setInt(1, userId);
                var rs = stmt.executeQuery();
                while (rs.next()) {
                    booksByYear.put(rs.getInt("release_year"), rs.getInt("count"));
                }
                stats.put("books_by_year", booksByYear);
            }

            try (var stmt = conn.prepareStatement(averageRatingQuery)) {
                stmt.setInt(1, userId);
                var rs = stmt.executeQuery();
                if (rs.next()) {
                    stats.put("average_rating", rs.getDouble("avg_rating"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving stats: " + e.getMessage());
        }

        return stats;
    }
}

