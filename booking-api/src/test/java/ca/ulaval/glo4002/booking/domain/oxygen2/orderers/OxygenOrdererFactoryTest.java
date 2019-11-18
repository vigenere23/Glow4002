package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen2.suppliers.OxygenSupplierFactory;

public class OxygenOrdererFactoryTest {

    private OxygenOrdererFactory oxygenOrdererFactory;
    private OxygenOrdererLinker oxygenOrdererLinker;
    private OxygenSupplierFactory oxygenSupplierFactory;
    private OxygenRequestSettingsFactory requestSettingsFactory;
    private OxygenInventory oxygenInventory;

    @BeforeEach
    public void setup() {
        oxygenOrdererLinker = mock(OxygenOrdererLinker.class);
        oxygenSupplierFactory = mock(OxygenSupplierFactory.class);
        requestSettingsFactory = mock(OxygenRequestSettingsFactory.class);
        oxygenInventory = mock(OxygenInventory.class);
        oxygenOrdererFactory = new OxygenOrdererFactory(
            oxygenOrdererLinker, oxygenSupplierFactory, requestSettingsFactory, oxygenInventory
        );
    }

    @Test
    public void givenOxygenGradeA_whenCreating_itReturnsOrdererWithTypeA() {

    }
}
