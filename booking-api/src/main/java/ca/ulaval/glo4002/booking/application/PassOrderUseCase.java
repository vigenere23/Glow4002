package ca.ulaval.glo4002.booking.application;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReservation;

public class PassOrderUseCase {

    private PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;
    private TransportReservation transportReservation;
    private ShuttleRepository transportRepository;
    private OxygenReserver oxygenReserver;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    public PassOrderUseCase(
            PassOrderFactory passFactory, PassOrderRepository passOrderRepository, TransportReservation transportReservation,
            ShuttleRepository transportRepository, OxygenReserver oxygenReserver, OxygenInventoryRepository oxygenInventoryRepository,
            OxygenHistoryRepository oxygenHistoryRepository) {
        this.passOrderFactory = passFactory;
        this.passOrderRepository = passOrderRepository;
        this.transportReservation = transportReservation;
        this.transportRepository = transportRepository;
        this.oxygenReserver = oxygenReserver;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest)
            throws OutOfSaleDatesException, OutOfFestivalDatesException {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        orderUtilities(orderDate, passOrder);
        passOrderRepository.save(passOrder);

        return passOrder;
    }

    private void orderUtilities(OffsetDateTime orderDate, PassOrder passOrder) {
        for (Pass pass : passOrder.getPasses()) {
            pass.reserveShuttles(transportReservation, transportRepository);
            pass.orderOxygen(orderDate.toLocalDate(), oxygenReserver, oxygenInventoryRepository, oxygenHistoryRepository);
        }
    }
}
