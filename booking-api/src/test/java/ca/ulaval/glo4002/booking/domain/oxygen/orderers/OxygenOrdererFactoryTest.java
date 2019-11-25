package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

public class OxygenOrdererFactoryTest {

    private OxygenDates someOxygenDates;
    private OxygenOrdererFactory oxygenOrdererFactory;
    private OxygenOrdererLinker oxygenOrdererLinker;
    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenRequestSettingsFactory requestSettingsFactory;
    private HeapOxygenInventoryRepository oxygenInventory;

    @BeforeEach
    public void setup() {
        someOxygenDates = mock(OxygenDates.class);
        oxygenOrdererLinker = new OxygenOrdererLinker();
        oxygenSupplierFactory = mock(OxygenSupplierFactory.class);
        requestSettingsFactory = new OxygenRequestSettingsFactory();
        oxygenInventory = mock(HeapOxygenInventoryRepository.class);
        oxygenOrdererFactory = new OxygenOrdererFactory(
            oxygenOrdererLinker, oxygenSupplierFactory, requestSettingsFactory, oxygenInventory
        );
    }

    @Test
    public void givenOxygenGradeA_whenCreating_itReturnsOrdererWithMinimumProducedGradeA() {
        OxygenGrade minimumOxygenGradeToOrder = OxygenGrade.A;
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(minimumOxygenGradeToOrder, someOxygenDates);
        assertThat(oxygenOrderer.getMinimumProducedGrade()).isEqualTo(minimumOxygenGradeToOrder);
    }

    @Test
    public void givenOxygenGradeB_whenCreating_itReturnsOrdererWithMinimumProducedGradeB() {
        OxygenGrade minimumOxygenGradeToOrder = OxygenGrade.B;
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(minimumOxygenGradeToOrder, someOxygenDates);
        assertThat(oxygenOrderer.getMinimumProducedGrade()).isEqualTo(minimumOxygenGradeToOrder);
    }

    @Test
    public void givenOxygenGradeE_whenCreating_itReturnsOrdererWithMinimumProducedGradeE() {
        OxygenGrade minimumOxygenGradeToOrder = OxygenGrade.E;
        OxygenOrderer oxygenOrderer = oxygenOrdererFactory.create(minimumOxygenGradeToOrder, someOxygenDates);
        assertThat(oxygenOrderer.getMinimumProducedGrade()).isEqualTo(minimumOxygenGradeToOrder);
    }
}
