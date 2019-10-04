package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.History;

public interface OxygenHistory {

    public HashMap<OffsetDateTime, History> getCreationHistory();
    public void updateCreationHistory(OffsetDateTime date, History history);
    public History getCreationHistoryPerDate(OffsetDateTime date);   
}
