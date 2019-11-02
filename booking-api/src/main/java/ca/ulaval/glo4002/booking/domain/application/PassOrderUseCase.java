package ca.ulaval.glo4002.booking.domain.application;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

public class PassOrderUseCase {

    private OrderResources orderResources;
    private PassOrderRepository passOrderRepository;
    private ShuttleRepository transportRepository;

    public PassOrderUseCase(OrderResources orderResources, PassOrderRepository passOrderRepository, ShuttleRepository transportRepository) {
        this.orderResources = orderResources;
        this.passOrderRepository = passOrderRepository;
        this.transportRepository = transportRepository;
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        List<Shuttle> departureShuttles = transportRepository.findShuttlesByLocation(Location.EARTH);
        List<Shuttle> arrivalShuttles = transportRepository.findShuttlesByLocation(Location.ULAVALOGY);
        PassOrder passOrder = orderResources.orderPasses(orderDate, vendorCode, passRequest, departureShuttles, arrivalShuttles);
        saveInRepositories(passOrder, departureShuttles, arrivalShuttles);

        return passOrder;
    }

    private void saveInRepositories(PassOrder passOrder, List<Shuttle> departureShuttles, List<Shuttle> arrivalShuttles) {
        passOrderRepository.save(passOrder);
        transportRepository.saveDeparture(departureShuttles);
        transportRepository.saveArrival(arrivalShuttles);
    }
}
