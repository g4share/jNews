package com.g4share.viewer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: gm
 */
public class JspFormatter {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM, HH:mm");
    private SimpleDateFormat todayFormat = new SimpleDateFormat("HH:mm");

    public String format(Date date) {
        Date now = new Date();
        DateFormat formatter;
        if (!areTheSameDay(date, now)) formatter = simpleDateFormat;
        else formatter = todayFormat;

        return formatter.format(date);
    }

    private boolean areTheSameDay(Date dt1, Date dt2){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(dt1).equals(fmt.format(dt2));
    }
}
