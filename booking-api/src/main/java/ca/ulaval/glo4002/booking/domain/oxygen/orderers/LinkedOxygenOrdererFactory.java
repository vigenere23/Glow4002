package ca.ulaval.glo4002.booking.domain.oxygen.orderers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class LinkedOxygenOrdererFactory {

    @Inject private OxygenOrdererLinker oxygenOrdererLinker;
    @Inject private SingleOxygenOrdererFactory singleOxygenOrdererFactory;

    public OxygenOrderer create(OxygenGrade oxygenGrade) {
        List<OxygenOrderer> oxygenOrderers = new ArrayList<>();

        for (OxygenGrade oxygenGradeToCompare : OxygenGrade.values()) {
            if (oxygenGrade.compareTo(oxygenGradeToCompare) <= 0) {
                oxygenOrderers.add(singleOxygenOrdererFactory.create(oxygenGradeToCompare));
            }
        }
        
        if (oxygenOrderers.isEmpty()) {
            throw new RuntimeException(
                String.format("Could not create oxygen orderer for grade %s", oxygenGrade)
            );
        }

        return oxygenOrdererLinker.link(oxygenOrderers);
    }
}
