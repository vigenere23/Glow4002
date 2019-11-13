package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.enumMaps.PassCategoryMapper;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.helpers.DateCalculator;

public class Pass {

    private static final int ONE_PLACE = 1;
    private PassNumber passNumber;
    private Price price;
    private PassOption passOption;
    private PassCategory passCategory;
    private LocalDate startDate;
    private LocalDate endDate;
    private ShuttleCategory shuttleCategory;
    private OxygenGrade oxygenGrade;
    private int oxygenQuantityPerDay;

    public Pass(FestivalDates festivalDates, PassOption passOption, PassCategory passCategory, Price price,
            LocalDate startDate, LocalDate endDate) {
        festivalDates.validateEventDate(startDate);
        festivalDates.validateEventDate(endDate);

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

    public boolean hasSameDateAs(LocalDate eventDate) {
        return this.startDate.equals(eventDate);
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

    public void reserveShuttles(TransportReserver transportReserver) {
        transportReserver.reserveDeparture(shuttleCategory, startDate, passNumber, ONE_PLACE);
        transportReserver.reserveArrival(shuttleCategory, endDate, passNumber, ONE_PLACE);
    }

    public void reserveOxygen(LocalDate orderDate, OxygenReserver oxygenReserver) {
        int requiredQuantity = calculateRequiredQuantity();
        oxygenReserver.reserveOxygen(orderDate, oxygenGrade, requiredQuantity);
    }

    private int calculateRequiredQuantity() {
        int numberOfDays = DateCalculator.daysBetween(startDate, endDate);
        return oxygenQuantityPerDay * numberOfDays;
    }
}
