package ca.ulaval.glo4002.booking.application.order;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class PassOrderUseCase {

    private final TransportRequester transportRequester;
    private final OxygenRequester oxygenRequester;
    private final PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;

    public PassOrderUseCase(TransportRequester transportRequester, OxygenRequester oxygenRequester, PassOrderFactory passOrderCreator, PassOrderRepository passOrderRepository) {
        this.transportRequester = transportRequester;
        this.oxygenRequester = oxygenRequester;
        this.passOrderFactory = passOrderCreator;
        this.passOrderRepository = passOrderRepository;
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = orderPasses(orderDate, vendorCode, passRequest);

        for (Pass pass : passOrder.getPasses()) {
            reserveShuttles(pass);
            orderOxygen(orderDate, pass);
        }
        return passOrder;
    }

    private PassOrder orderPasses(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        passOrderRepository.save(passOrder);
        return passOrder;
    }

    private void reserveShuttles(Pass pass) {
        ShuttleCategory shuttleCategory = PassCategoryMapper.getShuttleCategory(pass.getPassCategory());
        transportRequester.reserveDeparture(shuttleCategory, pass.getStartDate(), pass.getPassNumber());
        transportRequester.reserveArrival(shuttleCategory, pass.getEndDate(), pass.getPassNumber());
    }

    private void orderOxygen(OffsetDateTime orderDate, Pass pass) {
        PassCategory passCategory = pass.getPassCategory();
        OxygenGrade oxygenGrade = PassCategoryMapper.getOxygenGrade(passCategory);
        int oxygenQuantityPerDay = PassCategoryMapper.getOxygenQuantity(passCategory);
        int numberOfDays = (int) ChronoUnit.DAYS.between(pass.getStartDate(), pass.getEndDate()) + 1;

        oxygenRequester.orderOxygen(orderDate.toLocalDate(), oxygenGrade, oxygenQuantityPerDay * numberOfDays);
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }
}
