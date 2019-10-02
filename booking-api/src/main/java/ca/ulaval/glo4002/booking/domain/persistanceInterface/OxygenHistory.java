package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.time.OffsetDateTime;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.HistoryDto;

public interface OxygenHistory {

    public HashMap<OffsetDateTime, HistoryDto> getCreationHistory();
    public void updateCreationHistory(OffsetDateTime date, HistoryDto history);
    public HistoryDto getCreationHistoryPerDate(OffsetDateTime date);   
}