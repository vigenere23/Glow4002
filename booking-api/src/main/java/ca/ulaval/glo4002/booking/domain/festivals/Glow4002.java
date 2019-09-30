package ca.ulaval.glo4002.booking.domain.festivals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Festival;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

public class Glow4002 extends Festival implements Glow4002Festival{

    //private TransportService transportService; TODO: create transport sevice here
    //private PassReserver: TODO: create pass order service here
    private OxygenRequester oxygenRequester;
    private OxygenPersistance oxygenPersistance;
    private Repository repository;


    public Glow4002(OffsetDateTime startDate, OffsetDateTime endDate, OffsetDateTime saleStartDate, OffsetDateTime saleEndDate, Repository repository) {
        super(startDate, endDate, saleStartDate, saleEndDate);
        Objects.requireNonNull(repository, "repository");

        oxygenRequester = new OxygenRequester(endDate.toLocalDate());       
        this.repository = repository;
        this.oxygenPersistance = repository.getOxygenPersistance();

        //todo a enlever les 3 lignes ci-dessous just for test
        orderOxygenQuantityOfGradeA(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 5);
        orderOxygenQuantityOfGradeE(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 10);
        orderOxygenQuantityOfGradeB(OffsetDateTime.of(LocalDate.of(2050, 2, 17), LocalTime.now(), ZoneOffset.UTC), 2);
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
