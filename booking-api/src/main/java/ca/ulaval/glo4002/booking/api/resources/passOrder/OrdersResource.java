package ca.ulaval.glo4002.booking.api.resources.passOrder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ca.ulaval.glo4002.booking.api.resources.passOrder.dto.PassOrderResponseMapper;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.api.exceptions.NotFoundException;
import ca.ulaval.glo4002.booking.api.resources.helpers.LocationHeaderCreator;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private PassOrderUseCase passOrderUseCase;

    @Inject
    public OrdersResource(PassOrderUseCase passOrderUseCase) {
        this.passOrderUseCase = passOrderUseCase;
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String stringOrderNumber) {
        OrderNumber orderNumber = OrderNumber.of(stringOrderNumber);
        Optional<PassOrder> passOrder = passOrderUseCase.getOrder(orderNumber);
        if (!passOrder.isPresent()) {
            throw new NotFoundException("order", orderNumber.getValue());
        }
        return Response.ok().entity(new PassOrderResponseMapper()
                .getPassOrderResponse(passOrder.get())).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws URISyntaxException {
        PassOrder passOrder = passOrderUseCase.orchestPassCreation(request.orderDate, request.vendorCode, request.passes);
        String orderNumber = passOrder.getOrderNumber().getValue();

        URI location = LocationHeaderCreator.createURI(uriInfo, orderNumber);
        return Response.status(201).location(location).build();
    }
}
