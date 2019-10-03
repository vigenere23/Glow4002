package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderService;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos.PassRequest;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordNotFoundException;

public class Glow4002 extends Festival {


    //private TransportService transportService; TODO: create transport sevice here
    private PassOrderService passOrderService;
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

        this.passOrderService = new PassOrderService(repository, this.startDate, this.endDate);
        this.repository = repository;
    }

    public PassOrder reservePasses(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests) {
        validateOrderDate(orderDate);

        return this.passOrderService.orderPasses(orderDate, vendorCode, passRequests);
    }

    public PassOrder getOrder(long id) throws RecordNotFoundException {
        return this.passOrderService.getOrder(id);
    }

    private void validateOrderDate(OffsetDateTime orderDate) {
        if (!isDuringSaleTime(orderDate)) {
            // TODO replace by specific exception
            // TODO message is not correct
            throw new IllegalArgumentException(
                String.format(
                    "order date should be between %s and %s",
                    this.saleStartDate.format(DateTimeFormatter.ofPattern("LL d u")),
                    this.saleEndDate.format(DateTimeFormatter.ofPattern("LL d u"))
                )
            );
        }
    }

    public void setOxygenRequester(OxygenRequester oxygenRequester) {
        this.oxygenRequester = oxygenRequester;
    }

    //remive this methos just for test purpose
    public void orderTemporaryOxygenToValidateIfItWorks() {
        oxygenRequester.orderOxygen(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), OxygenGrade.A, 6);
    }
}
