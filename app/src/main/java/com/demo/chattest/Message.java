package com.demo.chattest;

public class Message {
    private String author;
    private String name;
    private String message;
    private long date;

    public Message() {
    }

    public Message(String author, String name, String message, long date) {
        this.author = author;
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
