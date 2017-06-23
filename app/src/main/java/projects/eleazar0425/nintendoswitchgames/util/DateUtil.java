package projects.eleazar0425.nintendoswitchgames.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Eleazar Estrella on 6/7/17.
 */

public class DateUtil {

    static final String DEFAULT_PATTERN = "MMM dd, yyyy";

    public static Date parse(String dateString){
        Date date;
        try{
            SimpleDateFormat format = new SimpleDateFormat(DEFAULT_PATTERN, Locale.US);
            date = format.parse(dateString);
        }catch (ParseException e){
            date = new Date();
        }
        return date;
    }

    public static int getDaysBetweenDates(Date date1, Date date2){
        return (int) TimeUnit.DAYS.convert(date2.getTime() - date1.getTime(), TimeUnit.MILLISECONDS);
    }
}
