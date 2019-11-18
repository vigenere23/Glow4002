package ca.ulaval.glo4002.booking.domain.oxygen2.orderers;

import java.util.List;

public class OxygenOrdererLinker {

    public OxygenOrderer link(List<OxygenOrderer> oxygenOrderers) {
        if (oxygenOrderers.isEmpty()) {
            throw new IllegalArgumentException("At least one OxygenOrderer must be present");
        }
        for (int i = 0; i < oxygenOrderers.size() - 1; i++) {
            oxygenOrderers.get(i).setNextOrderer(oxygenOrderers.get(i + 1));
        }
        return oxygenOrderers.get(0);
    }
}
