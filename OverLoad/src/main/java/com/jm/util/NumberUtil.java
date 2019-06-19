package com.jm.util;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtil {

    public static double parseDouble(String numStr) {
        double num = 0;
        try {
            num = Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return num;
    }
    
    public static String formatNum(double num) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2); 
        nf.setRoundingMode(RoundingMode.DOWN);
        return nf.format(num);
    }
}
