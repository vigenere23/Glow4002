package ca.ulaval.glo4002.booking.application;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class PassOrderUseCase {

    private PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;
    private TransportReserver transportReserver;
    private ShuttleRepository transportRepository;
    private OxygenProducer oxygenProducer;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    public PassOrderUseCase(
            PassOrderFactory passFactory, PassOrderRepository passOrderRepository, TransportReserver transportReserver,
            ShuttleRepository transportRepository, OxygenProducer oxygenProducer, OxygenInventoryRepository oxygenInventoryRepository,
            OxygenHistoryRepository oxygenHistoryRepository) {
        this.passOrderFactory = passFactory;
        this.passOrderRepository = passOrderRepository;
        this.transportReserver = transportReserver;
        this.transportRepository = transportRepository;
        this.oxygenProducer = oxygenProducer;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
    }

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
    }

    public PassOrder orchestPassCreation(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        PassOrder passOrder = passOrderFactory.create(orderDate, vendorCode, passRequest);
        orderUtilities(orderDate, passOrder);
        passOrderRepository.save(passOrder);

        return passOrder;
    }

    private void orderUtilities(OffsetDateTime orderDate, PassOrder passOrder) {
        for (Pass pass : passOrder.getPasses()) {
            pass.reserveShuttles(transportReserver, transportRepository);
            pass.orderOxygen(orderDate.toLocalDate(), oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);
        }
    }
}
