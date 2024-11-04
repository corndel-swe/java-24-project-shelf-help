package org.project.shelfhelp.controllers;

import io.javalin.http.*;
import org.project.shelfhelp.repositories.BookRepository;

import java.sql.SQLException;

public class BookController {
    public static int addBook(Context ctx) {
        return 1;
    }
    public static int removeBook(Context ctx) {
        return 1;
    }




    // get book by id
    public static void  getBookById(Context ctx) throws SQLException {
            var id = Integer.parseInt(ctx.pathParam("bookId"));
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

        if (bookByTitle != null){
            ctx.status(200).json(bookByTitle);
        } else {
            throw new BadRequestResponse("Cant find book with this title");
        }
    }
}
