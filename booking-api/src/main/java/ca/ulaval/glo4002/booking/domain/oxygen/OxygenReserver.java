package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenReserver {

    private OxygenOrderFactory oxygenOrderFactory;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OutcomeSaver outcomeSaver;

    public OxygenReserver(OxygenOrderFactory oxygenOrderFactory, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository, OutcomeSaver outcomeSaver) {
        this.oxygenOrderFactory = oxygenOrderFactory;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
        this.outcomeSaver = outcomeSaver;
    }

    public void reserveOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity) {
        OxygenInventory oxygenInventory = oxygenInventoryRepository.findByGrade(grade);
        OxygenStatus oxygenStatus = orderOxygen(orderDate, grade, requiredQuantity, oxygenInventory);
        saveOxygenStatus(oxygenStatus);
    }

    private OxygenStatus orderOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity, OxygenInventory oxygenInventory) {
        int quantityRemaining = oxygenInventory.getRemainingQuantity();
        int quantityToOrder = requiredQuantity > quantityRemaining ? requiredQuantity - oxygenInventory.getRemainingQuantity() : 0;

        if (quantityToOrder > 0) {
            oxygenInventory.setRemainingQuantity(0);
            OxygenOrder oxygenOrder = oxygenOrderFactory.create(grade);
            OxygenStatus oxygenStatus = getNewOxygenStatus(orderDate, grade, quantityToOrder, oxygenInventory, oxygenOrder);
            addOxygenOrderCostToOutcome(oxygenOrder);
            return oxygenStatus;
        }

        oxygenInventory.setRemainingQuantity(quantityRemaining - requiredQuantity);
        return new OxygenStatus(oxygenInventory);
    }

    private void addOxygenOrderCostToOutcome(OxygenOrder oxygenOrder) {
        oxygenOrder.saveOutcome(outcomeSaver);
    }

    private OxygenStatus getNewOxygenStatus(LocalDate orderDate, OxygenGrade grade, int quantityToOrder, OxygenInventory oxygenInventory, OxygenOrder oxygenOrder) {
        if (!oxygenOrder.isEnoughTimeToFabricate(orderDate)) {
            reserveOxygen(orderDate, getLowerGradeOf(grade), quantityToOrder);
        }

        updateOxygenInventory(oxygenInventory, oxygenOrder, orderDate, quantityToOrder);
        SortedMap<LocalDate, OxygenHistoryItem> oxygenHistory = getOxygenHistory(oxygenOrder, orderDate);

        return new OxygenStatus(oxygenInventory, oxygenHistory);
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

    private void updateOxygenInventory(OxygenInventory oxygenInventory, OxygenOrder oxygenOrder, LocalDate orderDate, int quantityToOrder) {
        int quantityToReserve = oxygenOrder.getQuantityToReserve(orderDate, quantityToOrder);
        oxygenInventory.updateInventory(quantityToReserve);
    }

    private SortedMap<LocalDate, OxygenHistoryItem> getOxygenHistory(OxygenOrder oxygenOrder, LocalDate orderDate) {
        SortedMap<LocalDate, OxygenHistoryItem> orderHistory = oxygenOrder.getOxygenOrderHistory(orderDate);
        updateOrderHistoryWithCurrentHistory(orderHistory);

        return orderHistory;
    }

    private void updateOrderHistoryWithCurrentHistory(SortedMap<LocalDate, OxygenHistoryItem> orderHistory) {
        for (LocalDate date: orderHistory.keySet()) {
            OxygenHistoryItem currentHistoryItem = oxygenHistoryRepository.findOxygenHistoryOfDate(date);
            orderHistory.get(date).updateQuantities(currentHistoryItem);
        }
    }

    private void saveOxygenStatus(OxygenStatus oxygenStatus) {
        oxygenInventoryRepository.save(oxygenStatus.getOxygenInventory());

        SortedMap<LocalDate, OxygenHistoryItem> orderHistory = oxygenStatus.getOrderHistory();
        for (LocalDate date: orderHistory.keySet()) {
            oxygenHistoryRepository.save(orderHistory.get(date));
        }
    }
}
