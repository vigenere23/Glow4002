package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.util.List;

public abstract class OxygenResource {

    public abstract List<InventoryDto> getInventory();
    public abstract List<HistoryDto> getOxygenHistory();
}