package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import java.time.LocalDate;

public interface OxygenSupplier {
    public void order(LocalDate orderDate, int minQuantityToProduce);
}
