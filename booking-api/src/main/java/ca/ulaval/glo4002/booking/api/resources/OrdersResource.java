package ca.ulaval.glo4002.booking.api.resources;

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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ca.ulaval.glo4002.booking.api.dtoMappers.PassOrderResponseMapper;
import ca.ulaval.glo4002.booking.api.dtos.orders.PassOrderRequest;
import ca.ulaval.glo4002.booking.api.exceptions.ClientError;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidOrderDateException;
import ca.ulaval.glo4002.booking.api.exceptions.OrderNotFoundException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orchestrators.PassOrderingOrchestrator;
import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderExposer;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private PassOrderingOrchestrator orchestrator;
    private PassOrderExposer passOrderExposer;

    @Inject
    public OrdersResource(PassOrderingOrchestrator orchestrator, PassOrderExposer passOrderRequester) {
        this.orchestrator = orchestrator;
        this.passOrderExposer = passOrderRequester;
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String stringOrderNumber) throws OrderNotFoundException {
        OrderNumber orderNumber = OrderNumber.of(stringOrderNumber);
        Optional<PassOrder> passOrder = passOrderExposer.getOrder(orderNumber);
        if (!passOrder.isPresent()) {
            throw new OrderNotFoundException(orderNumber);
        }
        return Response.ok().entity(new PassOrderResponseMapper().getPassOrderResponse(passOrder.get())).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws ClientError, URISyntaxException {
        try {
            PassOrder passOrder = orchestrator.orchestPassCreation(request.orderDate, request.vendorCode, request.passes);
            String orderNumber = passOrder.getOrderNumber().getValue();

            URI location = LocationHeaderCreator.createURI(uriInfo, orderNumber);
            return Response.status(201).location(location).build();
        }
        catch (OutOfSaleDatesException exception) {
            throw new InvalidOrderDateException(exception.getMessage());
        }
        catch (OutOfFestivalDatesException exception) {
            throw new InvalidEventDateException(exception.getMessage());
        }
    }
}
