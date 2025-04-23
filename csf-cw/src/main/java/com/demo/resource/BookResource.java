package com.demo.resource;

import com.demo.model.Book;
import com.demo.dataModel.BookDataStore;
import com.demo.exception.BookNotFoundException;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @GET
    @Path("/getBook/{isbn}")
    public Response getBookByISBN(@PathParam("isbn") String isbn) {
        if (BookDataStore.getAllBooks().isEmpty()) {
            throw new BookNotFoundException("No books have been added to the database.");
        }

        Book book = BookDataStore.getBook(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book not found.");
        }

        return Response.ok(book).build();
    }

    @POST
    @Path("/addBook")
    public Response addBook(Book book) {
        if (book == null) throw new BookNotFoundException("Book data is missing or malformed.");

        if (book.getISBN() == null || book.getISBN().trim().isEmpty())
            throw new BookNotFoundException("ISBN is required.");

        if (book.getTitle() == null || book.getTitle().trim().isEmpty())
            throw new BookNotFoundException("Title is required.");

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty())
            throw new BookNotFoundException("Author is required.");

        if (book.getPublicationYear() <= 0)
            throw new BookNotFoundException("Publication year must be a valid year.");

        if (book.getPrice() <= 0)
            throw new BookNotFoundException("Price must be valid prize.");

        if (book.getStockQuantity() <= 0)
            throw new BookNotFoundException("At least 1 book needs to be added to stock.");

        if (BookDataStore.getBook(book.getISBN()) != null)
            throw new BookNotFoundException("A book with this ISBN already exists.");

        BookDataStore.addBook(book);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Book added successfully.\"}").build();
    }

    @PUT
    @Path("/updateBook/{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, Book updatedBook) {
        if (updatedBook == null)
            throw new BookNotFoundException("Book data is missing or malformed.");

        if (updatedBook.getISBN() == null || updatedBook.getISBN().trim().isEmpty())
            throw new BookNotFoundException("ISBN is required.");

        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty())
            throw new BookNotFoundException("Title is required.");

        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty())
            throw new BookNotFoundException("Author is required.");

        if (updatedBook.getPublicationYear() <= 0)
            throw new BookNotFoundException("Publication year must be a valid year.");

        if (updatedBook.getPrice() <= 0)
            throw new BookNotFoundException("Price must be valid prize.");

        if (updatedBook.getStockQuantity() <= 0)
            throw new BookNotFoundException("At least 1 book needs to be added to stock.");

        Book existingBook = BookDataStore.getBook(isbn);
        if (existingBook == null)
            throw new BookNotFoundException("Book not found.");

        BookDataStore.updateBook(existingBook.getTitle(), existingBook.getAuthor(), existingBook.getISBN(), updatedBook);

        return Response.ok("{\"message\":\"Book updated successfully\"}").build();
    }

    @DELETE
    @Path("/deleteBook/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        Book book = BookDataStore.getBook(isbn);
        if (book == null)
            throw new BookNotFoundException("Book not found.");

        BookDataStore.removeBook(book.getTitle(), book.getAuthor(), book.getISBN());
        return Response.ok("{\"message\":\"Book deleted successfully\"}").build();
    }
}