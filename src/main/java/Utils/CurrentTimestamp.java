package Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrentTimestamp {
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

            //method 1
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            //return number of milliseconds since January 1, 1970, 00:00:00 GMT

            return String.valueOf(timestamp.getTime());
    }
}
