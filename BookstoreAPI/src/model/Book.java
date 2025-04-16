package model;

public class Book {
    private String title;
    private String author;
    private int ISBN;
    private String publicationYear;
    private int price;
    private int stockQuantity;

    public Book() {}

    public Book(String title, String author, int ISBN, String publicationYear, int price, int stockQuantity) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    public String getTitle() {return title;}
    public String getAuthor() {return author;}
    public int getISBN() {return ISBN;}
    public String getPublicationYear() {return publicationYear;}
    public int getPrice() {return price;}
    public int getStockQuantity() {return stockQuantity;}

    public void setTitle(String title) {this.title = title;}
    public void setAuthor(String author) {this.author = author;}
    public void setISBN(int ISBN) {this.ISBN = ISBN;}
    public void setPublicationYear(String publicationYear) {this.publicationYear = publicationYear;}
    public void setPrice(int price) {this.price = price;}
    public void setStockQuantity(int stockQuantity) {this.stockQuantity = stockQuantity;}





}
