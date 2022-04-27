package date;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateTimeTest {

    private static String timePattern = "yyyy-MM-dd HH:mm:ss";
    private static String datePattern = "yyyy-MM-dd";


    private static String timeStr = "2022-04-21 15:45:50";


    @Test
    public void DateToLocalDatetime() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timePattern);
        Date parseDate = simpleDateFormat.parse(timeStr);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(parseDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime UTCDataTime = LocalDateTime.ofInstant(parseDate.toInstant(), ZoneId.of("UTC"));
    }


    @Test
    public void LocalDateTimeParse() throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(timePattern);
        LocalDateTime parse = LocalDateTime.parse(timeStr, dateTimeFormatter);
    }


}
