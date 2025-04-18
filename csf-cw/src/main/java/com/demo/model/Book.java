package com.demo.model;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;
    private double price;
    private int stockQuantity;

    public Book() {}

    public Book(String title, String author, String ISBN, int publicationYear, double price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public String getISBN() {return ISBN;}
    public int getPublicationYear() {return publicationYear;}
    public double getPrice() {return price;}
    public int getStockQuantity() {return stockQuantity;}

    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setISBN(String ISBN) {this.ISBN = ISBN;}
    public void setPublicationYear(int publicationYear) {this.publicationYear = publicationYear;}
    public void setPrice(double price) {this.price = price;}
    public void setStockQuantity(int stockQuantity) {this.stockQuantity = stockQuantity;}





}
