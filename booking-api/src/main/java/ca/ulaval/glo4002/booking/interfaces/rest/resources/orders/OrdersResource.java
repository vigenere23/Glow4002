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

import ca.ulaval.glo4002.booking.application.orders.PassOrderCreationUseCase;
import ca.ulaval.glo4002.booking.application.orders.PassOrderFetchingUseCase;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
import ca.ulaval.glo4002.booking.interfaces.rest.helpers.response.LocationHeaderCreator;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests.PassOrderRequest;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses.PassOrderResponse;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    @Inject private PassOrderCreationUseCase passOrderCreationUseCase;
    @Inject private PassOrderFetchingUseCase passOrderFetchingUseCase;

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") OrderNumber orderNumber) {
        PassOrderDto passOrderDto = passOrderFetchingUseCase.getOrder(orderNumber);
        PassOrderResponse response = new PassOrderResponse(passOrderDto);
        return Response.ok().entity(response).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws URISyntaxException {
        PassOrderDto passOrderDto = passOrderCreationUseCase.orderPasses(request.orderDate, request.vendorCode,
                request.passes);
        URI location = LocationHeaderCreator.createURI(uriInfo, passOrderDto.orderNumber);
        return Response.created(location).build();
    }
}
