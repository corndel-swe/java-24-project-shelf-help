package org.project.shelfhelp;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.project.shelfhelp.controllers.BookController;
import org.project.shelfhelp.controllers.EntryController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//import io.javalin.rendering.template.JavalinThymeleaf;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;


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
                    // http://localhost:8080/book/addBook/PLlOCUIAh88C
                .post("/book/addBook/{bookId}", BookController::addBook)
                // http://localhost:8080/book/removeBook/PLlOCUIAh88C
                .delete("/book/removeBook/{bookId}", BookController::removeBook)
                    // GET http://localhost:8080/book/id/2
                .get("/book/id/{bookId}", BookController::getBookById)
                    // http://localhost:8080/book?title=The Great Gatsby
                .get("/", BookController::getBookByTitle)
                .get("/index", ctx -> {
                        ctx.render("index.html");

                    })
            .put("/setTag", EntryController::setTag)
            .put("/markAsRead", EntryController::markAsRead)
            .get("/getStats", EntryController::getStats);




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
