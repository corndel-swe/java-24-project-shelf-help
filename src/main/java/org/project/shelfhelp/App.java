package org.project.shelfhelp;

import io.javalin.Javalin;
import org.project.shelfhelp.controllers.BookController;
import static io.javalin.apibuilder.ApiBuilder.*;


public class App {
    private Javalin app;

    public static void main(String[] args) {
        var app = new App().javalinApp();
        app.start(8080);
    }

    public App() {

        app = Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/book", () -> {
                    // http://localhost:8080/book/addBook/buc0AAAAMAAJ
                    post("/addBook/{bookId}", BookController::addBook);
                    get("/removeBook", BookController::removeBook);
                    // GET http://localhost:8080/book/id/2
                    get("/id/{bookId}", BookController::getBookById);
                    // http://localhost:8080/book?title=The Great Gatsby
                    get("/", BookController::getBookByTitle);
                });
            });
        });



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
