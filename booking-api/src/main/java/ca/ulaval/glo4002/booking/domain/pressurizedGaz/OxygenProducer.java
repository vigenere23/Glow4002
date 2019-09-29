package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;

public class OxygenProducer {
    private int fabricationQuantity;
    private int fabricationTimeInDays;
    private int totalQuantity = 0;
    private int storageQuantity = 0;
    private LocalDate limitDeliveryDate;

    public OxygenProducer(int fabricationQuantity, int fabricationTimeInDays, LocalDate limitDeliveryDate) {
	this.fabricationQuantity = fabricationQuantity;
	this.fabricationTimeInDays = fabricationTimeInDays;
	this.limitDeliveryDate = limitDeliveryDate;
    }

    public void adjustInventory(LocalDate orderDate, int requirementQuantity) throws NotEnoughTimeException {
	if (!enoughTimeForFabrication(orderDate)) {
	    throw new NotEnoughTimeException();
	}
	int quantityOfTanksLacking = requirementQuantity - storageQuantity;
	int quantityToFabricate = getQuantityToFabricate(quantityOfTanksLacking);
	storageQuantity = quantityToFabricate - quantityOfTanksLacking;
	totalQuantity += quantityToFabricate;
    }

    private boolean enoughTimeForFabrication(LocalDate orderDate) {
	LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
	if (fabricationCompletionDate.isAfter(limitDeliveryDate)) {
	    return false;
	}
	return true;
    }

    private int getQuantityToFabricate(int quantityOfTanksLacking) {
	return getNumberOfFabricationBatchesNeeded(quantityOfTanksLacking) * fabricationQuantity;
    }

    private int getNumberOfFabricationBatchesNeeded(int quantityLacking) {
	if (quantityLacking % fabricationQuantity > 0) {
	    return quantityLacking / fabricationQuantity + 1;
	}
	return quantityLacking / fabricationQuantity;
    }

    public int getTotalQuantity() {
	return totalQuantity;
    }
}
