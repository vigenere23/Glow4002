package ca.ulaval.glo4002.booking.domain.oxygenService;

import ca.ulaval.glo4002.booking.domain.enumeration.PassCategory;

public interface GasNeedable {
    public void initialize(PassCategory passCategory);
}
