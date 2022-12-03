package com.example.supershop.data;

public class Book {
    int image;         //图标
    String title;   //书名

    public Book(int id, String title) {
        this.image = id;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
