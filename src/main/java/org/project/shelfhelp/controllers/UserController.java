package org.project.shelfhelp.controllers;
import io.javalin.http.*;
import org.project.shelfhelp.models.User;
import org.project.shelfhelp.models.UserDTO;
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
            ctx.sessionAttribute("id", user.getId());
            ctx.status(200);
            ctx.redirect("/login/" + user.getId());
        } else {
            ctx.status(401);
        }

    }

    public static void addNewUser(Context ctx) throws Exception {

        // if details supplied as a json..
        UserDTO body = ctx.bodyAsClass(UserDTO.class);

        /*
            // if using a form - grab each parameter to pass into User constructor
            String username = ctx.formParamAsClass("username", String.class).get();
            String firstName = ctx.formParamAsClass("firstName", String.class).get();
            String lastName = ctx.formParamAsClass("lastName", String.class).get();
            String email = ctx.formParamAsClass("email", String.class).get();
            String avatarUrl = ctx.formParamAsClass("avatarUrl", String.class).get();

            UserDTO body = new UserDTO(firstName, lastName, username, password, avatarUrl);
         */

        System.out.println(body);
        int response = UserRepository.insertNewUser(body);
        if(response!=-1){
            ctx.json(response).status(201);
        }else{
            throw new BadRequestResponse("unable to add user.");
        }
    }

    public static void renderForm(Context ctx) {
        ctx.render("/login.html");
    }
}
