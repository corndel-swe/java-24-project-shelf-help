package org.project.shelfhelp.controllers;
import io.javalin.http.*;
import org.project.shelfhelp.models.User;
import org.project.shelfhelp.repositories.UserRepository;

import java.sql.SQLException;

public class UserController {

    public static void getUser(Context ctx) throws SQLException {

        // get the username
        String username = ctx.formParamAsClass("username", String.class).get();
        // String password = ctx.formParamAsClass("password", String.class).get();

        // check the username exists in database
        User user = UserRepository.findUser(username);

        // redirect
        if(user != null){
            ctx.status(200);
            ctx.redirect("/index");
        } else {
            ctx.status(401);
            ctx.redirect("/login");
        }

    }
}
