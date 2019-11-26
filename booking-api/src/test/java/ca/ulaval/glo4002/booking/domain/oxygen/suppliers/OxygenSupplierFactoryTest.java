package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.infrastructure.persistence.memory.InMemoryOxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

@ExtendWith(MockitoExtension.class)
public class OxygenSupplierFactoryTest {

    @Mock InMemoryOxygenHistoryRepository oxygenHistory;
    @Mock OutcomeSaver outcomeSaver;
    @InjectMocks OxygenSupplierFactory oxygenSupplierFactory;

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
