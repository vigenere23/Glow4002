package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenResource;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class Glow4002 extends Festival {


    private final OxygenRequester oxygenRequester;

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

        this.oxygenRequester = new OxygenRequester(endDate, repository.getOxygenPersistance());     
        this.repository = repository;

        //To remove for pert purpose
        orderTemporaryOxygenToValidateIfItWorks();
    }

    public OxygenResource getOxygenRequester() {
        return this.oxygenRequester;
    }

    private void orderTemporaryOxygenToValidateIfItWorks() {
        oxygenRequester.orderOxygen(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), OxygenGrade.A, 6);
    }
}
