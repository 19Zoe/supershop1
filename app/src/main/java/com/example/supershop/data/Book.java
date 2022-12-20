package com.example.supershop.data;

public class Book {
    int image;         //图标
    String title;   //书名
    String time;       //出版时间

    public Book(int id, String title, String time) {
        this.image = id;
        this.title = title;
        this.time =time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
