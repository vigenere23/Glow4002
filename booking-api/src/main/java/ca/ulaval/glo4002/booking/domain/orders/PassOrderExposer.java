package ca.ulaval.glo4002.booking.domain.orders;

import java.time.OffsetDateTime;
import java.util.Optional;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;

public interface PassOrderExposer {

    public Optional<PassOrder> getOrder(Long id);
    public PassOrder orderPasses(OffsetDateTime orderDate, String vendorCode, PassRequest passRequest) throws OutOfSaleDatesException, OutOfFestivalDatesException;
}
