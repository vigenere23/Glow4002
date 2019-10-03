package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.History;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.Inventory;

public abstract class OxygenExposer {

    public abstract List<Inventory> getInventory();
    public abstract List<History> getOxygenHistory();
}