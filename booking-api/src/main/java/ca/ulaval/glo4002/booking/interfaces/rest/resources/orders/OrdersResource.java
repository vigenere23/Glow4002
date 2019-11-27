package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders;

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

import ca.ulaval.glo4002.booking.interfaces.rest.helpers.response.LocationHeaderCreator;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests.PassOrderRequest;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses.PassOrderResponse;
import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;

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
        PassOrderDto passOrderDto = passOrderUseCase.getOrder(orderNumber);
        PassOrderResponse response = new PassOrderResponse(passOrderDto);
        return Response.ok().entity(response).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws URISyntaxException {
        PassOrderDto passOrderDto = passOrderUseCase.orchestPassCreation(request.orderDate, request.vendorCode,
                request.passes);
        URI location = LocationHeaderCreator.createURI(uriInfo, passOrderDto.orderNumber);
        return Response.created(location).build();
    }
}
