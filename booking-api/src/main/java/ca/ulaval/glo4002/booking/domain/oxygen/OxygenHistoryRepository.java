package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.HashMap;

import ca.ulaval.glo4002.booking.domain.oxygen.History;

public interface OxygenHistoryRepository {

    public HashMap<LocalDate, History> findCreationHistory();
    public void saveCreationHistory(LocalDate date, History history);
    public History findCreationHistoryPerDate(LocalDate date);   
}
