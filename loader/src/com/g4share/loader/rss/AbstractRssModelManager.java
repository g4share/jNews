package com.g4share.loader.rss;

import com.g4share.common.log.LogLevel;
import com.g4share.common.xml.XmlModelManager;
import com.g4share.common.xml.XmlTag;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: gm
 */
public abstract class AbstractRssModelManager<T extends Enum<T> & XmlTag, U> implements XmlModelManager<T, U> {
    static SimpleDateFormat rssFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    @Override
    public abstract boolean addNode(List<T> path, String text, Map<String, String> attributes);

    @Override
    public abstract  void eventOccurred(LogLevel level, String hint);

    @Override
    public abstract U getProcessedData();

    static Date format(String rssDate){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime( rssFormatter.parse( rssDate));
            cal.add(Calendar.HOUR, -1);
            Date date = cal.getTime();
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}