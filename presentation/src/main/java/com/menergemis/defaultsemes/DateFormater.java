package com.menergemis.defaultsemes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormater {
    public static long getTimeZoneValue(long time){
        TimeZone tz = TimeZone.getDefault();
        Date date = new Date(time);
        int rawOffset = tz.getRawOffset();
        if(tz.inDaylightTime(date)){
            int dstSavings = tz.getDSTSavings();
            time = time + rawOffset + dstSavings;
        }
        else{
            time = time + rawOffset;
        }
        return  time;
    }
    public static String getDateFormat(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(getTimeZoneValue(date));
    }

    public static String getDateTimeFormat(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String getTimeFormat(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM" + "\n" +"h:mm a");
        return dateFormat.format(getTimeZoneValue(time));
    }

    public static String getTimeFormatShort(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(getTimeZoneValue(time));
    }

    public static String getTimeFormatLong(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return dateFormat.format(getTimeZoneValue(time));
    }

    public static String getTimeFromStr(String startDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(startDate);
            return getTimeFormat(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static long getTimeFromStrToLong(String startDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = dateFormat.parse(startDate);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public static String getTimeFromStrShort(String startDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            Date date = dateFormat.parse(startDate);
            return getTimeFormatShort(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public static String getTimeFromStrLong(String startDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date = dateFormat.parse(startDate);
            return getTimeFormatLong(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }


    public static long getTimeInMillis(String startDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(startDate);
            assert date != null;
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

   /* String str_date="13-09-2011";
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = (Date)formatter.parse(str_date);*/
}
