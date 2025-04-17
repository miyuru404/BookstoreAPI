package modelData;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDataStore {
    private static final List<Book> books = new ArrayList<Book>();
    private static Scanner scanner = new Scanner(System.in);

    public static void addBook(Book book) {
        books.add(book);
    }

    public static void deleteBook(Book book) {
        books.remove(book);
    }

    public static void updateBook(Book book) {

    }

    // method overloading if user have given other books details other than ISBN
    public static Book getBook(String title, String author, int year) {
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return null;
        }
        for (Book book : books) {
            if (book.getTitle().equals(title)
                    && book.getAuthor().equals(author)
                    && book.getPublicationYear() == year) {
                if (book.getStockQuantity() > 0) {
                    return book;
                } else {
                    System.out.println("The book is out of stock.");
                    return null;
                }
            }
        }
        System.out.println("The book was not found.");
        return null;
    }

    // method overloading if user have given only the ISBN
    public static Book getBook(String ISBN) {
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return null;
        }
        for (Book book : books) {
            if (book.getISBN() == ISBN) {
                if (book.getStockQuantity() > 0) {
                    return book;
                } else {
                    System.out.println("The book is out of stock.");
                    return null;
                }
            }
        }
        System.out.println("The book was not found.");
        return null;
    }
}
