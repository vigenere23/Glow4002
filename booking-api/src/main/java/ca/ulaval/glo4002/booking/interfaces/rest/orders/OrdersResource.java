package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiException;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormatApiException;

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
        PassOption passOption = null;
        PassCategory passCategory = null;
        List<OffsetDateTime> eventDates = null;

        try {
            passOption = PassOption.fromString(request.passOption);
            passCategory = PassCategory.fromString(request.passCategory);
            if (request.eventDates != null) {
                eventDates = request.eventDates
                    .stream()
                    .map(stringDate -> OffsetDateTime.of(LocalDate.parse(stringDate), LocalTime.NOON, ZoneOffset.UTC))
                    .collect(Collectors.toList());
            }
        }
        catch (Exception exception) {
            throw new InvalidFormatApiException();
        }

        // TODO do some more filtering for dates etc.

        // TODO call Festival for creating order and oxygen etc.

        // TODO return a new order response from the returned order (may need to create PassResponses too)

        OrderResponse orderResponse = new OrderResponse(passOption, passCategory, eventDates);
        return Response.ok().entity(orderResponse).build();
    }
}
