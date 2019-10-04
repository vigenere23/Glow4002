package ca.ulaval.glo4002.booking.domain;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToShuttleCategory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassRequest;

public class Orchester {

    private final TransportExposer transportExposer;
    private final PassOrderService passOrderService;

    public Orchester(TransportExposer transportExposer, PassOrderService passOrderService) {
        this.transportExposer = transportExposer;
        this.passOrderService = passOrderService;
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderService.orderPasses(orderDate, vendorCode, passRequests);

        PassCategoryToShuttleCategory passCategoryToShuttleCategory = new PassCategoryToShuttleCategory();
        for (Pass pass : passOrder.getPasses()) {
            this.transportExposer.reserveDeparture(
                passCategoryToShuttleCategory.getAssociatedValue(pass.getPassCategory()),
                pass.getStartDate().toLocalDate(),
                pass.getPassNumber()
            );
            this.transportExposer.reserveArrival(
                passCategoryToShuttleCategory.getAssociatedValue(pass.getPassCategory()),
                pass.getEndDate().toLocalDate(),
                pass.getPassNumber()
            );
        }

        return passOrder;
    }
}
