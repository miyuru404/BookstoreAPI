package com.demo.dataModel;



import com.demo.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDataStore {
    private static final List<Book> books = new ArrayList<Book>();

    public static List<Book> getAllBooks() {
        return books;
    }

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

    public static void addBook(Book book) {


        books.add(book);
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

    public static void updateBook(String title,String author, String ISBN, Book newBook) {
        Book book = getBook(title,author,ISBN);
        if(book == null){
            System.out.println("no book found");
        }
        else {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setISBN(newBook.getISBN());
            book.setPublicationYear(newBook.getPublicationYear());
            book.setPrice(newBook.getPrice());
            book.setStockQuantity(newBook.getStockQuantity());
            System.out.println("book updated");
        }
    }


}
