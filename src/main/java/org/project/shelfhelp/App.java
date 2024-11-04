package org.project.shelfhelp;

import io.javalin.Javalin;
import org.project.shelfhelp.controllers.BookController;
import org.project.shelfhelp.controllers.UserController;

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
                path("/products", () -> {
                    get("/addBook", BookController::addBook);
                    get("/removeBook", BookController::removeBook);
                    post("/login", UserController::getUser);
                    post("/register", UserController::addNewUser);
                });
            });
        });

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.status(500);
            ctx.result("An unknown error occurred.");
        });

    }

    public Javalin javalinApp() {
        return app;
    }
}
