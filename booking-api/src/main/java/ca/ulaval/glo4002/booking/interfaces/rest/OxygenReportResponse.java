package ca.ulaval.glo4002.booking.interfaces.rest;

import ca.ulaval.glo4002.booking.domain.oxygenService.OxygenReportable;

public class OxygenReportResponse {
	
	private OxygenReportable oxigenReportableService;
	public OxygenReportResponse() {
		oxigenReportableService = ca.ulaval.glo4002.booking.domain.FestivalManager.getOxygeneReport();
		
	}

}
