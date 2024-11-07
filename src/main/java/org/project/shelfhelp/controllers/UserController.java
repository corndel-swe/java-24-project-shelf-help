package org.project.shelfhelp.controllers;
import io.javalin.http.*;
import org.project.shelfhelp.models.User;
import org.project.shelfhelp.models.UserDTO;
import org.project.shelfhelp.repositories.UserRepository;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class UserController {

    public static void getUser(Context ctx) throws SQLException {

        // get the username
        String username = ctx.formParamAsClass("username", String.class).get();
        String password = ctx.formParamAsClass("password", String.class).get();

        // check the username exists in database
        User user = UserRepository.findUser(username);
        // redirect
        if(user != null && user.getPassword().equals(password)){
            ctx.sessionAttribute("id", user.getId());
            ctx.sessionAttribute("username", user.getUsername());
            ctx.status(200);
            ctx.redirect("/readingList/");
        } else {
            ctx.status(401)
            .redirect("/");

        }

    }

    public static void addNewUser(Context ctx) throws Exception {
        // if using a form - grab each parameter to pass into User constructor
        String username = ctx.formParamAsClass("username", String.class).get();
        String firstName = ctx.formParamAsClass("firstName", String.class).get();
        String lastName = ctx.formParamAsClass("lastName", String.class).get();
        String password = ctx.formParamAsClass("password", String.class).get();
        String avatarUrl = ctx.formParamAsClass("avatarUrl", String.class).get();

        UserDTO body = new UserDTO(avatarUrl, password, username, lastName, firstName);

        int response = UserRepository.insertNewUser(body);
        if(response!=-1){
            System.out.println(response);
            ctx.status(201);
            ctx.redirect("/");
        }else{
            ctx.status(400);
            ctx.redirect("/register");
            //throw new BadRequestResponse("unable to add user.");
        }
    }

    public static void logOut(Context ctx) throws Exception {
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }

    public static void renderLoginForm(Context ctx) {
        ctx.render("/login.html");
    }

    public static void renderRegisterForm(Context ctx) {
        ctx.render("/register.html");
    }

}
