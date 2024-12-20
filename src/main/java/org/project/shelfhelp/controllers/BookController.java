package org.project.shelfhelp.controllers;

import io.javalin.http.*;
import org.project.shelfhelp.models.Book;
import org.project.shelfhelp.models.Entry;
import org.project.shelfhelp.repositories.BookRepository;
import org.project.shelfhelp.repositories.EntryRepository;
import org.project.shelfhelp.repositories.GBRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookController {

    public static void addBook(Context ctx) throws Exception {
        var bookId = ctx.pathParam("bookId");
        var book = GBRepository.getABookbyId(bookId);
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
        var userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;
        var entry = EntryRepository.createEntry(userId, bookId);

        ctx.redirect("/readingList");
}

        public static void removeBook(Context ctx) throws SQLException {
                var id =ctx.pathParam("bookId");
                var deletedBook = BookRepository.deleteBook(id);

                if (deletedBook != null) {
                    System.out.println(deletedBook);
                    ctx.status(204).json(deletedBook);
                } else {
                    throw new BadRequestResponse("Cannot find book with this Id");
                }

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

    public static void  SearchRender(Context ctx) throws Exception {
//        String searchInput = ctx.queryParam("searchInput");
        int userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;
        String username = ctx.sessionAttribute("username");
        if (username == null) {
            ctx.redirect("/");
            return;
        }
        String searchInput = ctx.queryParam("searchInput") == null? "adventures": ctx.queryParam("searchInput");
//        String titleSearch = ctx.formParamAsClass("searchInput", String.class).get();
        System.out.println(searchInput+ " searchInput");
        List<Book> booksBySearch = GBRepository.getBooksBySearch(searchInput);
//        List<Book> booksByTitle = GBRepository.getBooksByTitle(searchInput);
//        System.out.println(booksByTitle);
//        List<Book> booksByAuthor = GBRepository.getBooksByTitle(author);
        ctx.render("/searchPage.html", Map.of("books", booksBySearch));
        ctx.status(200);
    }

    public static void detailsRender(Context ctx) throws Exception {
            var id = ctx.pathParam("bookId");
            var book = GBRepository.getABookbyId(id);
            ctx.render("bookDetails.html", Map.of("b",book));
    }


}// end of BookController
