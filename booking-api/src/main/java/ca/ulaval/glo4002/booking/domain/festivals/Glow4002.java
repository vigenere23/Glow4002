package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.TransportExposer;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDto;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderCreator;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class Glow4002 {

    private TransportExposer transportExposer;
    private PassOrderCreator passOrderCreator;
    private OxygenRequester oxygenRequester;

    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    private int dailyNeedOxygenGardeA = 3;
    private int dailyNeedOxygenGardeB = 3;
    private int dailyNeedOxygenGardeE = 5;


    public Glow4002(Repository repository) {
        startDate = OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        endDate = OffsetDateTime.of(LocalDate.of(2050, 7, 24), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
        saleStartDate = OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        saleEndDate = OffsetDateTime.of(LocalDate.of(2050, 7, 16), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);

        Objects.requireNonNull(repository, "repository");

        this.passOrderCreator = new PassOrderCreator(repository, this.startDate, this.endDate);
    }

    public OffsetDateTime getStartDate() {
        return this.startDate;
    }

    public OffsetDateTime getEndDate() {
        return this.endDate;
    }

    public void setOxygenRequester(OxygenRequester oxygenRequester) {
        this.oxygenRequester = oxygenRequester;
    }
    
    public void setTransportExposer(TransportExposer transportExposer) {
        this.transportExposer = transportExposer;
    }

    public PassOrder reservePasses(OffsetDateTime orderDate, String vendorCode, List<PassDto> passDtos) throws Exception {
        validateOrderDate(orderDate);

        return this.passOrderCreator.orderPasses(orderDate, vendorCode, passDtos);
    }

    private void validateOrderDate(OffsetDateTime orderDate) {
        if (!isDuringSaleTime(orderDate)) {
            // TODO replace by ApiException
            throw new IllegalArgumentException(
                String.format(
                    "order date should be between %s and %s",
                    this.saleStartDate.format(DateTimeFormatter.ofPattern("LL d u")),
                    this.saleEndDate.format(DateTimeFormatter.ofPattern("LL d u"))
                )
            );
        }
    }

    public boolean isDuringSaleTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.saleStartDate) && dateTime.isBefore(this.saleEndDate);
    }

    public boolean isDuringEventTime(OffsetDateTime dateTime) {
        return dateTime.isAfter(this.startDate) && dateTime.isBefore(this.endDate);
    }

    //remive this methos just for test purpose
    public void orderTemporaryOxygenToValidateIfItWorks() {
        oxygenRequester.orderOxygen(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), OxygenGrade.A, 6);
    }
}
