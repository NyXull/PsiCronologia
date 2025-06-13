package util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ExtrairDadosData {

    public static int getMes(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getMonthValue();
    }

    public static int getAno(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Data não pode ser nula");
        }
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate().getYear();
        } else {
            Instant instant = date.toInstant();
            return instant.atZone(ZoneId.systemDefault()).toLocalDate().getYear();
        }
    }
}
