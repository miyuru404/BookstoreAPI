package modelData;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDataStore {
    private static final List<Book> books = new ArrayList<Book>();

    public static void addBook(String newTitle, String newAuthor, String newISBN, Integer newPublicationYear, Double newPrice, Integer newStockQuantity) {
            if(newTitle != null
                    && newAuthor != null
                    && newISBN != null
                    && newPrice != null
                    && newPublicationYear != null
                    && newStockQuantity != null)
            {
                if(!books.contains(new Book(newTitle, newAuthor, newISBN, newPublicationYear, newPrice, newStockQuantity))) {

                    System.out.println("Book is added to the list");
                }
                else {
                    System.out.println("book already exists");
                }
        }

    }

    public static void deleteBook(String ISBN) {
        boolean found = false;
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return;
        }
        else {
            if(ISBN != null) {
                for (Book book : books) {
                    if (book.getISBN().equals(ISBN)) {
                        books.remove(book);
                        System.out.println("Book with ISBN " + ISBN + " deleted.");
                        found = true;
                    }
                }
                if(found ) {System.out.println("Book with ISBN " + ISBN + " not found");}
            }

            else{
                System.out.println("please add all required details about book");
            }
        }


    }
    // update the books detail if there are not null or empty
    public static void updateBook(String ISBN, String newTitle, String newAuthor, String newISBN, Integer newPublicationYear, Double newPrice, Integer newStockQuantity) {
        boolean found = false;

        if (books.isEmpty()) {
            System.out.println("No books available currently.");

        }
        else{
            for (Book book : books) {
                if (book.getISBN().equals(ISBN)) {
                    if (newISBN != null && !newISBN.isEmpty()
                            && newTitle != null && !newTitle.isEmpty()
                            && newAuthor != null && !newAuthor.isEmpty()
                            && newPublicationYear != null && newPublicationYear > 0
                            && newPrice != null && newPrice > 0
                            && newStockQuantity != null && newStockQuantity >= 0) {
                        book.setISBN(newISBN);
                        book.setTitle(newTitle);
                        book.setAuthor(newAuthor);
                        book.setPublicationYear(newPublicationYear);
                        book.setPrice(newPrice);
                        book.setStockQuantity(newStockQuantity);
                    }
                    System.out.println("Book updated successfully.");
                    found = true;

                }
            }
            if(found ) {System.out.println("Book with ISBN " + ISBN + " not found");}
        }
    }




    // method overloading if user have given other books details other than ISBN
    public static Book getBook(String title, String author, int year) {
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return null;
        }

        for (Book book : books) {
            if (book.getTitle().equals(title) &&
                    book.getAuthor().equals(author) &&
                    book.getPublicationYear() == year) {

                if (book.getStockQuantity() > 0) {
                    return book;
                } else {
                    System.out.println("The book is out of stock.");
                    return null;
                }
            }
        }
        System.out.println("Book with title '" + title + "' by " + author + " not found.");
        return null;
    }


    // method overloading if user have given only the ISBN
    public static Book getBook(String ISBN) {
        if (books.isEmpty()) {
            System.out.println("No books available currently.");
            return null;
        }

        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                if (book.getStockQuantity() > 0) {
                    return book;
                } else {
                    System.out.println("The book is out of stock.");
                    return null;
                }
            }
        }

        System.out.println("The book with ISBN '" + ISBN + "' was not found.");
        return null;
    }

}
