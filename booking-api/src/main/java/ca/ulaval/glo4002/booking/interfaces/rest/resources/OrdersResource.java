package ca.ulaval.glo4002.booking.interfaces.rest.resources;

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

import ca.ulaval.glo4002.booking.domain.Orchestrator;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers.PassOrderResponseMapper;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientError;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidEventDateException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidOrderDateException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidVendorCodeException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.OrderNotFoundException;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassOrderRequest;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrdersResource {

    private Orchestrator orchestrator;
    private PassOrderService passOrderService;
    
    @Inject
    public OrdersResource(Orchestrator orchestrator, PassOrderService passOrderService) {
        this.orchestrator = orchestrator;
        this.passOrderService = passOrderService;
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String stringId) throws OrderNotFoundException {
        Long id = Long.parseLong(stringId);
        Optional<PassOrder> passOrder = passOrderService.getOrder(id);
        if (!passOrder.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        return Response.ok().entity(new PassOrderResponseMapper().getPassOrderResponse(passOrder.get())).build();
    }

    @POST
    public Response create(PassOrderRequest request, @Context UriInfo uriInfo) throws ClientError {
        if (!request.vendorCode.equals("TEAM")) {
            throw new InvalidVendorCodeException();
        }

        try {
            PassOrder passOrder = orchestrator.orchestPassCreation(request.orderDate, request.vendorCode, request.passes);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Long.toString(passOrder.getId()));
            return Response.created(builder.build()).build();
        }
        catch (OutOfSaleDatesException exception) {
            throw new InvalidOrderDateException(exception.getMessage());
        }
        catch (OutOfFestivalDatesException exception) {
            throw new InvalidEventDateException(exception.getMessage());
        }
        catch (IllegalArgumentException exception) {
            throw new InvalidFormatException();
        }
    }
}
