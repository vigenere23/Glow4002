package ca.ulaval.glo4002.booking.services.orchestrators;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenGrade;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenQuantity;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToShuttleCategory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.services.orders.PassOrderService;
import ca.ulaval.glo4002.booking.services.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.services.transport.TransportExposer;

public class PassOrderingOrchestrator {

    private final TransportExposer transportExposer;
    private final OxygenRequester oxygenRequester;
    private final PassOrderService passOrderService;

    public PassOrderingOrchestrator(TransportExposer transportExposer, OxygenRequester oxygenRequester, PassOrderService passOrderService) {
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
