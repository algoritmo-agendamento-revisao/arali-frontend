package br.com.arali.app.util;

import java.util.Calendar;
import java.util.Date;

public class Util {

    public static Date sumDays(Date dt, Integer days){
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
}
