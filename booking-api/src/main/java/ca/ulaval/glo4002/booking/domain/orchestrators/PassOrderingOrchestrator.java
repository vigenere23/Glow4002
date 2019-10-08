package ca.ulaval.glo4002.booking.domain.orchestrators;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenGrade;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToOxygenQuantity;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryToShuttleCategory;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class PassOrderingOrchestrator {

    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;
    private final PassOrderRequester passOrderCreator;

    public PassOrderingOrchestrator(TransportRequester transportRequester, OxygenRequester oxygenRequester, PassOrderRequester passOrderCreator) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
        this.passOrderCreator = passOrderCreator;
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderCreator.orderPasses(orderDate, vendorCode, passRequest);

        PassCategoryToShuttleCategory passCategoryToShuttleCategory = new PassCategoryToShuttleCategory();
        for (Pass pass : passOrder.getPasses()) {
            PassCategory passCategory = pass.getPassCategory();

            ShuttleCategory shuttleCategory = passCategoryToShuttleCategory.getAssociatedValue(passCategory);
            transportRequester.reserveDeparture(shuttleCategory, pass.getStartDate(), pass.getPassNumber());
            transportRequester.reserveArrival(shuttleCategory, pass.getEndDate(), pass.getPassNumber());
            
            OxygenGrade oxygenGrade = new PassCategoryToOxygenGrade().getAssociatedValue(passCategory);
            int oxygenQuantityPerDay = new PassCategoryToOxygenQuantity().getAssociatedValue(passCategory);
            int numberOfDays = (int) ChronoUnit.DAYS.between(pass.getStartDate(), pass.getEndDate()) + 1;

            oxygenRequester.orderOxygen(orderDate.toLocalDate(), oxygenGrade, oxygenQuantityPerDay * numberOfDays);
        }
        return passOrder;
    }
}
