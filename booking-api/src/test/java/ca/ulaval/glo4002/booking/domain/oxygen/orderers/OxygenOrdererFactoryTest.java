package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

@ExtendWith(MockitoExtension.class)
public class OxygenOrdererFactoryTest {

    private OxygenOrdererLinker oxygenOrdererLinker;
    
    @Mock OxygenDates someOxygenDates;
    @Mock OxygenSupplierFactory oxygenSupplierFactory;
    @Mock HeapOxygenInventoryRepository oxygenInventory;
    @Mock OxygenRequestSettingsFactory requestSettingsFactory;
    private OxygenOrdererFactory oxygenOrdererFactory;

    @BeforeEach
    public void setup() {
        oxygenOrdererLinker = new OxygenOrdererLinker();
        requestSettingsFactory = new OxygenRequestSettingsFactory();
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
