package org.project.shelfhelp.controllers;

import io.javalin.http.Context;
import org.project.shelfhelp.repositories.BookRepository;
import org.project.shelfhelp.repositories.EntryRepository;
import java.util.Map;

public class EntryController {

    public static void setTag(Context ctx) {
        try {
            int userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;
            String tag = ctx.formParam("tag");
            String bookId = ctx.formParam("bookId");

            if (userId == 0 || bookId == null || tag == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            EntryRepository.setTag(userId, bookId, tag);
            ctx.redirect("/readingList");
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("Error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("Error", e.getMessage()));
            e.printStackTrace();
        }
    }


    public static void markAsRead(Context ctx) {
        try {
            int userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;
            String bookId = ctx.formParam("bookId");

            if (userId == 0 || bookId == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            EntryRepository.markAsRead(userId, bookId);
            ctx.redirect("/readingList");
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("Error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("Error", e.getMessage()));
            e.printStackTrace();
        }
    }


    public static void deleteEntry(Context ctx) {
        try {
            int userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;
            String bookId = ctx.formParam("bookId");

            if (userId == 0 || bookId == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            EntryRepository.deleteEntry(userId, bookId);

            var entries = EntryRepository.findAll();
            var isBookInOtherUsers = entries.stream().anyMatch(entry -> entry.getBookId().equals(bookId) && entry.getUserId() != userId);
            if (!isBookInOtherUsers) {
                BookRepository.deleteBook(bookId);
            }

            ctx.redirect("/readingList");
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("Error", e.getMessage()));
        } catch (Exception e) {
            ctx.status(500).json(Map.of("Error", e.getMessage()));
            e.printStackTrace();
        }
    }


    public static void readingListRender(Context ctx) {
        try {
            int userId = ctx.sessionAttribute("id") != null ? (int) ctx.sessionAttribute("id") : 0;

            String username = ctx.sessionAttribute("username");
            if (username == null) {
                ctx.redirect("/");
                return;
            }


            var entries = EntryRepository.findByUser(userId);

            ctx.render("readingList.html", Map.of("entries", entries));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("Error", e.getMessage()));
        } catch (Exception e) {
            ctx.redirect("/");
            ctx.status(500).json(Map.of("Error", e.getMessage()));
            e.printStackTrace();
        }
    }


}
