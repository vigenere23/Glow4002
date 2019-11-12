package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumberFactory;

public class PassFactory {

    private FestivalDates festivalDates;
    private PassNumberFactory passNumberFactory;
    private PassPriceFactory passPriceFactory;

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
        Price price = passPriceFactory.create(PassOption.SINGLE_PASS, passCategory);
        return new Pass(festivalDates, passNumber, PassOption.SINGLE_PASS, passCategory, price, eventDate, eventDate);
    }

    private Pass createPackagePass(PassCategory passCategory) {
        PassNumber passNumber = passNumberFactory.create();
        Price price = passPriceFactory.create(PassOption.PACKAGE, passCategory);
        return new Pass(festivalDates, passNumber, PassOption.PACKAGE, passCategory, price, festivalDates.getStartDate(), festivalDates.getEndDate());
    }
}
