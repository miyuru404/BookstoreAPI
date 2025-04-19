package com.demo.resource;

import com.demo.model.Book;
import com.demo.dataModel.BookDataStore;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

//import static com.demo.dataModel.BookDataStore.books;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {



    //  Get book by ISBN
    @GET
    @Path("/{isbn}")
    public Response getBookByISBN(@PathParam("isbn") String isbn) {
        if (BookDataStore.getAllBooks().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"No books have been added to the database\"}").build();
        }

        Book book = BookDataStore.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }

        return Response.ok(book).build();
    }

    // add book
    @POST
    @Path("/addBook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        if (book == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Book data is missing or malformed.\"}").build();
        }

        // Validate required fields
        if (book.getISBN() == null || book.getISBN().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"ISBN is required.\"}").build();
        }

        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Title is required.\"}").build();
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Author is required.\"}").build();
        }

        if (book.getPublicationYear() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Publication year must be a valid year.\"}").build();
        }

        if (book.getPrice() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Price must be valid prize.\"}").build();
        }

        if (book.getStockQuantity() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"At least 1 book needs to be added to stock.\"}").build();
        }


        // Check for duplicates by ISBN
        if (BookDataStore.getBook(book.getISBN()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"A book with this ISBN already exists.\"}").build();
        }

        // Add the book
        BookDataStore.addBook(book);
        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Book added successfully.\"}").build();
    }




    //  Update a book
    @PUT
    @Path("/{isbn}")
    public Response updateBook(@PathParam("isbn") String isbn, Book updatedBook) {

        if (updatedBook == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Book data is missing or malformed.\"}").build();
        }

        // Validate required fields
        if (updatedBook.getISBN() == null || updatedBook.getISBN().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"ISBN is required.\"}").build();
        }

        if (updatedBook.getTitle() == null || updatedBook.getTitle().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Title is required.\"}").build();
        }

        if (updatedBook.getAuthor() == null || updatedBook.getAuthor().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Author is required.\"}").build();
        }

        if (updatedBook.getPublicationYear() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Publication year must be a valid year.\"}").build();
        }

        if (updatedBook.getPrice() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Price must be valid prize.\"}").build();
        }

        if (updatedBook.getStockQuantity() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"At least 1 book needs to be added to stock.\"}").build();
        }

        Book book = BookDataStore.getBook(isbn);

        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }

        BookDataStore.updateBook(book.getTitle(), book.getAuthor(), book.getISBN(),updatedBook);

        return Response.ok("{\"message\":\"Book updated successfully\"}").build();
    }

    //  Delete a book
    @DELETE
    @Path("/{isbn}")
    public Response deleteBook(@PathParam("isbn") String isbn) {
        Book book = BookDataStore.getBook(isbn);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Book not found\"}").build();
        }

        BookDataStore.removeBook(book.getTitle(), book.getAuthor(), book.getISBN());
        return Response.ok("{\"message\":\"Book deleted successfully\"}").build();
    }
}