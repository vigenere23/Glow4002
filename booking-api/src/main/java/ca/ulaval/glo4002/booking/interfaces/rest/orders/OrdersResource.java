package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.OffsetDateTime;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiException;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormatApiException;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.orderDTOs.PassDTO;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().build();
    }

    @POST
    public Response create(OrderRequest request) throws ApiException {
        OffsetDateTime orderDate = null;
        String vendorCode = null;
        PassDTO passes = null;

        try {
            orderDate = OffsetDateTime.parse(request.order.orderDate);
            vendorCode = request.order.vendorCode;
            passes = request.order.passes;
        }
        catch (Exception exception) {
            throw new InvalidFormatApiException();
        }

        // TODO do some more filtering for dates etc.

        // TODO call Festival for creating order and oxygen etc.

        // TODO return a new order response from the returned order (may need to create PassResponses too)

        OrderResponse orderResponse = new OrderResponse(orderDate, vendorCode, passes);
        return Response.ok().status(201).entity(orderResponse).build();
    }
}
