package com.demo.dataModel;



import com.demo.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDataStore {
    private static final List<Book> books = new ArrayList<Book>();

    public static Book getBook(String ISBN) {
        if(books.isEmpty()){
            System.out.println("no books been added yet");
            return null;
        }
        for(Book book : books){
            if(book.getISBN().equals(ISBN)){
                return book;
            }
        }
        System.out.println("no book found");
        return null;
    }
    public static Book getBook(String title, String author, String ISBN){
        if(books.isEmpty()){
            System.out.println("no books been added yet");
            return null;
        }
        for(Book book : books){
            if(book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getISBN().equals(ISBN)){
                System.out.println("book found");
                return book;
            }
        }
        System.out.println("no book found");
        return null;
    }

    public static void addBook(String title , String author,String ISBN,int publicationYear,double prize , int stockQuantity) {
        if(getBook(title, author, ISBN) != null){
            System.out.println("book already exists");
            return;
        }
        if(stockQuantity < 1){
            System.out.println("at least 1 stock required");
            return;
        }
        Book newBook = new Book(title,author,ISBN,publicationYear,prize,stockQuantity);
        books.add(newBook);

    }

    public static void removeBook(String title, String author, String ISBN) {
        Book book = getBook(title,author,ISBN);
        if(book == null){
            System.out.println("no book found");
        }
        else {
            books.remove(book);
            System.out.println("book removed");
        }
    }

    public static void updateBook(String title,String author, String ISBN,String newTitle, String newAuthor, String newISBN, int newPublicationYear, double newPrize, int newStockQuantity) {
        Book book = getBook(title,author,ISBN);
        if(book == null){
            System.out.println("no book found");
        }
        else {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setISBN(newISBN);
            book.setPublicationYear(newPublicationYear);
            book.setPrice(newPrize);
            book.setStockQuantity(newStockQuantity);
            System.out.println("book updated");
        }
    }


}
