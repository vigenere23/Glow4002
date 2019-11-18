package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;

public class OxygenSupplierFactoryTest {

    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    @BeforeEach
    public void setup() {
        oxygenInventory = mock(OxygenInventory.class);
        oxygenHistory = mock(OxygenHistory.class);
        profitCalculator = mock(ProfitCalculator.class);
        oxygenSupplierFactory = new OxygenSupplierFactory(oxygenInventory, oxygenHistory, profitCalculator);
    }

    @Test
    public void givenOxygenGradeA_whenCreating_itReturnsOxygenGradeAProducer() {
        OxygenSupplier createdSupplier = oxygenSupplierFactory.create(OxygenGrade.A);
        assertThat(createdSupplier).isInstanceOf(OxygenGradeAProducer.class);
    }

    @Test
    public void givenOxygenGradeB_whenCreating_itReturnsOxygenGradeBProducer() {
        OxygenSupplier createdSupplier = oxygenSupplierFactory.create(OxygenGrade.B);
        assertThat(createdSupplier).isInstanceOf(OxygenGradeBProducer.class);
    }

    @Test
    public void givenOxygenGradeE_whenCreating_itReturnsOxygenGradeEBuyer() {
        OxygenSupplier createdSupplier = oxygenSupplierFactory.create(OxygenGrade.E);
        assertThat(createdSupplier).isInstanceOf(OxygenGradeEBuyer.class);
    }
}
