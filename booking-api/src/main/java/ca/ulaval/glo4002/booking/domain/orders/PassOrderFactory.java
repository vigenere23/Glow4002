package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.factories.PassFactory;

public class PassOrderFactory {

    private PassFactory passFactory;
    private Glow4002 festival;

    public PassOrderFactory(Glow4002 festival) {
        this.festival = festival;
        
        passFactory = new PassFactory(festival);
    }

    public PassOrder create(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest) throws OutOfFestivalDatesException, OutOfSaleDatesException {
        validateOrderDate(orderDate);

        List<Pass> passes = createPasses(passRequest);
        PassOrder passOrder = new PassOrder(passes);
        return passOrder;
    }

    private List<Pass> createPasses(PassRequest passRequest) throws OutOfFestivalDatesException {
        validatePassRequest(passRequest);

        List<Pass> passes = new ArrayList<>();

        if (passRequest.eventDates == null || passRequest.eventDates.isEmpty()) {
            passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, null));
        } else {
            for (LocalDate eventDate : passRequest.eventDates) {
                passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, eventDate));
            }
        }
        return passes;
    }

    private void validateOrderDate(OffsetDateTime orderDate) throws OutOfSaleDatesException {
        if (!festival.isDuringSaleTime(orderDate)) {
            throw new OutOfSaleDatesException(festival.getSaleStartDate(), festival.getSaleEndDate());
        }
    }

    private void validatePassRequest(PassRequest passRequest) {
        if (passRequest == null) {
            throw new IllegalArgumentException("Passes cannot be null");
        }
    }
}
