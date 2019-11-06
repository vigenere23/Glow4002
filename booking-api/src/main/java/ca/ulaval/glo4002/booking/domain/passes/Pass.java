package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.Price;

import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
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

    public Pass(FestivalDates festivalDates, PassOption passOption, PassCategory passCategory, Price price, LocalDate startDate, LocalDate endDate) {
        festivalDates.validateEventDates(startDate, endDate);

        this.passNumber = new PassNumber();
        this.passOption = passOption;
        this.passCategory = passCategory;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        shuttleCategory = PassCategoryMapper.getShuttleCategory(passCategory);
        oxygenGrade = PassCategoryMapper.getOxygenGrade(passCategory);
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

    public void reserveShuttles(TransportReservation transportReservation, ShuttleRepository shuttleRepository) {
        reserveDepartureShuttles(transportReservation, shuttleRepository);
        reserveArrivalShuttles(transportReservation, shuttleRepository);
    }

    private void reserveDepartureShuttles(TransportReservation transportReservation, ShuttleRepository shuttleRepository) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        departureShuttles = transportReservation.reserveDeparture(shuttleCategory, startDate, passNumber, departureShuttles);
        shuttleRepository.saveDeparture(departureShuttles);
    }

    private void reserveArrivalShuttles(TransportReservation transportReservation, ShuttleRepository shuttleRepository) {
        List<Shuttle> arrivalShuttles = shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY);
        arrivalShuttles = transportReservation.reserveArrival(shuttleCategory, startDate, passNumber, arrivalShuttles);
        shuttleRepository.saveArrival(arrivalShuttles);
    }

    public void orderOxygen(LocalDate orderDate, OxygenRequester oxygenRequester) {
        // TODO modify in issue #112
        int oxygenQuantityPerDay = PassCategoryMapper.getOxygenQuantity(passCategory);
        int numberOfDays = DateCalculator.daysBetween(startDate, endDate);

        oxygenRequester.orderOxygen(orderDate, oxygenGrade, oxygenQuantityPerDay * numberOfDays);
    }
}
