package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class OxygenSupplierFactoryTest {

    private OxygenSupplierFactory oxygenSupplierFactory;
    private HeapOxygenHistoryRepository oxygenHistory;
    private OutcomeSaver outcomeSaver;

    @BeforeEach
    public void setup() {
        oxygenHistory = mock(HeapOxygenHistoryRepository.class);
        outcomeSaver = mock(OutcomeSaver.class);
        oxygenSupplierFactory = new OxygenSupplierFactory(oxygenHistory, outcomeSaver);
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
