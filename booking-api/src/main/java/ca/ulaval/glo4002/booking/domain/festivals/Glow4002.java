package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;

public class Glow4002 extends Festival {

    //private TransportService transportService; TODO: create transport sevice here
    private OxygenRequester oxygenRequester;

    private Repository repository;
    private int dailyNeedOxygenGardeA = 3;
    private int dailyNeedOxygenGardeB = 3;
    private int dailyNeedOxygenGardeE = 5;

    public Glow4002(Repository repository) {
        super(
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 25), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC)
        );
        Objects.requireNonNull(repository, "repository");

        this.repository = repository;
    }

    public void setOxygenRequester(OxygenRequester oxygenRequester) {
        this.oxygenRequester = oxygenRequester;
    }

    //remive this methos just for test purpose
    public void orderTemporaryOxygenToValidateIfItWorks() {
        oxygenRequester.orderOxygen(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), OxygenGrade.A, 6);
    }
}
