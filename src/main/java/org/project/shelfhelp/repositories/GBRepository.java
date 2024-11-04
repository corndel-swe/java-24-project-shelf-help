package org.project.shelfhelp.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.project.shelfhelp.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GBRepository {
    static String generalUrl = "https://books.google.com/books"; // Amend once you know the correct link
    static String apiKey = "AIzaSyDVEnipudTAkK1cFIw1Tuy7I4vRbqh8WUw"; // Don't think this is needed
    static String id = "buc0AAAAMAAJ"; // This is an example for now. Replace with general fed in ID

    public static Book getABookbyId(String id) throws Exception {

        String url = "https://www.googleapis.com/books/v1/volumes/" + id;

        var response = Unirest.get(url).header("Accept", "application.json").asString();

        String book = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode bookTree = mapper.readTree(book);

        // FOR DEBUGGING
     System.out.println(bookTree);

        JsonNode volumeInfo = bookTree.get("volumeInfo");
        JsonNode imageLinks = volumeInfo.get("imageLinks");

        String title = String.valueOf(volumeInfo.get("title"));
        String author = String.valueOf(volumeInfo.get("authors"));
        int year = Integer.parseInt(String.valueOf(volumeInfo.get("publishedDate")).replace("\"",""));
        String summary = String.valueOf(volumeInfo.get("description"));
//        String genre = String.valueOf(volumeInfo.get("authors")); *** THERE IS NO GENRE FIELD ***
        String bookCover = String.valueOf(imageLinks.get("thumbnail"));
        Float averagePublicRating = Float.valueOf(String.valueOf(volumeInfo.get("averageRating")));

        System.out.println("title: " + title);
        System.out.println("author: " + author);
        System.out.println("year: " + year);
        System.out.println("summary: " + summary);
        System.out.println("book cover: " + bookCover);
        System.out.println("rating: " + averagePublicRating);


        return new Book(id, title, author, year, averagePublicRating, summary, bookCover);
    }

//    public static List <Book> getBooksByTitle(String title) throws Exception {
//
//        String url = "https://www.googleapis.com/books/v1/volumes/?q=" + title;
//
//        var response = Unirest.get(url).header("Accept", "application.json").asString();
//
//        String book = response.getBody();
//        ObjectMapper mapper = new ObjectMapper();
//
//        JsonNode bookTree = mapper.readTree(book);
//
////        // FOR DEBUGGING
////        System.out.println(bookTree);
//        var books = bookTree.get("items"); // Gets an individual book
//        var individualBook = bookTree.get("items").get(2); // Gets an individual book
//        List<Book> bookList = new ArrayList<>();
//
//        for (JsonNode i: books){
//            JsonNode volumeInfo = bookTree.get("volumeInfo");
//            JsonNode imageLinks = volumeInfo.get("imageLinks");
//
//            String id = String.valueOf(volumeInfo.get("id"));
//            String author = String.valueOf(volumeInfo.get("authors"));
//            int year = Integer.parseInt(String.valueOf(volumeInfo.get("publishedDate")).replace("\"",""));
//            String summary = String.valueOf(volumeInfo.get("description"));
//            String bookCover = String.valueOf(imageLinks.get("thumbnail"));
//            Float averagePublicRating = Float.valueOf(String.valueOf(volumeInfo.get("averageRating")));
//
//
//
//            System.out.println("title: " + title);
//            System.out.println("author: " + author);
//            System.out.println("year: " + year);
//            System.out.println("summary: " + summary);
//            System.out.println("book cover: " + bookCover);
//            System.out.println("rating: " + averagePublicRating);
//
//        }
//
//        System.out.println(books);
//        System.out.println(individualBook);
//
//
//        return null;
//    }
//public static void main(String[] args) throws Exception {
//    GBRepository.getABookbyId("buc0AAAAMAAJ");
//    GBRepository.getBooksByTitle("Alice in borderland");
//}

}
