package ca.ulaval.glo4002.booking.domain.program;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.program.Activity;
import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;

public class SingleDayProgramTest {

    private Activity activity = Activity.CARDIO;
    private String artistName = "Sun 41";
    private LocalDate date = LocalDate.now();
    private int oxygenQuantity = 15;
    private OxygenGrade oxygenGrade = OxygenGrade.E;
    private ArtistRepository artistRepository;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OxygenProducer oxygenProducer;
    private SingleDayProgram singleDayProgram;
    
    @BeforeEach
    public void setUp() {
        artistRepository = mock(ArtistRepository.class);
        oxygenProducer = mock(OxygenProducer.class);
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);

        singleDayProgram = new SingleDayProgram(activity, artistName, date);
    }

    @Test
    public void givenProgram_provideProgramResources_thenProvideProgramResourcesfromProgramIsCalled() {
        
        singleDayProgram.orderOxygen(oxygenProducer, artistRepository, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(date, oxygenGrade, oxygenQuantity, oxygenInventoryRepository.findInventories(), oxygenHistoryRepository.findOxygenHistory());
    }
}