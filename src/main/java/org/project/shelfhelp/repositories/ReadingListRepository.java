package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;
import org.project.shelfhelp.models.ReadingListEntry;

import java.sql.SQLException;

public class ReadingListRepository {

    public static ReadingListEntry createEntry(int userId, int bookId) {
        return createEntry(userId, bookId, null);
    }

    public static ReadingListEntry createEntry(int userId, int bookId, String tag) {
        String insertQuery = "INSERT INTO reading_lists (user_id, book_id, is_read, tag) VALUES (?, ?, ?, ?)";

        try (var conn = DB.getConnection();
             var statement = conn.prepareStatement(insertQuery)) {

            statement.setInt(1, userId);
            statement.setInt(2, bookId);
            statement.setBoolean(3, false);
            statement.setString(4, tag);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return new ReadingListEntry(userId, bookId, tag);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        }

        return null;
    }

    public static void setTag(int userId, int bookId, String tag) {
        String setTagQuery = "UPDATE reading_lists SET tag = ? WHERE user_id = ? AND book_id = ?";

        try (var conn = DB.getConnection();
             var statement = conn.prepareStatement(setTagQuery)) {

            statement.setString(1, tag);
            statement.setInt(2, userId);
            statement.setInt(3, bookId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Tag updated successfully for user ID: " + userId + ", book ID: " + bookId);
            } else {
                System.out.println("No entry found for user ID: " + userId + ", book ID: " + bookId + ". Tag update failed.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to update tag for user ID: " + userId + ", book ID: " + bookId + ". Error: " + e.getMessage());
        }
    }

}
