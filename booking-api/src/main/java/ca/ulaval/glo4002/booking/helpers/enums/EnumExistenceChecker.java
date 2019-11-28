package ca.ulaval.glo4002.booking.helpers.enums;

public class EnumExistenceChecker {

    public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> enumClass) {
        for (E e : enumClass.getEnumConstants()) {
            if (e.name().equals(value) || e.toString().equals(value)) return true;
        }
        
        return false;
    }
}
