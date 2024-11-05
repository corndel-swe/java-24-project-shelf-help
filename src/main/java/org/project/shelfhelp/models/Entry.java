package org.project.shelfhelp.models;

public class Entry {
    private int userId;
    private String bookId;
    private boolean isRead = false;
    private String tag;
    private String title;
    private String author;
    private String year;
    private String bookCover;

    public Entry() {};

    public Entry(int userId, String bookId, String tag) {
        this.userId = userId;
        this.bookId = bookId;
        this.isRead = false;
        this.tag = tag;
    }

    public Entry(String bookId, String title, String author, String year, String bookCover, boolean isRead, String tag) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.bookCover = bookCover;
        this.isRead = isRead;
        this.tag = tag;
    }

    public Entry(int userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.isRead = false;
        this.tag = null;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return this.bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean isRead() {
        return this.isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}