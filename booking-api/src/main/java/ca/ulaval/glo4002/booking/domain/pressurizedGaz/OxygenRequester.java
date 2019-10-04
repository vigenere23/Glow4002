package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

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

    public OxygenRequester(OffsetDateTime limitDeliveryDate, OxygenPersistance oxygenPersistance) {
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

    public void orderOxygen(OffsetDateTime orderDate, OxygenGrade grade, int quantity) {
        int remainingQuantity = inventory.getOxygenRemaining(grade);

        if (hasToProduce(quantity, remainingQuantity)) {
            initializeResults(orderDate, grade);
            int totalToProduce = oxygenProducer.calculateTotalToProduce(quantity, remainingQuantity);
            inventory.setOxygenRemaining(grade, 0);

            OxygenGrade gradeProduced = results.gradeProduced;
            oxygenProducer.produceOxygen(gradeProduced, totalToProduce, results);

            updateInventory();
            updateHistory();
        } else {
            inventory.setOxygenRemaining(grade, remainingQuantity - quantity);
        }
    }

    private boolean hasToProduce(int quantityToProduce, int remainingQuantity) {
        return quantityToProduce > remainingQuantity;
    }

    private void initializeResults(OffsetDateTime orderDate, OxygenGrade grade) {
        results = new OxygenProductionResults();
        results.orderDateHistory = history.getCreationHistoryPerDate(orderDate);
        results.gradeProduced = oxygenProducer.getRealGradeToProduce(orderDate, grade);

        OffsetDateTime deliveryDate = oxygenProducer.getFabricationCompletionDate(orderDate, results.gradeProduced);
        results.deliveryDateHistory = history.getCreationHistoryPerDate(deliveryDate);
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
