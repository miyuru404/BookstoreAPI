package com.demo.resource;

import com.demo.dataModel.BookDataStore;
import com.demo.dataModel.CartDataStore;
import com.demo.dataModel.CustomerDataStore;
import com.demo.model.*;

import com.demo.exception.*;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    @GET
    @Path("/getcart/{email}")
    public Response getCartByCustomer(@PathParam("email") String email) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);
        if (customer == null) throw new CustomerNotFoundException("Customer not found!");

        Cart cart = CartDataStore.getCart(email);
        if (cart == null) throw new CartNotFoundException("Cart not found!");

        return Response.ok(cart).build();
    }

    @POST
    @Path("/addBook/{email}")
    public Response addBookToCart(@PathParam("email") String email, BookInput input) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);
        if (customer == null) throw new CustomerNotFoundException("Customer not found!");

        validateBookInput(input);

        Book book = BookDataStore.getBook(input.title, input.author, input.ISBN);
        if (book == null) throw new BookNotFoundException("Book not found in database.");

        Cart cart = CartDataStore.getCart(email);
        cart.addItem(book);

        return Response.ok("{\"message\":\"Book added to cart.\"}").build();
    }

    @GET
    @Path("/all")
    public Response getAllCarts() {
        List<Cart> carts = CartDataStore.getAllCarts();
        return Response.ok(carts).build();
    }

    @POST
    @Path("/removeBook/{email}")
    public Response removeBook(@PathParam("email") String email, BookInput input) {
        Customer customer = CustomerDataStore.getCustomerByEmail(email);
        if (customer == null) throw new CustomerNotFoundException("Customer not found!");

        validateBookInput(input);

        Book book = BookDataStore.getBook(input.title, input.author, input.ISBN);
        if (book == null) throw new BookNotFoundException("Book not found in database.");

        Cart cart = CartDataStore.getCart(email);
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new BookNotFoundException("No books found in the cart.");
        }

        cart.removeItem(book);
        return Response.ok("{\"message\":\"Book has been removed from the cart.\"}").build();
    }

    private void validateBookInput(BookInput input) {
        if (input.ISBN == null || input.ISBN.trim().isEmpty())
            throw new InvalidInputException("ISBN is required.");
        if (input.title == null || input.title.trim().isEmpty())
            throw new InvalidInputException("Title is required.");
        if (input.author == null || input.author.trim().isEmpty())
            throw new InvalidInputException("Author is required.");
    }
}

class BookInput {
    public String title;
    public String author;
    public String ISBN;
}
