package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;

public class OxygenRequester extends OxygenExposer {

    private OxygenProducer oxygenProducer;
    private OxygenProductionResults results;
    private OxygenInventory inventory;
    private OxygenHistory history;

    public OxygenRequester(LocalDate limitDeliveryDate, OxygenPersistance oxygenPersistance) {
        inventory = oxygenPersistance.getOxygenInventory();
        history = oxygenPersistance.getOxygenHistory();
        oxygenProducer = new OxygenProducer(limitDeliveryDate);
    }

    @Override
    public List<Inventory> getInventory() {
        return inventory.getCompleteInventory();
    }

    @Override
    public List<History> getOxygenHistory() {
        return new ArrayList<>(history.getCreationHistory().values());
    }

    public void orderOxygen(LocalDate orderDate, OxygenGrade grade, int quantity) {
        int remainingQuantity = inventory.getOxygenRemaining(grade);

        if (hasToProduce(quantity, remainingQuantity)) {
            initializeResults(orderDate, grade);
            inventory.setOxygenRemaining(grade, 0);

            int totalToProduce = oxygenProducer.calculateTotalToProduce(quantity, remainingQuantity);           
            oxygenProducer.produceOxygen(grade, totalToProduce, results);

            updateInventory();
            updateHistory();
        } else {
            inventory.setOxygenRemaining(grade, remainingQuantity - quantity);
        }
    }

    private boolean hasToProduce(int quantityToProduce, int remainingQuantity) {
        return quantityToProduce > remainingQuantity;
    }

    private void initializeResults(LocalDate orderDate, OxygenGrade grade) {
        results = new OxygenProductionResults();
        History orderDateHistory = history.getCreationHistoryPerDate(orderDate);
        results.orderDateHistory = orderDateHistory;

        LocalDate deliveryDate = oxygenProducer.getNextAvailableDeliveryDate(orderDate, grade);
        History deliveryDateHistory = history.getCreationHistoryPerDate(deliveryDate);
        results.deliveryDateHistory = deliveryDateHistory;
    }

    private void updateHistory() {
        history.updateCreationHistory(results.orderDateHistory.date, results.orderDateHistory);
        history.updateCreationHistory(results.deliveryDateHistory.date, results.deliveryDateHistory);
    }

    private void updateInventory() {
        OxygenGrade gradeProduced = results.gradeProduced;
        inventory.setOxygenInventory(gradeProduced, inventory.getInventoryOfGrade(gradeProduced) + results.quantityTankToAddToInventory);
        inventory.setOxygenRemaining(gradeProduced, inventory.getOxygenRemaining(gradeProduced) + results.quantityTankRemaining);
    }
}
