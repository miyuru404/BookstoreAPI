package modelData;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDataStore {
    private static final List<Book> books = new ArrayList<Book>();

    public static void addBook(Book book) {
        books.add(book);
    }

    public static void deleteBook(Book book) {
        books.remove(book);
    }

    public static void updateBook(String ISBN, String newTitle, String newAuthor, String newISBN, Integer newPublicationYear, Double newPrice, Integer newStockQuantity) {
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return;
        }

        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {

                if (newISBN != null && !newISBN.isEmpty()) {
                    book.setISBN(newISBN);
                }

                if (newTitle != null && !newTitle.isEmpty()) {
                    book.setTitle(newTitle);
                }

                if (newAuthor != null && !newAuthor.isEmpty()) {
                    book.setAuthor(newAuthor);
                }

                if (newPublicationYear != null && newPublicationYear > 0) {
                    book.setPublicationYear(newPublicationYear);
                }

                if (newPrice != null && newPrice > 0) {
                    book.setPrice(newPrice);
                }

                if (newStockQuantity != null && newStockQuantity >= 0) {
                    book.setStockQuantity(newStockQuantity);
                }

                System.out.println("Book updated successfully.");
                return;
            }
        }

        System.out.println("Book with given ISBN not found.");
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
