package ca.ulaval.glo4002.booking.api.resources.passOrder;

import java.net.URI;
import java.net.URISyntaxException;

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
import ca.ulaval.glo4002.booking.application.use_cases.PassOrderUseCase;
import ca.ulaval.glo4002.booking.api.exceptions.NotFoundException;
import ca.ulaval.glo4002.booking.api.helpers.response.LocationHeaderCreator;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private PassOrderUseCase passOrderUseCase;
    private final PassOrderResponseMapper passOrderResponseMapper;

    @Inject
    public OrdersResource(PassOrderUseCase passOrderUseCase, PassOrderResponseMapper passOrderResponseMapper) {
        this.passOrderUseCase = passOrderUseCase;
        this.passOrderResponseMapper = passOrderResponseMapper;
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String stringOrderNumber) {
        OrderNumber orderNumber = OrderNumber.of(stringOrderNumber);
        PassOrder passOrder = passOrderUseCase.getOrder(orderNumber)
            .orElseThrow(() -> new NotFoundException("order", orderNumber.getValue()));

        return Response.ok().entity(passOrderResponseMapper.getPassOrderResponse(passOrder)).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws URISyntaxException {
        PassOrder passOrder = passOrderUseCase.orchestPassCreation(request.orderDate, request.vendorCode,
                request.passes);
        String orderNumber = passOrder.getOrderNumber().getValue();
        URI location = LocationHeaderCreator.createURI(uriInfo, orderNumber);
        return Response.created(location).build();
    }
}
