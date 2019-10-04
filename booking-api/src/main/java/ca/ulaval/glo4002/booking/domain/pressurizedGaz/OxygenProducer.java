package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;

public class OxygenProducer {

    private LocalDate limitDeliveryDate;

    public OxygenProducer(LocalDate limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public int calculateTotalToProduce(int quantity, int remainingQuantity) {
        return quantity - remainingQuantity;
    }

    public void produceOxygen(OxygenGrade gradeToProduce, int quantityToProduce, OxygenProductionResults results) {
        int minimumFabricationQuantity = ProductionSettings.minimumFabricationQuantity.get(gradeToProduce);
        int productionBatchCount = calculateProductionBatchCount(quantityToProduce, minimumFabricationQuantity);
        int fabricationQuantity = productionBatchCount * minimumFabricationQuantity;

        results.gradeProduced = gradeToProduce;
        results.deliveryDateHistory.qtyOxygenTankMade += fabricationQuantity;
        results.quantityTankToAddToInventory = fabricationQuantity;
        results.quantityTankRemaining = fabricationQuantity - quantityToProduce;

        updateHistoryOfResults(quantityToProduce, productionBatchCount, results);
    }

    private int calculateProductionBatchCount(int quantityToProduce, int fabricationQuantity) {
        int overQuantity = quantityToProduce % fabricationQuantity;
        if (quantityToProduce < fabricationQuantity) return 1;
        return overQuantity == 0 ? quantityToProduce / fabricationQuantity :
                ((quantityToProduce - overQuantity) / fabricationQuantity) + 1;
    }

    private void updateHistoryOfResults(int quantityToProduce, int productionBatchCount, OxygenProductionResults results) {
        OxygenGrade gradeToProduce = results.gradeProduced;
        if (gradeToProduce.equals(OxygenGrade.A)) {
            int candleProductionNeedPerBatch = ProductionSettings.candleProductionNeed.get(gradeToProduce);
            results.orderDateHistory.qtyCandlesUsed += productionBatchCount * candleProductionNeedPerBatch;
        }
        if (gradeToProduce.equals(OxygenGrade.B)) {
            int waterUsedPerBatch = ProductionSettings.waterProductionNeed.get(gradeToProduce);
            results.orderDateHistory.qtyWaterUsed += productionBatchCount * waterUsedPerBatch;
        }

        if (gradeToProduce.equals(OxygenGrade.E)) {
            results.orderDateHistory.qtyOxygenTankBought += quantityToProduce;
        }
    }

    public OxygenGrade getNextGradeToProduce(LocalDate orderDate, OxygenGrade grade) {
        return enoughTimeForFabrication(orderDate, grade) ? grade : getLowerGradeOf(grade);
    }

    private boolean enoughTimeForFabrication(LocalDate orderDate, OxygenGrade grade) {
        LocalDate fabricationCompletionDate = getFabricationCompletionDate(orderDate, grade);
        return !fabricationCompletionDate.isAfter(limitDeliveryDate);
    }

    public LocalDate getFabricationCompletionDate(LocalDate orderDate, OxygenGrade grade) {
        long gradeFabricationDelay = ProductionSettings.fabricationTimeInDay.get(grade);
        return orderDate.plusDays(gradeFabricationDelay);
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