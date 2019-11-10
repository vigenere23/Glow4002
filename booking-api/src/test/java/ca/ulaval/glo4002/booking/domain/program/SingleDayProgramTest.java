package ca.ulaval.glo4002.booking.domain.program;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.artists.ArtistProgramInformation;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenReserver;

public class SingleDayProgramTest {

    private Activity activity = Activity.CARDIO;
    private String artistName = "Sun 41";
    private LocalDate date = LocalDate.now();
    private int oxygenQuantity = 15;
    private OxygenGrade oxygenGrade = OxygenGrade.E;
    private ArtistRepository artistRepository;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OxygenReserver oxygenReserver;
    private SingleDayProgram singleDayProgram;
    
    @BeforeEach
    public void setUp() {
        artistRepository = mock(ArtistRepository.class);
        oxygenReserver = mock(OxygenReserver.class);
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);

        singleDayProgram = new SingleDayProgram(activity, artistName, date);
    }
}