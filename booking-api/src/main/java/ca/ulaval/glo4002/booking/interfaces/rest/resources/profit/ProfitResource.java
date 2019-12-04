package ca.ulaval.glo4002.booking.interfaces.rest.resources.profit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.profit.dto.ProfitResponse;
import ca.ulaval.glo4002.booking.application.profit.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.profit.dtos.ProfitReport;

@Path("/report/profits")
@Produces(MediaType.APPLICATION_JSON)
public class ProfitResource {
    
    @Inject private ProfitUseCase profitUseCase;
    
    @GET
    public Response profit() {
        ProfitReport profitReport = profitUseCase.generateProfitReport();
        ProfitResponse response = new ProfitResponse(profitReport.getIncome(), profitReport.getOutcome(), profitReport.getProfit());
        return Response.ok(response).build();
    }
}
