package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

@ExtendWith(MockitoExtension.class)
public class SingleOxygenOrdererFactoryTest {

    private final static OxygenGrade SOME_GRADE = OxygenGrade.A;

    @Mock OxygenDates someOxygenDates;
    @Mock OxygenSupplierFactory oxygenSupplierFactory;
    @Mock OxygenRequestSettingsFactory requestSettingsFactory;
    @Mock OxygenInventoryRepository oxygenInventory;
    @InjectMocks SingleOxygenOrdererFactory singleOxygenOrdererFactory;

    @Test
    public void whenCreating_itAsksTheOxygenRequestSettingsFactoryToCreateWithTheRequestedGrade() {
        singleOxygenOrdererFactory.create(SOME_GRADE);
        verify(requestSettingsFactory).create(SOME_GRADE);
    }

    @Test
    public void whenCreating_itAsksTheOxygenSupplierFactoryToCreateWithTheRequestedGrade() {
        singleOxygenOrdererFactory.create(SOME_GRADE);
        verify(oxygenSupplierFactory).create(SOME_GRADE);
    }
}
