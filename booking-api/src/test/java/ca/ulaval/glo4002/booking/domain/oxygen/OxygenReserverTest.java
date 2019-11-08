package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenReserverTest {

    private static final int QUANTITY_LESS_THAN_GRADE_A_TANK_FABRICATION_QUANTITY = 4;
    private static final int QUANTITY_LESS_THAN_TWO_GRADE_A_TANK_FABRICATION_QUANTITIES = 6;
    private final static LocalDate FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);
    private final static LocalDate ONE_MONTH_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusMonths(1);
    private final static LocalDate DELIVERY_DATE_GRADE_A_ORDER = LocalDate.of(2050, 7, 7);
    private final static LocalDate FIFTEEN_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(15);
    private final static LocalDate FIVE_DAYS_BEFORE_FESTIVAL_DATE = FESTIVAL_STARTING_DATE.minusDays(5);

    private EnumMap<OxygenGrade, OxygenInventory> oxygenInventories = new EnumMap<>(OxygenGrade.class);
    private SortedMap<LocalDate, OxygenDateHistory> oxygenHistory = new TreeMap<>();
    private OxygenFactory oxygenFactory;
    private OxygenReserver oxygenReserver;

    @BeforeEach
    public void setUp() {
        initializeOxygenInventories();
        oxygenFactory = new OxygenFactory(FESTIVAL_STARTING_DATE);

        oxygenReserver = new OxygenReserver(oxygenFactory);
    }

    @Test
    public void whenOrderOxygenQuantityLessThanTankFabricationQuantity_thenTankFabricationQuantityIsAdded() {
        oxygenReserver.reserveOxygen(ONE_MONTH_BEFORE_FESTIVAL_DATE, OxygenGrade.A, QUANTITY_LESS_THAN_GRADE_A_TANK_FABRICATION_QUANTITY, oxygenInventories, oxygenHistory);

        int expectedInventory = oxygenInventories.get(OxygenGrade.A).getInventory();
        assertEquals(5, expectedInventory);
    }

    //TODO issue #112 part 2

    public void initializeOxygenInventories() {
        EnumSet.allOf(OxygenGrade.class)
                .forEach(grade -> oxygenInventories.put(grade, new OxygenInventory(grade, 0, 0)));
    }
}