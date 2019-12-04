package ca.ulaval.glo4002.booking.application.profit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.profit.dtos.ProfitReport;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfitUseCaseTest {
    
    private final static Price INCOME = new Price(1234.5678);
    private final static Price OUTCOME = new Price(5678.1234);

    @Mock ProfitRepository profitRepository;
    @InjectMocks ProfitUseCase profitUseCase;

    @BeforeEach
    public void setup() {
        when(profitRepository.findIncome()).thenReturn(INCOME);
        when(profitRepository.findOutcome()).thenReturn(OUTCOME);
    }

    @Test
    public void whenGeneratingProfitReport_thenReturnsProfitReportWithPopulatedFields() {
        ProfitReport returnedProfitReport = profitUseCase.generateProfitReport();

        assertThat(returnedProfitReport.getIncome()).isEqualTo(INCOME);
        assertThat(returnedProfitReport.getOutcome()).isEqualTo(OUTCOME);
    }
}
