package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok().build();
    }

    @POST
    public Response create(OrderRequest request) {
        OrderResponse orderResponse = new OrderResponse(request.passOption, request.passCategory, request.eventDates);
        return Response.ok().entity(orderResponse).build();
    }
}
