package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.SortedMap;

import ca.ulaval.glo4002.booking.domain.Price;

import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.helpers.DateCalculator;

public class Pass {

    private PassNumber passNumber;
    private Price price;
    private PassOption passOption;
    private PassCategory passCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private ShuttleCategory shuttleCategory;
    private OxygenGrade oxygenGrade;
    int oxygenQuantityPerDay;

    public Pass(FestivalDates festivalDates, PassOption passOption, PassCategory passCategory, Price price, LocalDate startDate, LocalDate endDate) {
        festivalDates.validateEventDates(startDate, endDate);

        this.passOption = passOption;
        this.passCategory = passCategory;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        
        passNumber = new PassNumber();
        shuttleCategory = PassCategoryMapper.getShuttleCategory(passCategory);
        oxygenGrade = PassCategoryMapper.getOxygenGrade(passCategory);
        oxygenQuantityPerDay = PassCategoryMapper.getOxygenQuantity(passCategory);
    }

    public boolean isOfType(PassOption passOption, PassCategory passCategory) {
        return this.passOption == passOption && this.passCategory == passCategory;
    }
    
    public Price getPrice() {
        return price;
    }

    public PassNumber getPassNumber() {
        return passNumber;
    }

    public PassOption getPassOption() {
        return passOption;
    }

    public PassCategory getPassCategory() {
        return passCategory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void reserveShuttles(TransportReserver transportReserver, ShuttleRepository shuttleRepository) {
        transportReserver.reserveDeparture(shuttleCategory, startDate, passNumber);
        reserveArrivalShuttles(transportReserver, shuttleRepository);
    }

    private void reserveArrivalShuttles(TransportReserver transportReserver, ShuttleRepository shuttleRepository) {
        List<Shuttle> arrivalShuttles = shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY);
        arrivalShuttles = transportReserver.reserveArrival(shuttleCategory, startDate, passNumber, arrivalShuttles);
        shuttleRepository.saveArrival(arrivalShuttles);
    }

    public void orderOxygen(LocalDate orderDate, OxygenProducer oxygenProducer, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        EnumMap<OxygenGrade, OxygenInventory> inventories = oxygenInventoryRepository.findInventories();
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenHistoryRepository.findOxygenHistory();

        int numberOfDays = DateCalculator.daysBetween(startDate, endDate);
        oxygenProducer.orderOxygen(orderDate, oxygenGrade, oxygenQuantityPerDay * numberOfDays, inventories, history);

        oxygenInventoryRepository.saveOxygenInventories(inventories);
        oxygenHistoryRepository.saveOxygenHistory(history);
    }
}
