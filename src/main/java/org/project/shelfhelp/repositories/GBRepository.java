package org.project.shelfhelp.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;
import org.project.shelfhelp.models.Book;

import java.util.ArrayList;
import java.util.List;

public class GBRepository {

//    static String id = "buc0AAAAMAAJ"; // This is an example for now

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
        List<String> authorList = new ArrayList<>();

        for (JsonNode item : volumeInfo.get("authors")){
            authorList.add(item.asText());
        }
        String title = String.valueOf(volumeInfo.get("title")).replace("\"","");
        String author = String.join(", ", authorList);
        String year = (String.valueOf(volumeInfo.get("publishedDate")).replace("\"",""));
        String summary = String.valueOf(volumeInfo.get("description")).replace("\"","").replaceAll("<[^>]*>","");
        float averagePublicRating = (volumeInfo.get("averageRating") == null) ? 0 : Float.parseFloat(String.valueOf(volumeInfo.get("averageRating")));
        String bookCover =  "https://demo.publishr.cloud/assets/common/images/edition_placeholder.png";
        if (imageLinks != null && imageLinks.get("large") != null){
            bookCover = String.valueOf(imageLinks.get("large")).replace("\"","");
        } else if (imageLinks != null && imageLinks.get("thumbnail") != null)
        {
            bookCover = String.valueOf(imageLinks.get("thumbnail")).replace("\"","");
        }
        System.out.println("title: " + title);
        System.out.println("author: " + author);
        System.out.println("year: " + year);
        System.out.println("summary: " + summary);
        System.out.println("book cover: " + bookCover);
        System.out.println("rating: " + averagePublicRating);

//            return null; // here for debugging
        return new Book(id, title, author, year, averagePublicRating, summary, bookCover);
    }

    public static List <Book> getBooksByTitle(String SearchTitle) throws Exception {

        String url = "https://www.googleapis.com/books/v1/volumes/?q=" + SearchTitle;

        var response = Unirest.get(url).header("Accept", "application.json").asString();

        String book = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode bookTree = mapper.readTree(book);

//        // FOR DEBUGGING
//        System.out.println(bookTree);
        var books = bookTree.get("items"); // Gets an individual book
        List<Book> bookList = new ArrayList<>();

        for (JsonNode i: books){
            JsonNode volumeInfo = i.get("volumeInfo");
            JsonNode imageLinks = volumeInfo.get("imageLinks");
//            System.out.println(volumeInfo);

            List<String> authorList = new ArrayList<>();

            for (JsonNode item : volumeInfo.get("authors")){
                authorList.add(item.asText());
            }
            String title = String.valueOf(volumeInfo.get("title")).replace("\"","");
            String id = String.valueOf(i.get("id")).replace("\"","");
            String author = String.join(", ", authorList);
            String year = (String.valueOf(volumeInfo.get("publishedDate")).replace("\"",""));
            String summary = String.valueOf(volumeInfo.get("description")).replace("\"","").replaceAll("<[^>]*>","");

            String bookCover =  "https://demo.publishr.cloud/assets/common/images/edition_placeholder.png";
            if (imageLinks != null && imageLinks.get("large") != null){
                bookCover = String.valueOf(imageLinks.get("large")).replace("\"","");
            } else if (imageLinks != null && imageLinks.get("thumbnail") != null)
            {
                bookCover = String.valueOf(imageLinks.get("thumbnail")).replace("\"","");
            }

            System.out.println("bookcover"+ bookCover);

            float averagePublicRating = (volumeInfo.get("averageRating") == null) ? 0 : Float.parseFloat(String.valueOf(volumeInfo.get("averageRating")));

            bookList.add(new Book(id,title,author,year,averagePublicRating,summary,bookCover));
            // DEBUGGING

        }
        System.out.println("this is the bookLIst"+bookList);
//        return null;// DEBUGGING
        return bookList;
    }

    public static List <Book> getBooksByAuthor(String author) throws Exception {

        String url = "https://www.googleapis.com/books/v1/volumes/?q=inauthor:" + author;

        var response = Unirest.get(url).header("Accept", "application.json").asString();

        String book = response.getBody();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode bookTree = mapper.readTree(book);

//        // FOR DEBUGGING
//        System.out.println(bookTree);
        var books = bookTree.get("items"); // Gets an individual book
        var individualBook = bookTree.get("items").get(2); // Gets an individual book
        List<Book> bookList = new ArrayList<>();

        for (JsonNode i: books){
            JsonNode volumeInfo = i.get("volumeInfo");
            JsonNode imageLinks = volumeInfo.get("imageLinks");
//          System.out.println(volumeInfo); //DEBUGGING

            String id = String.valueOf(i.get("id")).replace("\"","");
            String title = String.valueOf(volumeInfo.get("title")).replace("\"","");
            String year = (String.valueOf(volumeInfo.get("publishedDate")).replace("\"",""));
            String summary = String.valueOf(volumeInfo.get("description")).replace("\"","").replaceAll("<[^>]*>","");
            float averagePublicRating = (volumeInfo.get("averageRating") == null) ? 0 : Float.parseFloat(String.valueOf(volumeInfo.get("averageRating")));
            String bookCover =  "https://demo.publishr.cloud/assets/common/images/edition_placeholder.png";
            if (imageLinks != null && imageLinks.get("large") != null){
                bookCover = String.valueOf(imageLinks.get("large")).replace("\"","");
            } else if (imageLinks != null && imageLinks.get("thumbnail") != null)
            {
                bookCover = String.valueOf(imageLinks.get("thumbnail")).replace("\"","");
            }
            System.out.println("bookcover"+ bookCover);
            bookList.add(new Book(id,title,author,year,averagePublicRating,summary,bookCover));

        }
        System.out.println(bookList);
//        return null;// DEBUGGING
        return bookList;
    }

    //// HERE FOR DEBUGGING
//public static void main(String[] args) throws Exception {
//    GBRepository.getABookbyId("Cta5AAAAIAAJ");
//    System.out.println("-------");
//
//    GBRepository.getBooksByTitle("twilight");
//    System.out.println("-------");
//    GBRepository.getBooksByAuthor("meyers");
//}


}
