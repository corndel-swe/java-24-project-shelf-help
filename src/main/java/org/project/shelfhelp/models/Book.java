package org.project.shelfhelp.models;

public class Book {
    private String id;
    private String title;
    private String author;
    private int year;
    private float averageRating;
    private String bookSummary;
    private String bookCover;

    public Book(String id , String title, String author, int year, float averageRating, String bookSummary, String bookCover) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.averageRating = averageRating;
        this.bookSummary = bookSummary;
        this.bookCover = bookCover;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getBookSummary() {
        return bookSummary;
    }

    public void setBookSummary(String bookSummary) {
        this.bookSummary = bookSummary;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", averageRating=" + averageRating +
                ", bookSummary='" + bookSummary + '\'' +
                ", bookCover='" + bookCover + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
