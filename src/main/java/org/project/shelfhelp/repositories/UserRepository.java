package org.project.shelfhelp.repositories;

import org.project.shelfhelp.DB;

import io.javalin.http.Context;
import org.project.shelfhelp.models.User;
import org.project.shelfhelp.models.UserDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String password = rs.getString("password");
                    String avatar = rs.getString("avatar_url");
                    return new User(id, avatar, password, username, lastName, firstName);


                } else {
                    return null;

                }
            }
        }
    }

    public static int insertNewUser(UserDTO newUser) throws SQLException {

        // check if user exists in system
        if(findUser(newUser.username()) != null){
            return -1;
        }
        String query = "INSERT INTO users \n";
        query += "(first_name, last_name, username, password, avatar_url) \n";
        query += ("VALUES (?, ?, ?, ?, ?); \n");

        // try with resources - get connection
        try (var con = DB.getConnection();
             var stmt = con.prepareStatement(query);) {

            stmt.setString(1, newUser.firstName());
            stmt.setString(2, newUser.lastName());
            stmt.setString(3, newUser.username());
            stmt.setString(4, newUser.password());
            stmt.setString(5, newUser.avatarUrl());

            // returns the number of rows that were updated - should be 1 if successful
            int updatedRows = stmt.executeUpdate();

            if (updatedRows == 0) {
                return -1;
                // get the newly created id and add it to the user details that are returned
            } else {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        }
    }

    public static String validateAvatarUrl(String avatarUrl){
        if(avatarUrl.equals("http://") | avatarUrl.equals("https://")){
            return "";
        }

        Pattern pattern = Pattern.compile(".*\\.(jpe?g|png)$");
        Matcher matcher = pattern.matcher(avatarUrl);
        boolean hasImgExtension = matcher.matches();

        if(hasImgExtension){
            return avatarUrl;
        } else {
            return "";
        }
    }

    public static String hashPassword(String plainPassword, String username) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String saltedPassword = username + "shelfHelp" + plainPassword;
        messageDigest.update(saltedPassword.getBytes());
        String hashedPassword = new String(messageDigest.digest());

        return hashedPassword;
    }
}
