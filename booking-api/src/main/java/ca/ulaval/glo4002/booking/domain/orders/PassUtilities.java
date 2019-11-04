package ca.ulaval.glo4002.booking.domain.orders;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;
import ca.ulaval.glo4002.booking.helpers.DateCalculator;

import java.time.OffsetDateTime;
import java.util.List;

public class PassUtilities {
    private final TransportReservation transportReservation;
    private final OxygenRequester oxygenRequester;
    private final PassOrderFactory passOrderFactory;

    public PassUtilities(TransportReservation transportReservation, OxygenRequester oxygenRequester, PassOrderFactory passOrderFactory) {
        this.transportReservation = transportReservation;
        this.oxygenRequester = oxygenRequester;
        this.passOrderFactory = passOrderFactory;
    }

    public PassOrder orderPasses(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest, List<Shuttle> departureShuttles, List<Shuttle> arrivalShuttles) {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        for (Pass pass : passOrder.getPasses()) {
            reserveShuttles(pass, departureShuttles, arrivalShuttles);
            orderOxygen(orderDate, pass);
        }

        return passOrder;
    }

    private void reserveShuttles(Pass pass, List<Shuttle> departureShuttles, List<Shuttle> arrivalShuttles) {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(pass.getPassCategory());
        transportReservation.reserveShuttles(shuttleCategory, pass.getStartDate(), pass.getPassNumber(), departureShuttles, arrivalShuttles);
    }

    private void orderOxygen(OffsetDateTime orderDate, Pass pass) {
        // TODO modify in issue #112
        PassCategory passCategory = pass.getPassCategory();
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(passCategory);
        int oxygenQuantityPerDay = PassCategoryMapper.getOxygenQuantity(passCategory);
        int numberOfDays = DateCalculator.daysBetween(pass.getStartDate(), pass.getEndDate());

        oxygenRequester.orderOxygen(orderDate.toLocalDate(), oxygenGrade, oxygenQuantityPerDay * numberOfDays);
    }
}
