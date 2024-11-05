package org.project.shelfhelp.controllers;

import io.javalin.http.Context;
import org.project.shelfhelp.models.ReadingListEntry;
import org.project.shelfhelp.repositories.ReadingListRepository;

public class ReadingListController {
    public static void createEntry(Context ctx) {
        try {
            var body = ctx.bodyAsClass(ReadingListEntry.class);
            var entry = ReadingListRepository.createEntry(body.getUserId(), body.getBookId(), body.getTag());
            ctx.status(201).json(entry);
        } catch (Exception e) {
            ctx.status(400).json("Can't create reading list entry");
        }
    }

    public static void setTag(Context ctx) {
        try {
            var body = ctx.bodyAsClass(ReadingListEntry.class);
            ReadingListRepository.setTag(body.getUserId(), body.getBookId(), body.getTag());

        } catch (Exception e) {
            ctx.status(400).json("Can't set tag");
        }
    }
}
