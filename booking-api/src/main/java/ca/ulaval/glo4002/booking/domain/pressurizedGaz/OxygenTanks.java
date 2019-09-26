package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;

public class OxygenTanks {
    private int fabricationQuantity;
    private int fabricationTimeInDays;
    private int totalQuantity = 0;
    private int storageQuantity = 0;

    public OxygenTanks(int fabricationQuantity, int fabricationTimeInDays) {
	this.fabricationQuantity = fabricationQuantity;
	this.fabricationTimeInDays = fabricationTimeInDays;
    }

    public boolean isEnoughTimeForFabrication(LocalDate orderDate, LocalDate limitDeliveryDate) {
	LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
	if (fabricationCompletionDate.isAfter(limitDeliveryDate)) {
	    return false;
	}
	return true;
    }

    public void adjustInventory(int requirementQuantity) {
	int quantityOfTanksLacking = getQuantityOfTanksLacking(requirementQuantity);
	int quantityToFabricate = getQuantityToFabricate(quantityOfTanksLacking);
	storageQuantity = quantityToFabricate - quantityOfTanksLacking;
	totalQuantity += quantityToFabricate;
    }

    private int getQuantityOfTanksLacking(int requirementQuantity) {
	return requirementQuantity - storageQuantity;
    }

    private int getQuantityToFabricate(int quantityOfTanksLacking) {
	return getNumberOfFabricationBatchesNeeded(quantityOfTanksLacking) * fabricationQuantity;
    }

    private int getNumberOfFabricationBatchesNeeded(int quantityLacking) {
	return (int) Math.ceil((double) quantityLacking / fabricationQuantity);
    }

    public int getTotalQuantity() {
	return totalQuantity;
    }
}
