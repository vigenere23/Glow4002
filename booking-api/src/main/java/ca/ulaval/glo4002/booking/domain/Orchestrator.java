package ca.ulaval.glo4002.booking.domain;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenGrade;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenQuantity;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToShuttleCategory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassRequest;

public class Orchestrator {

    private final TransportExposer transportExposer;
    private final OxygenRequester oxygenRequester;
    private final PassOrderService passOrderService;

    public Orchestrator(TransportExposer transportExposer, OxygenRequester oxygenRequester, PassOrderService passOrderService) {
        this.transportExposer = transportExposer;
        this.oxygenRequester = oxygenRequester;
        this.passOrderService = passOrderService;
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderService.orderPasses(orderDate, vendorCode, passRequest);

        PassCategoryToShuttleCategory passCategoryToShuttleCategory = new PassCategoryToShuttleCategory();
        for (Pass pass : passOrder.getPasses()) {
            PassCategory passCategory = pass.getPassCategory();

            ShuttleCategory shuttleCategory = passCategoryToShuttleCategory.getAssociatedValue(passCategory);
            transportExposer.reserveDeparture(shuttleCategory, pass.getStartDate(), pass.getPassNumber());
            transportExposer.reserveArrival(shuttleCategory, pass.getEndDate(), pass.getPassNumber());
            
            OxygenGrade oxygenGrade = new PassCategoryToOxygenGrade().getAssociatedValue(passCategory);
            int oxygenQuantityPerDay = new PassCategoryToOxygenQuantity().getAssociatedValue(passCategory);
            int numberOfDays = (int) ChronoUnit.DAYS.between(pass.getStartDate(), pass.getEndDate()) + 1;

            oxygenRequester.orderOxygen(orderDate.toLocalDate(), oxygenGrade, oxygenQuantityPerDay * numberOfDays);
        }
        return passOrder;
    }
}
