package ca.ulaval.glo4002.booking.domain.persistanceInterface;

import java.time.LocalDate;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.oxygen.History;

public interface OxygenHistory {

    public HashMap<LocalDate, History> getCreationHistory();
    public void updateCreationHistory(LocalDate date, History history);
    public History getCreationHistoryPerDate(LocalDate date);   
}
