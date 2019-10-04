package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;

public class Glow4002 {

    private OxygenRequester oxygenRequester;

    private final OffsetDateTime startDate;
    private final OffsetDateTime endDate;
    private final OffsetDateTime saleStartDate;
    private final OffsetDateTime saleEndDate;

    public Glow4002(Repository repository) {
        startDate = OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        endDate = OffsetDateTime.of(LocalDate.of(2050, 7, 24), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
        saleStartDate = OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC);
        saleEndDate = OffsetDateTime.of(LocalDate.of(2050, 7, 16), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);

        Objects.requireNonNull(repository, "repository");
    }

    public OffsetDateTime getStartDate() {
        return this.startDate;
    }

    public OffsetDateTime getEndDate() {
        return this.endDate;
    }

    public OffsetDateTime getSaleStartDate() {
        return this.saleStartDate;
    }

    public OffsetDateTime getSaleEndDate() {
        return this.saleEndDate;
    }

    public void setOxygenRequester(OxygenRequester oxygenRequester) {
        this.oxygenRequester = oxygenRequester;
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
