package ca.ulaval.glo4002.booking.domain.oxygen2.history;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OxygenHistory {

    private Map<LocalDate, OxygenHistoryEntry> history;

    public OxygenHistory() {
        history = new HashMap<>();
    }

    public void addCandlesUsed(LocalDate date, int numberOfCandles) {
        OxygenHistoryEntry oxygenHistoryEntry = findOrCreate(date);
        oxygenHistoryEntry.addCandlesUsed(numberOfCandles);
        save(oxygenHistoryEntry);
    }

    public void addWaterUsed(LocalDate date, int litersOfWater) {
        OxygenHistoryEntry oxygenHistoryEntry = findOrCreate(date);
        oxygenHistoryEntry.addWaterUsed(litersOfWater);
        save(oxygenHistoryEntry);
    }

    public void addTankMade(LocalDate date, int numberOfTankMade) {
        OxygenHistoryEntry oxygenHistoryEntry = findOrCreate(date);
        oxygenHistoryEntry.addTankMade(numberOfTankMade);
        save(oxygenHistoryEntry);
    }

    public void addTankBought(LocalDate date, int numberOfTankBought) {
        OxygenHistoryEntry oxygenHistoryEntry = findOrCreate(date);
        oxygenHistoryEntry.addTankBought(numberOfTankBought);
        save(oxygenHistoryEntry);
    }

    public List<OxygenHistoryEntry> findAll() {
        return new ArrayList<>(history.values());
    }

    public Optional<OxygenHistoryEntry> find(LocalDate date) {
        return Optional.ofNullable(history.get(date));
    }

    private OxygenHistoryEntry findOrCreate(LocalDate date) {
        Optional<OxygenHistoryEntry> entry = find(date);
        if (!entry.isPresent()) {
            return new OxygenHistoryEntry(date);
        }
        return entry.get();
    }

    private void save(OxygenHistoryEntry oxygenHistoryEntry) {
        history.put(oxygenHistoryEntry.getDate(), oxygenHistoryEntry);
    }
}
