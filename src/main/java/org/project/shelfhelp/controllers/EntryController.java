package org.project.shelfhelp.controllers;

import io.javalin.http.Context;
import org.project.shelfhelp.models.Entry;
import org.project.shelfhelp.repositories.EntryRepository;

public class EntryController {

    public static void setTag(Context ctx) {
        try {
            var body = ctx.bodyAsClass(Entry.class);
            EntryRepository.setTag(body.getUserId(), body.getBookId(), body.getTag());
            ctx.status(200).json("Tag set up correctly");
        } catch (Exception e) {
            ctx.status(400).json("Can't set tag");
        }
    }

    public static void markAsRead(Context ctx) {
        try {
            var body = ctx.bodyAsClass(Entry.class);
            EntryRepository.markAsRead(body.getUserId(), body.getBookId());
            ctx.status(200).json("Book marked as read");
        } catch (Exception e) {
            ctx.status(400).json("Can't mark book as read");
        }
    }

    public static void getStats(Context ctx) {
        try {
//            int userId = ctx.sessionAttribute("id");
            var stats = EntryRepository.getStats(1);
            ctx.status(200).json(stats);
        } catch (Exception e) {
            ctx.status(400).json("Can't get book stats");
        }
    }

//    public static void createEntry(Context ctx) {
//        try {
//            var body = ctx.bodyAsClass(ReadingListEntry.class);
//            var entry = ReadingListRepository.createEntry(body.getUserId(), body.getBookId(), body.getTag());
//            ctx.status(201).json(entry);
//        } catch (Exception e) {
//            ctx.status(400).json("Can't create reading list entry");
//        }
//    }
}
