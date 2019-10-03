package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.util.EnumMap;

public final class ProductionSettings {
    
    public static final EnumMap<OxygenGrade, Integer> minimumFabricationQuantity = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class) {
        private static final long serialVersionUID = 1L;
        {
           put(OxygenGrade.A, 5);
           put(OxygenGrade.B, 3);
           put(OxygenGrade.E, 1);
        } 
    };

    public static final EnumMap<OxygenGrade, Integer> fabricationTimeInDay = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class) {
        private static final long serialVersionUID = 1L;
        {
           put(OxygenGrade.A, 20);
           put(OxygenGrade.B, 10);
           put(OxygenGrade.E, 0);
        } 
    };

    public static final EnumMap<OxygenGrade, Integer> candleProductionNeed = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class) {
        private static final long serialVersionUID = 1L;
        {
           put(OxygenGrade.A, 15);
           put(OxygenGrade.B, 0);
           put(OxygenGrade.E, 0);
        } 
    };

    public static final EnumMap<OxygenGrade, Integer> waterProductionNeed = new EnumMap<OxygenGrade, Integer>(OxygenGrade.class) {
        private static final long serialVersionUID = 1L;
        {
           put(OxygenGrade.A, 0);
           put(OxygenGrade.B, 8);
           put(OxygenGrade.E, 0);
        } 
    };
}