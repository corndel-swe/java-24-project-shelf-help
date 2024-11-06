package org.project.shelfhelp;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.project.shelfhelp.controllers.BookController;
import org.project.shelfhelp.repositories.EntryRepository;
import org.project.shelfhelp.repositories.GBRepository;
import org.project.shelfhelp.controllers.EntryController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import org.project.shelfhelp.controllers.UserController;


import java.util.Map;


public class App {
    private Javalin app;

    public static void main(String[] args) {
        var app = new App().javalinApp();
        app.start(8080);
    }

    public App() {

        app = Javalin.create(
                config -> {
                    config.staticFiles.add("/public", Location.CLASSPATH);
                    var resolver = new ClassLoaderTemplateResolver();
                    resolver.setPrefix("/templates/");
                    resolver.setSuffix(".html");
                    resolver.setTemplateMode("HTML");

                    var engine = new TemplateEngine();
                    engine.setTemplateResolver(resolver);

                    config.fileRenderer(new JavalinThymeleaf(engine));
                });

        app
                .post("/book/addBook/{bookId}", BookController::addBook) // http://localhost:8080/book/addBook/PLlOCUIAh88C
                .get("/books/search", BookController::SearchRender) // http://localhost:8080/books/search?title=The Great Gatsby
                .delete("/book/removeBook/{bookId}", BookController::removeBook) // http://localhost:8080/book/removeBook/PLlOCUIAh88C
                .get("/book/id/{bookId}", BookController::getBookById) // GET http://localhost:8080/book/id/
                .get("/book", BookController::getBookByTitle) // http://localhost:8080/book?title=The Great Gatsby
                .get("/details/{bookId}", BookController::detailsRender)
                .get("/readingList", EntryController::readingListRender)
                .post("/setTag", EntryController::setTag)
                .post("/markAsRead", EntryController::markAsRead)
                .get("/getStats", EntryController::getStats)
                .get("/", UserController::renderLoginForm)
                .get("/register", UserController::renderRegisterForm)
                .post("/login", UserController::getUser)
                .post("/register", UserController::addNewUser)
                .get("/logout", UserController::logOut);






        app.exception(Exception.class, (e, ctx) -> {
            ctx.status(500);
            ctx.result("An unknown error occurred.");
            e.printStackTrace();
        });
    }

    public Javalin javalinApp() {
        return app;
    }
}
