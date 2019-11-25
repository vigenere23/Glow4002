package ca.ulaval.glo4002.booking.context.application.oxygen;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererLinker;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

public class OxygenContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(OxygenOrdererLinker.class);
        bindAsContract(OxygenRequestSettingsFactory.class);
        bindAsContract(OxygenSupplierFactory.class);
        bindAsContract(OxygenOrdererFactory.class);
        bindAsContract(OxygenRequester.class);
        bindAsContract(OxygenHistoryEntryDtoMapper.class);
        bindAsContract(OxygenInventoryEntryDtoMapper.class);
        bindAsContract(OxygenUseCase.class);
    }
}
