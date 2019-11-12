package ca.ulaval.glo4002.booking.api.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.profit.ProfitResponse;
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
        double income = profitUseCase.getIncome();
        double outcome = profitUseCase.getOutcome();
        double profit = profitUseCase.getProfit();

        return new ProfitResponse(income, outcome, profit);
    }
}
