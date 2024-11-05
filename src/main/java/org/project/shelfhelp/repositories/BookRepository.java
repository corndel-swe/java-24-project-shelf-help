package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;
import org.project.shelfhelp.models.Book;
import org.project.shelfhelp.models.Review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public static Book findById(String id) throws SQLException {

        var query = """
        SELECT books.*, reviews.id AS review_id, reviews.user_id, reviews.content,
               reviews.rating, reviews.created_at
        FROM books
        LEFT JOIN reviews ON books.id = reviews.book_id
        WHERE books.id = ?
    """;

        Book book = null;
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query)) {

            stmt.setString(1, id);

            try (var rs = stmt.executeQuery()) {
                List<Review> reviews = new ArrayList<>();

                while (rs.next()) {
                        var bookId = rs.getString("id");
                        var title = rs.getString("title");
                        var author = rs.getString("author");
                        var year = rs.getString("release_year");
                        var averageRating = rs.getFloat("average_rating");
                        var bookSummary = rs.getString("summary");
                        var bookCover = rs.getString("cover_url");
                   book =  new Book(bookId, title, author, year, averageRating, bookSummary, bookCover, reviews);



                    var reviewId = rs.getInt("review_id");
                        var userId = rs.getInt("user_id");
                        var content = rs.getString("content");
                        var rating = rs.getInt("rating");
                        var createdAt = rs.getTimestamp("created_at");
                        reviews.add(new Review(reviewId, userId, content, rating, createdAt));
                    }


            }
        }
        return book;
    }

    public static List<Book> findByTitle(String title) throws SQLException {
        var query = "SELECT * FROM books WHERE title LIKE ?";

        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);) {

            stmt.setString(1, "%" + title + "%");

            try (var rs = stmt.executeQuery();) {
                List<Book> books = new ArrayList<>();

                while (rs.next()) {
                    var bookId = rs.getString("id");
                    var bookTitle = rs.getString("title");
                    var author = rs.getString("author");
                    var year = rs.getString("release_year");
                    var averageRating = rs.getFloat("average_rating");
                    var bookSummary = rs.getString("summary");
                    var bookCover = rs.getString("cover_url");

                    books.add(new Book(bookId,bookTitle, author, year, averageRating, bookSummary, bookCover));
                }

                return books;
            }
        }
    }
    public static Book deleteBook(String id) throws SQLException {
        var query = "DELETE FROM books WHERE id = ?";
        try (var connection = DB.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setString(1, id);

            System.out.println(statement);

            int deletedRow = statement.executeUpdate();
            System.out.println(deletedRow);
            if (deletedRow > 0){
                System.out.println("book Deleted by ID " + id);
                return new Book(id);
            } else {
                System.out.println("no book found by ID " + id );
                return null;
            }
        }
    }

    public static Book addBook(String id, String bookTitle, String bookAuthor, String release_year, float average_rate , String summary, String cover_url) throws SQLException {
        var query = "INSERT INTO books (id, title, author, release_year, average_rating, summary,cover_url) VALUES (?, ?, ?, ?,?, ?,?) RETURNING *";

        try (var con = DB.getConnection();
             var statement = con.prepareStatement(query);) {


            statement.setString(1, id);
            statement.setString(2, bookTitle);
            statement.setString(3, bookAuthor);
            statement.setString(4, release_year);
            statement.setFloat(5,average_rate);
            statement.setString(6,summary);
            statement.setString(7,cover_url);
            try (var rs = statement.executeQuery();){

                if(rs.next()){
                    var bookId = rs.getString("id");
                    var title = rs.getString("title");
                    var author = rs.getString("author");
                    var year = rs.getString("release_year");
                    var averageRating = rs.getFloat("average_rating");
                    var bookSummary= rs.getString("summary");
                    var bookCover = rs.getString("cover_url");

                    return new Book(bookId,title, author, year, averageRating, bookSummary, bookCover);
                }else{
                    System.out.println("Cannot insert books.");
                    return null;
                }
            }
        }
    }

    // testing
    public static void main(String[] args) throws Exception {
        //By Id
        var bookByID = BookRepository.findById("2");
        System.out.println(bookByID);
        // by bookTitle
        var bookByTitle= BookRepository.findByTitle("To Kill a Mockingbird");
        System.out.println(bookByTitle);
        // delete book
        var bookDelete = BookRepository.deleteBook("k7hIAw5WmmwC");
        System.out.println(bookDelete);
        // create// add book
   Book addedbook = GBRepository.getABookbyId("k7hIAw5WmmwC");
        var addedBook = BookRepository.addBook(
                addedbook.getId(),
                addedbook.getTitle(),
                addedbook.getAuthor(),
                addedbook.getYear(),
                addedbook.getAverageRating(),
                addedbook.getBookSummary(),
                addedbook.getBookCover()
        );
        System.out.println(addedBook);
    }
}
