package ca.ulaval.glo4002.booking.api.resources.profit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.profit.dto.ProfitResponse;
import ca.ulaval.glo4002.booking.application.use_cases.ProfitUseCase;

@Path("/report/profits")
@Produces(MediaType.APPLICATION_JSON)
public class ProfitResource {
    
    private ProfitUseCase profitUseCase;
    
    @Inject
    public ProfitResource(ProfitUseCase profitUseCase) {
        this.profitUseCase = profitUseCase;
    }

    @GET
    public Response profit() {
        ProfitResponse response = new ProfitResponse(profitUseCase.getIncome(), profitUseCase.getOutcome(), profitUseCase.getProfit());
        return Response.ok(response).build();
    }
}
