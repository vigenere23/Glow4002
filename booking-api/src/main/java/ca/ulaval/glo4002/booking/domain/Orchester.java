package ca.ulaval.glo4002.booking.domain;

import java.time.OffsetDateTime;
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

public class Orchester {

    private final TransportExposer transportExposer;
    private final OxygenRequester oxygenRequester;
    private final PassOrderService passOrderService;

    public Orchester(TransportExposer transportExposer, OxygenRequester oxygenRequester, PassOrderService passOrderService) {
        this.transportExposer = transportExposer;
        this.oxygenRequester = oxygenRequester;
        this.passOrderService = passOrderService;
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderService.orderPasses(orderDate, vendorCode, passRequests);

        PassCategoryToShuttleCategory passCategoryToShuttleCategory = new PassCategoryToShuttleCategory();
        for (Pass pass : passOrder.getPasses()) {
            PassCategory passCategory = pass.getPassCategory();

            ShuttleCategory shuttleCategory = passCategoryToShuttleCategory.getAssociatedValue(passCategory);
            this.transportExposer.reserveDeparture(shuttleCategory, pass.getStartDate().toLocalDate(), pass.getPassNumber());
            this.transportExposer.reserveArrival(shuttleCategory, pass.getEndDate().toLocalDate(), pass.getPassNumber());
            
            OxygenGrade oxygenGrade = new PassCategoryToOxygenGrade().getAssociatedValue(passCategory);
            int oxygenQuantityPerDay = new PassCategoryToOxygenQuantity().getAssociatedValue(passCategory);
            OffsetDateTime oxygenOrderDate = pass.getStartDate();

            while (!oxygenOrderDate.isAfter(pass.getEndDate())) {
                this.oxygenRequester.orderOxygen(oxygenOrderDate, oxygenGrade, oxygenQuantityPerDay);
                oxygenOrderDate = oxygenOrderDate.plusDays(1);
            }
        }

        return passOrder;
    }
}
