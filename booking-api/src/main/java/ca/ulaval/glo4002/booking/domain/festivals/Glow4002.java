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
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDTO;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Festival;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrderCreator;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class Glow4002 extends Festival implements Glow4002Festival{

    //private TransportService transportService; TODO: create transport sevice here
    private PassOrderCreator passOrderCreator;
    private OxygenRequester oxygenRequester;
    private OxygenPersistance oxygenPersistance; // TODO remove


    public Glow4002(Repository repository) {
        super(
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 25), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC),
            OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC)
        );

        Objects.requireNonNull(repository, "repository");

        this.passOrderCreator = new PassOrderCreator(repository, this.startDate, this.endDate);
        this.oxygenRequester = new OxygenRequester(endDate.toLocalDate());       
        this.oxygenPersistance = repository.getOxygenPersistance();

        // TODO a enlever les 3 lignes ci-dessous just for test
        orderOxygenQuantityOfGradeA(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 5);
        orderOxygenQuantityOfGradeE(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 10);
        orderOxygenQuantityOfGradeB(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 2);
    }

    public PassOrder reservePasses(OffsetDateTime orderDate, String vendorCode, List<PassDTO> passDTOs) throws Exception {
        validateOrderDate(orderDate);

        return this.passOrderCreator.orderPasses(orderDate, vendorCode, passDTOs);
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

    @Override
    public int getGradeEOxygenInventory() {
        return oxygenPersistance.getOxygenInventory().getInventoryOfGrade(OxygenGrade.E);
    }

    @Override
    public int getGradeAOxygenInventory() {
        return oxygenPersistance.getOxygenInventory().getInventoryOfGrade(OxygenGrade.A);
    }

    @Override
    public int getGradeBOxygenInventory() {
        return oxygenPersistance.getOxygenInventory().getInventoryOfGrade(OxygenGrade.B);
    }

    private void orderOxygenQuantityOfGradeA(OffsetDateTime orderDate, int quantity) {
        oxygenRequester.orderTemplatedOxygenQuantity(orderDate.toLocalDate(), OxygenGrade.A);

        //TODO: mettre a jour la bd seulement si la cération a focntionner correctement
        oxygenPersistance.getOxygenInventory().addOxygenToInventory(OxygenGrade.A, quantity);      
    }

    private void orderOxygenQuantityOfGradeE(OffsetDateTime orderDate, int quantity) {
        oxygenRequester.orderTemplatedOxygenQuantity(orderDate.toLocalDate(), OxygenGrade.E);

         //TODO: mettre a jour la bd seulement si la cération a focntionner correctement
         oxygenPersistance.getOxygenInventory().addOxygenToInventory(OxygenGrade.E, quantity);   
    }

    private void orderOxygenQuantityOfGradeB(OffsetDateTime orderDate, int quantity) {
        oxygenRequester.orderTemplatedOxygenQuantity(orderDate.toLocalDate(), OxygenGrade.B);

         //TODO: mettre a jour la bd seulement si la cération a focntionner correctement
         oxygenPersistance.getOxygenInventory().addOxygenToInventory(OxygenGrade.B, quantity);   
    }
}
