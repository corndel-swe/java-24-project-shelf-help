package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;
import org.project.shelfhelp.models.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    public static Book findById(int id) throws SQLException {

        var query = "SELECT *\n";
        query += "FROM books \n";
        query += ("WHERE id = ?");

        // try with resources - get connection
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);) {

            stmt.setInt(1, id);

            try (var rs = stmt.executeQuery();){

                if(rs.next()){
                    var title = rs.getString("title");
                    var author = rs.getString("author");
                    var year = rs.getInt("release_year");
                    var genre = rs.getString("genre");
                    var bookSummary= rs.getString("summary");
                    var bookCover = rs.getString("cover_url");

                    return new Book(title, author, year, genre, bookSummary, bookCover);
                }else{
                    System.out.println("not a valid id.");
                    return null;
                }
            }
        }
    }

    public static List<Book> findByTitle(String title) throws SQLException {
        var query = "SELECT * FROM books WHERE title LIKE ?";

        // Try with resources - get connection
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);) {

            // Use '%' wildcards to find similar titles
            stmt.setString(1, "%" + title + "%");

            try (var rs = stmt.executeQuery();) {
                List<Book> books = new ArrayList<>();

                // Loop through the result set to retrieve all matching books
                while (rs.next()) {
                    var bookTitle = rs.getString("title");
                    var author = rs.getString("author");
                    var year = rs.getInt("release_year");
                    var genre = rs.getString("genre");
                    var bookSummary = rs.getString("summary");
                    var bookCover = rs.getString("cover_url");

                    books.add(new Book(bookTitle, author, year, genre, bookSummary, bookCover));
                }

                return books; // Return the list of matching books
            }
        }
    }


    // testing
    public static void main(String[] args) throws SQLException {
        //By Id
        var bookByID = BookRepository.findById(2);
        System.out.println(bookByID);
        // by bookTitle
        var bookByTitle= BookRepository.findByTitle("To Kill a Mockingbird");
        System.out.println(bookByTitle);


    }
}
