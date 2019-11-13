package ca.ulaval.glo4002.booking.api.resources.profit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.resources.profit.dto.ProfitResponse;
import ca.ulaval.glo4002.booking.application.ProfitUseCase;

@Path("/report/profits")
@Produces(MediaType.APPLICATION_JSON)
public class ProfitResource {
    
    private ProfitUseCase profitUseCase;
    
    @Inject
    public ProfitResource(ProfitUseCase profitUseCase) {
        this.profitUseCase = profitUseCase;
    }

    @GET
    public ProfitResponse profit() {
        return new ProfitResponse(profitUseCase.getIncome(), profitUseCase.getOutcome(), profitUseCase.getProfit());
    }
}
