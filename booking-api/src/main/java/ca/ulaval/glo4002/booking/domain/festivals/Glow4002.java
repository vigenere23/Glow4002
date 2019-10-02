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
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDto;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderCreator;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenResource;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class Glow4002 extends Festival {


    private PassOrderCreator passOrderCreator;
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

        this.passOrderCreator = new PassOrderCreator(repository, this.startDate, this.endDate);
        this.oxygenRequester = new OxygenRequester(endDate, repository.getOxygenPersistance());     
        this.repository = repository;

        //To remove for pert purpose
		  orderTemporaryOxygenToValidateIfItWorks();
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

 

    public OxygenResource getOxygenRequester() {
        return this.oxygenRequester;
    }

    private void orderTemporaryOxygenToValidateIfItWorks() {
        oxygenRequester.orderOxygen(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), OxygenGrade.A, 6);
    }
}
