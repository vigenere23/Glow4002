package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.OffsetDateTime;

public class OxygenProducer {

    private OffsetDateTime limitDeliveryDate;   

    public OxygenProducer(OffsetDateTime limitDeliveryDate) {
        this.limitDeliveryDate = limitDeliveryDate;
    }

    public void produceOxygen(OxygenGrade gradeToProduce, int quantityToProduce, OxygenProductionResults results) {
        OxygenGrade realGradeToProduce = getRealGradeToProduce(results.orderDateHistory.date, gradeToProduce);
        produceGrade(quantityToProduce, results, realGradeToProduce);            
    }

    public OffsetDateTime getNextAvailableDeliveryDate(OffsetDateTime orderDate, OxygenGrade grade) {
        OxygenGrade realGradeToProduce = getRealGradeToProduce(orderDate, grade);
        return getFabricationCompletionDate(orderDate, realGradeToProduce);
    }

    public int calculateTotalToProduce(int quantity, int remainingQuantity) {
        return quantity - remainingQuantity;
    }

    private void produceGrade(int quantityToProduce, OxygenProductionResults results, OxygenGrade grade) {           
        int minimumFabricationQuantity = ProductionSettings.minimumFabricationQuantity.get(grade);     
        int productionBatchCount = calculateProductionBatchCount(quantityToProduce, minimumFabricationQuantity);  

        results.gradeProduced = grade;  
        results.deliveryDateHistory.qtyOxygenTankMade = quantityToProduce;
        results.quantityTankToAddToInventory = productionBatchCount * minimumFabricationQuantity;
        results.quantityTankRemaining = quantityToProduce < minimumFabricationQuantity ? minimumFabricationQuantity - quantityToProduce :
         ((productionBatchCount * minimumFabricationQuantity) - quantityToProduce);
  
        if(grade.equals(OxygenGrade.A)) {
            int candleProductionNeedPerBatch = ProductionSettings.candleProductionNeed.get(grade);
            results.orderDateHistory.qtyCandlesUsed = productionBatchCount * candleProductionNeedPerBatch; 
        }

        if(grade.equals(OxygenGrade.B)) {
            int waterUsedPerBatch = ProductionSettings.waterProductionNeed.get(grade);
            results.orderDateHistory.qtyWaterUsed = productionBatchCount * waterUsedPerBatch; 
        }
          
        if(grade.equals(OxygenGrade.E)) {
            results.orderDateHistory.qtyOxygenTankBought = quantityToProduce;
        }      
    }

    private int calculateProductionBatchCount(int quantityToProduce, int fabricationQuantity) {
        int overQuantity = quantityToProduce % fabricationQuantity;
        if(quantityToProduce < fabricationQuantity ) return 1;
        return overQuantity == 0 ? quantityToProduce / fabricationQuantity :
            ((quantityToProduce - overQuantity) / fabricationQuantity) + 1 ;
    }

    private OxygenGrade getRealGradeToProduce(OffsetDateTime orderDate, OxygenGrade grade) {
        OxygenGrade realGradeToProduce = grade;
        while (!enoughTimeForFabrication(orderDate, realGradeToProduce)) {
            realGradeToProduce = getLowerGradeOf(realGradeToProduce);
        }
        return realGradeToProduce;
    }

    private boolean enoughTimeForFabrication(OffsetDateTime orderDate, OxygenGrade grade) {       
        OffsetDateTime fabricationCompletionDate = getFabricationCompletionDate(orderDate, grade);
        return fabricationCompletionDate.isAfter(limitDeliveryDate) ? false : true;
    }
 
    private OffsetDateTime getFabricationCompletionDate(OffsetDateTime orderDate, OxygenGrade grade) {
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
