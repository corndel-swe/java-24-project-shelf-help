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
            ctx.sessionAttribute("user", user);
            ctx.status(200);
            ctx.redirect("/index/" + user.getId());
        } else {
            ctx.status(401);
            ctx.redirect("/login");
        }

    }

    public static void addNewUser(Context ctx) throws Exception {

        // if details supplied as a json..
        UserDTO body = ctx.bodyAsClass(UserDTO.class);

        /*
            // if using a form - grab each parameter to pass into User constructor
            String username = ctx.formParamAsClass("username", String.class).get();
         */

        System.out.println(body);
        int response = UserRepository.insertNewUser(body);
        if(response!=-1){
            ctx.json(response).status(201);
        }else{
            throw new BadRequestResponse("unable to add user.");
        }
    }
}
