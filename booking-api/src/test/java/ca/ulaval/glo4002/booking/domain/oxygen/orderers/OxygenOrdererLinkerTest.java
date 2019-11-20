package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OxygenOrdererLinkerTest {

    private OxygenOrdererLinker oxygenOrdererLinker;
    private OxygenOrderer oxygenOrderer1;
    private OxygenOrderer oxygenOrderer2;
    private OxygenOrderer oxygenOrderer3;
    private List<OxygenOrderer> oxygenOrderers;

    @BeforeEach
    public void setup() {
        setupOxygenOrderersList();
        oxygenOrdererLinker = new OxygenOrdererLinker();
    }

    @Test
    public void givenEmptyList_whenLinking_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            oxygenOrdererLinker.link(new ArrayList<>());
        });
    }

    @Test
    public void givenListOfOxygenOrderer_whenLinking_itSetsNextEachOrdererWithTheNextOne() {
        oxygenOrdererLinker.link(oxygenOrderers);
        verify(oxygenOrderer1).setNextOrderer(oxygenOrderer2);
        verify(oxygenOrderer2).setNextOrderer(oxygenOrderer3);
    }

    private void setupOxygenOrderersList() {
        oxygenOrderer1 = mock(OxygenOrderer.class);
        oxygenOrderer2 = mock(OxygenOrderer.class);
        oxygenOrderer3 = mock(OxygenOrderer.class);
        oxygenOrderers = new ArrayList<>();
        oxygenOrderers.add(oxygenOrderer1);
        oxygenOrderers.add(oxygenOrderer2);
        oxygenOrderers.add(oxygenOrderer3);
    }
}
