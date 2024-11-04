package org.project.shelfhelp.controllers;

import io.javalin.http.*;
import org.project.shelfhelp.models.Book;
import org.project.shelfhelp.repositories.BookRepository;
import org.project.shelfhelp.repositories.GBRepository;

import java.sql.SQLException;

public class BookController {
    public static void addBook(Context ctx) throws Exception {
        var id = ctx.pathParam("bookId");
        Book book = GBRepository.getABookbyId(id);
        System.out.println(book);
        Book addedBook = BookRepository.addBook(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.getAverageRating(),
                book.getBookSummary(),
                book.getBookCover()
        );
        ctx.status(201).json(addedBook);

}
        public static void removeBook(Context ctx) {

    }




    // get book by id
    public static void  getBookById(Context ctx) throws SQLException {
            var id = ctx.pathParam("bookId");
            var bookById = BookRepository.findById(id);
            if (bookById != null){
                ctx.status(200).json(bookById);
            } else {
                throw new BadRequestResponse("Cant find book with this Id");
            }
            }

    public static void  getBookByTitle(Context ctx) throws SQLException {
        String title = ctx.queryParam("title");
        var bookByTitle = BookRepository.findByTitle(title);
        if (title == null || title.isEmpty()) {
            throw new BadRequestResponse("Title parameter is required");
        }
        ctx.status(200).json(bookByTitle);
    }
}
