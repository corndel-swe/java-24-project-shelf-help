package org.project.shelfhelp.models;

public class ReadingListEntry {
    private int userId;
    private int bookId;
    private boolean isRead = false;
    private String tag;

    public ReadingListEntry() {};

    public ReadingListEntry(int userId, int bookId, String tag) {
        this.userId = userId;
        this.bookId = bookId;
        this.isRead = false;
        this.tag = tag;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return this.bookId;
    }

    public void setBookId(int bookId) {
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