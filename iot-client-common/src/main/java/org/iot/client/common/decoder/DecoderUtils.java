package org.iot.client.common.decoder;

import org.apache.commons.lang3.math.NumberUtils;

public class DecoderUtils {
    public static Integer parseInteger(String fullText) {
        String[] ps = fullText.split(":");
        if(ps.length >= 2) {
            if(NumberUtils.isDigits(ps[1])) {
                return Integer.parseInt(ps[1]);
            }
        }
        return null;
    }

    public static String parseString(String fullText) {
        String str = fullText.substring(fullText.indexOf(":")+1, fullText.length());
        return str;
    }

    public static String parseDateTime(String fullText) {
        String dateTime = fullText.substring(fullText.indexOf(":")+1, fullText.length());
        return dateTime;
    }
    
    public static void main(String[] args) {
        System.out.println(DecoderUtils.parseDateTime("时间:2018-10-01 12:12:10"));
    }
    
}
