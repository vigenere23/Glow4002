package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos.OrderRequest;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private Glow4002 festival;

    // TODO remove when correctly injecting
    public OrdersResource() {
        this.festival = new Glow4002(new HeapRepository());
    }

    public OrdersResource(Glow4002 festival) {
        this.festival = festival;
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) throws Exception {
        PassOrder passOrder = this.festival.getOrder(id);
        return Response.ok().entity(passOrder.serialize()).build();
    }

    @POST
    public Response create(OrderRequest request, @Context UriInfo uriInfo) throws Exception {
        PassOrder passOrder = this.festival.reservePasses(request.orderDate, request.vendorCode, request.passes);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(passOrder.getId()));
        return Response.created(builder.build()).build();
    }
}
