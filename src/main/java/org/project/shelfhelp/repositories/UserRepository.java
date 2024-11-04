package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;

import io.javalin.http.Context;
import org.project.shelfhelp.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public static User findUser(String username) throws SQLException {
        String query = "SELECT *  \n";
        query += "FROM users \n";
        query += "WHERE username = ?;";

        try (var conn = DB.getConnection();
             var stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery();) {

                if (rs.next()) {
                    int id = rs.getInt("id");
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String password = rs.getString("password");
                    String avatar = rs.getString("avatar");

                    return new User(id, username, firstName, lastName, password, avatar);


                } else {
                    return null;

                }
            }
        }
    }
}
