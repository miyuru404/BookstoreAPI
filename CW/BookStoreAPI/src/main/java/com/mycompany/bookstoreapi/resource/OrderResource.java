package com.mycompany.bookstoreapi.resource;





import com.mycompany.bookstoreapi.dataModel.CustomerDataStore;
import com.mycompany.bookstoreapi.dataModel.OrderDataStore;
import com.mycompany.bookstoreapi.exception.CustomerNotFoundException;
import com.mycompany.bookstoreapi.exception.InvalidInputException;
import com.mycompany.bookstoreapi.exception.OrderNotFoundException;
import com.mycompany.bookstoreapi.model.Customer;
import com.mycompany.bookstoreapi.model.Order;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    @Path("/getOrderById/{orderId}")
    public Response getOrderById(@PathParam("orderId") String orderId) {
        if (OrderDataStore.getAllOrders().isEmpty()) {
            throw new OrderNotFoundException("No orders have been added to the system.");
        }

        Order order = OrderDataStore.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order not found.");
        }

        return Response.ok(order).build();
    }

    @GET
    @Path("/allOrder")
    public Response getAllOrders() {
        List<Order> orders = OrderDataStore.getAllOrders();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/getOrderByEmail/{email}")
    public Response getOrdersByCustomerEmail(@PathParam("email") String email) {
        Order orders = OrderDataStore.getOrdersByCustomerEmail(email);
        if (orders == null) {
            throw new OrderNotFoundException("No orders found for this customer.");
        }
        return Response.ok(orders).build();
    }

    @POST
    @Path("/addOrder/{email}")
    public Response addOrder(@PathParam("email") String email , String shippingAddress) {


        if (email== null || email.trim().isEmpty())
            throw new InvalidInputException("Customer email is required.");

        if (shippingAddress == null || shippingAddress.trim().isEmpty())
            throw new InvalidInputException("Shipping address is required.");
        Customer customer = CustomerDataStore.getCustomerByEmail(email);
        if(customer == null)
            throw new CustomerNotFoundException("Customer not found.");


        Order exOrder = OrderDataStore.getOrdersByCustomerEmail(email);
        if (exOrder != null)
            throw new InvalidInputException("this customer already have unpaid order .");

        Order order = new Order(email,shippingAddress);
        //order.setOrderDate(new Date());
        OrderDataStore.addOrder(order);

        return Response.status(Response.Status.CREATED)
                .entity("{\"message\":\"Order added successfully.\"}").build();
    }

    @PUT
    @Path("/updateStatus/{orderId}")
    public Response updateOrderStatus(@PathParam("orderId") String orderId, String newStatus) {
        boolean updated = OrderDataStore.updateOrderStatus(orderId, newStatus);
        if (!updated) {
            throw new OrderNotFoundException("Order not found.");
        }
        return Response.ok("{\"message\":\"Order status updated successfully.\"}").build();
    }

    @DELETE
    @Path("/deleteOrder/{orderId}")
    public Response deleteOrder(@PathParam("orderId") String orderId) {
        boolean removed = OrderDataStore.removeOrder(orderId);
        if (!removed) {
            throw new OrderNotFoundException("Order not found.");
        }
        return Response.ok("{\"message\":\"Order deleted successfully.\"}").build();
    }

}
