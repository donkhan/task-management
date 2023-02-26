package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class CalendarUtils {
    public static int getMonth(String month){
        if(month.equals("Jan")) return 1;
        if(month.equals("Feb")) return 2;
        if(month.equals("Mar")) return 3;
        if(month.equals("Apr")) return 4;
        if(month.equals("May")) return 5;
        if(month.equals("Jun")) return 6;
        if(month.equals("Jul")) return 7;
        if(month.equals("Aug")) return 8;
        if(month.equals("Sep")) return 9;
        if(month.equals("Oct")) return 10;
        if(month.equals("Nov")) return 11;
        if(month.equals("Dec")) return 12;
        return -1;
    }

    public static GregorianCalendar getDateFromString(String s){
        StringTokenizer tokenizer = new StringTokenizer(s,"-");
        return getCalendar(Integer.parseInt(tokenizer.nextToken()),getMonth(tokenizer.nextToken()),
                Integer.parseInt(tokenizer.nextToken()));
    }

    private static GregorianCalendar getCalendar(int date, int month,int year){
        GregorianCalendar s = new GregorianCalendar();
        s.set(Calendar.MONTH,month-1); s.set(Calendar.DATE,date);s.set(Calendar.YEAR,2000 + year);
        s.set(Calendar.HOUR_OF_DAY,1); s.set(Calendar.MINUTE,0); s.set(Calendar.SECOND,0);
        s.set(Calendar.MILLISECOND,0);
        return s;
    }
}
