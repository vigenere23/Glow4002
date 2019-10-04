package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.OffsetDateTime;

public class OxygenProducer {

    private OffsetDateTime limitDeliveryDate;

    public OxygenProducer(OffsetDateTime limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public int calculateTotalToProduce(int quantity, int remainingQuantity) {
        return quantity - remainingQuantity;
    }

    public void produceOxygen(OxygenGrade gradeToProduce, int quantityToProduce, OxygenProductionResults results) {
        int minimumFabricationQuantity = ProductionSettings.minimumFabricationQuantity.get(gradeToProduce);
        int productionBatchCount = calculateProductionBatchCount(quantityToProduce, minimumFabricationQuantity);
        int fabricationQuantity = productionBatchCount * minimumFabricationQuantity;

        results.deliveryDateHistory.qtyOxygenTankMade = fabricationQuantity;
        results.quantityTankToAddToInventory = fabricationQuantity;
        results.quantityTankRemaining = fabricationQuantity - quantityToProduce;

        updateHistoryOfResults(quantityToProduce, productionBatchCount, results);
    }

    private int calculateProductionBatchCount(int quantityToProduce, int fabricationQuantity) {
        int overQuantity = quantityToProduce % fabricationQuantity;
        if(quantityToProduce < fabricationQuantity ) return 1;
        return overQuantity == 0 ? quantityToProduce / fabricationQuantity :
                ((quantityToProduce - overQuantity) / fabricationQuantity) + 1 ;
    }

    private void updateHistoryOfResults(int quantityToProduce, int productionBatchCount, OxygenProductionResults results) {
        OxygenGrade gradeToProduce = results.gradeProduced;
        if(gradeToProduce.equals(OxygenGrade.A)) {
            int candleProductionNeedPerBatch = ProductionSettings.candleProductionNeed.get(gradeToProduce);
            results.orderDateHistory.qtyCandlesUsed = productionBatchCount * candleProductionNeedPerBatch;
        }
        if(gradeToProduce.equals(OxygenGrade.B)) {
            int waterUsedPerBatch = ProductionSettings.waterProductionNeed.get(gradeToProduce);
            results.orderDateHistory.qtyWaterUsed = productionBatchCount * waterUsedPerBatch;
        }

        if(gradeToProduce.equals(OxygenGrade.E)) {
            results.orderDateHistory.qtyOxygenTankBought = quantityToProduce;
        }
    }

    public OxygenGrade getRealGradeToProduce(OffsetDateTime orderDate, OxygenGrade grade) {
        OxygenGrade realGradeToProduce = grade;
        while (!enoughTimeForFabrication(orderDate, realGradeToProduce)) {
            realGradeToProduce = getLowerGradeOf(realGradeToProduce);
        }
        return realGradeToProduce;
    }

    private boolean enoughTimeForFabrication(OffsetDateTime orderDate, OxygenGrade grade) {
        OffsetDateTime fabricationCompletionDate = getFabricationCompletionDate(orderDate, grade);
        return !fabricationCompletionDate.isAfter(limitDeliveryDate);
    }

    public OffsetDateTime getFabricationCompletionDate(OffsetDateTime orderDate, OxygenGrade grade) {
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