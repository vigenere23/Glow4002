package ca.ulaval.glo4002.booking.api.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.profit.ProfitResponse;
import ca.ulaval.glo4002.booking.domain.profit.ProfitService;

@Path("/report/profits")
@Produces(MediaType.APPLICATION_JSON)
public class ProfitResource {
    
    private ProfitService profitService;
    
    @Inject
    public ProfitResource(ProfitService profitService) {
        this.profitService = profitService;
    }

    @GET
    public ProfitResponse profit() {
        float income = profitService.getIncome();
        float outcome = profitService.getOutcome();
        float profit = income - outcome;

        return new ProfitResponse(income, outcome, profit);
    }
}
