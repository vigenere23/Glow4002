package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumberFactory;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class PassFactory {

    private FestivalDates festivalDates;
    private PassNumberFactory passNumberFactory;
    private PassPriceFactory passPriceFactory;

    @Inject
    public PassFactory(FestivalDates festivalDates, PassNumberFactory passNumberFactory, PassPriceFactory passPriceFactory) {
        this.festivalDates = festivalDates;
        this.passNumberFactory = passNumberFactory;
        this.passPriceFactory = passPriceFactory;
    }

    public Pass create(PassOption passOption, PassCategory passCategory, Optional<LocalDate> eventDate) {
        switch (passOption) {
        case SINGLE_PASS:
            validateEventDatePresence(eventDate);
            return createSinglePass(passCategory, eventDate.get());
        case PACKAGE:
            validateEventDateAbsence(eventDate);
            return createPackagePass(passCategory);
        default:
            throw new IllegalArgumentException(String.format("No pass exists for option %s", passOption.toString()));
        }
    }

    private void validateEventDatePresence(Optional<LocalDate> eventDate) {
        if (!eventDate.isPresent()) {
            throw new IllegalArgumentException("An event date is required for single pass creation");
        }
    }

    private void validateEventDateAbsence(Optional<LocalDate> eventDate) {
        if (eventDate.isPresent()) {
            throw new IllegalArgumentException("No event date must be specified for package pass creation");
        }
    }

    private Pass createSinglePass(PassCategory passCategory, LocalDate eventDate) {
        PassNumber passNumber = passNumberFactory.create();
        PassengerNumber passengerNumber = new PassengerNumber(passNumber);
        Price price = passPriceFactory.create(PassOption.SINGLE_PASS, passCategory);
        return new Pass(festivalDates, passNumber, passengerNumber, PassOption.SINGLE_PASS, passCategory, price, eventDate);
    }

    private Pass createPackagePass(PassCategory passCategory) {
        PassNumber passNumber = passNumberFactory.create();
        PassengerNumber passengerNumber = new PassengerNumber(passNumber);
        Price price = passPriceFactory.create(PassOption.PACKAGE, passCategory);
        return new Pass(festivalDates, passNumber, passengerNumber, PassOption.PACKAGE, passCategory, price, festivalDates.getStartDate(), festivalDates.getEndDate());
    }
}
