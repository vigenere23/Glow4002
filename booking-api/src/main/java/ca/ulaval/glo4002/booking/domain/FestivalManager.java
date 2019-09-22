package ca.ulaval.glo4002.booking.domain;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

import ca.ulaval.glo4002.booking.domain.oxygenService.OxygenReportable;
import ca.ulaval.glo4002.booking.domain.oxygenService.OxygenService;

public class FestivalManager extends Module {
    private static OxygenService oxigenService;

    public static OxygenReportable getOxygeneReport() {
	return oxigenService;
    }

    public FestivalManager() {
	oxigenService = new OxygenService();
    }

    @Override
    public String getModuleName() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Version version() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setupModule(SetupContext context) {
	// TODO Auto-generated method stub

    }
}
