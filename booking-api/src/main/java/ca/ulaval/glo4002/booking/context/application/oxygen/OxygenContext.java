package ca.ulaval.glo4002.booking.context.application.oxygen;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.LinkedOxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererLinker;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.SingleOxygenOrdererFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.settings.OxygenRequestSettingsFactory;
import ca.ulaval.glo4002.booking.domain.oxygen.suppliers.OxygenSupplierFactory;

public class OxygenContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(OxygenOrdererLinker.class);
        bindAsContract(OxygenRequestSettingsFactory.class);
        bindAsContract(OxygenSupplierFactory.class);
        bindAsContract(SingleOxygenOrdererFactory.class);
        bindAsContract(LinkedOxygenOrdererFactory.class);
        bindAsContract(OxygenRequester.class);
        bindAsContract(OxygenHistoryDtoMapper.class);
        bindAsContract(OxygenInventoryDtoMapper.class);
        bindAsContract(OxygenUseCase.class);
    }
}
