package ca.ulaval.glo4002.booking.application.order;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.SortedMap;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class PassOrderUseCase {

    private final TransportRequester transportRequester;
    private final OxygenProducer oxygenProducer;
    private final PassOrderFactory passOrderFactory;
    private PassOrderRepository passOrderRepository;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    public PassOrderUseCase(TransportRequester transportRequester, OxygenProducer oxygenProducer, PassOrderFactory passOrderCreator, PassOrderRepository passOrderRepository, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        this.transportRequester = transportRequester;
        this.oxygenProducer = oxygenProducer;
        this.passOrderFactory = passOrderCreator;
        this.passOrderRepository = passOrderRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
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

    public Optional<PassOrder> getOrder(OrderNumber orderNumber) {
        return passOrderRepository.findByOrderNumber(orderNumber);
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

        orderOxygen(orderDate.toLocalDate(), oxygenGrade, oxygenQuantityPerDay * numberOfDays);
    }

    private void orderOxygen(LocalDate orderDate, OxygenGrade oxygenGrade, int requiredQuantity) {
        int totalQuantity = oxygenInventoryRepository.findInventoryOfGrade(oxygenGrade);
        int remainingQuantity = oxygenInventoryRepository.findOxygenRemaining(oxygenGrade);
        SortedMap<LocalDate, OxygenHistory> oxygenHistory = oxygenHistoryRepository.findOxygenHistory();

        try {
            Oxygen oxygen = oxygenProducer.orderOxygen(orderDate, oxygenGrade, requiredQuantity, totalQuantity, remainingQuantity, oxygenHistory);
            saveOxygenRepositories(oxygenGrade, oxygen, oxygenHistory);
        } catch (NotEnoughTimeException exception) {
            oxygenInventoryRepository.saveOxygenRemaining(oxygenGrade, 0);
            orderOxygen(orderDate, getLowerGradeOf(oxygenGrade), requiredQuantity - remainingQuantity);
        }
    }

    private void saveOxygenRepositories(OxygenGrade oxygenGrade, Oxygen oxygen, SortedMap<LocalDate, OxygenHistory> oxygenHistory) {
        oxygenInventoryRepository.saveOxygenInventory(oxygenGrade, oxygen.getTotalQuantity());
        oxygenInventoryRepository.saveOxygenRemaining(oxygenGrade, oxygen.getRemainingQuantity());
        oxygenHistoryRepository.saveOxygenHistory(oxygenHistory);
    }

    private OxygenGrade getLowerGradeOf(OxygenGrade grade) {
        switch (grade) {
            case A:
                return OxygenGrade.B;
            case B:
                return OxygenGrade.E;
            default:
                throw new IllegalArgumentException(String.format("No lower oxygen grade exists for grade %s.", grade));
        }
    }
}
