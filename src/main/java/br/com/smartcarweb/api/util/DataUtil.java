package br.com.smartcarweb.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataUtil {
    static final String DATEFORMAT = "dd-MM-yyyy HH:mm:ss";

    /**
     * Retorna data UTC.
     * @return data UTC
     */    
    public static Date getDataUtc() {
        return converterStringParaDate(getDataUtcString());
    }

    /**
     * Retorna horário UTC em formato de texto.
     * @return string de data UTC 
     */
    public static String getDataUtcString() {

        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());
        return utcTime;
    }

    /**
     * Converte string em formato (dd-MM-yyyy HH:mm:ss) para data.
     * @param strData data em formato de string
     * @return data UTC 
     */
    public static Date converterStringParaDate(String strData) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try {
            dateToReturn = (Date) dateFormat.parse(strData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateToReturn;
    }
}
