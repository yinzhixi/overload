package com.jm.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public abstract class DateUtil extends org.apache.commons.lang3.time.DateUtils {
	
	 /** PATTERN yyyy-MM-dd HH:mm:ss*/
    public static final String DATETIME_DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    /** pattern yyyy-MM-dd */
    public static final String DATE_SAMPLE_PATTERN = "yyyy-MM-dd";
    
    /** pattern yyyyMMdd */
    public static final String DATE_NUMBER_PATTERN = "yyyyMMdd";
    
    /** pattern HH:mm */
    public static final String TIME_DEFAULT_PATTERN = "HH:mm";
    
    /** pattern HH:mm:ss */
    public static final String TIME_SAMPLE_PATTERN = "HH:mm:ss";
    
    /** patterns */
    public static final String[] DATE_PATTERNS = new String[]{"yyyy-MM-dd","yyyy/MM/dd","MM/dd/yyyy","dd/MM/yyyy","dd.MM.yyyy"};
    public static final String[] WEEK_DESC = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
    
    /**
     * 转换格式为yyyy-MM-dd HH:mm:ss
     * @param date
     * @return String 
     */
    public static String format(Date date) {
        return format(date, DATETIME_DEFAULT_PATTERN);
    }
    
    /**
     * 将日期类型转换为数据库的日期类型
     * @param date
     * @return
     */
    public static java.sql.Date parse2SqlDate(Date date) {
    	return new java.sql.Date(date.getTime());
    }
    
    /**
     * 将yyyy-mm-dd的字符串日期转换为数据库的日期类型
     * @param date
     * @return
     */
    public static java.sql.Date parse2SqlDate(String date) {
    	return java.sql.Date.valueOf(date);
    }
    
    /**
     * 获得当前数据类型的日期
     * @return
     */
    public static java.sql.Date newSqlDate() {
    	return new java.sql.Date(new Date().getTime());
    }
    
    /**
     * 获取当前时间戳
     * @return
     */
    public static Timestamp newTimestamp() {
    	return new Timestamp(new Date().getTime());
    }
    
    /**
     * 转换格式为yyyy-MM-dd
     * @param date
     * @return String
     */
    public static String format2SampleDate(Date date) {
    	return format(date, DATE_SAMPLE_PATTERN);
    }
    
    /**
     * 将日期类型的对象转换格式为yyyy-MM-dd
     * @param obj
     * @return String
     */
    public static String format2SampleDate(Object obj) {
    	if (obj == null) {
    		return null;
    	}
    	if (obj instanceof Date) {
    		Date date = (Date) obj;
    		return format2SampleDate(date);
    	}
    	return null;
    }
    
    /**
     * 将格式为yyyy-MM-dd格式字符串转换为Date
     * 
     * @param str
     * @return Date
     */
    public static Date parseDate(String str) {
        Date date = null;
    	try {
    	    date = parseDate(str, DATE_SAMPLE_PATTERN);
    	    return date;
		} catch (ParseException e) {}
    	try {
            date = parseDate(str, DATETIME_DEFAULT_PATTERN);
            return date;
        } catch (ParseException e) {}
    	try {
            date = parseDate(str, DATE_NUMBER_PATTERN);
            return date;
        } catch (ParseException e) {}
    	
    	return date;
    }
    
    /**
     * 
     * @param str
     * @param fromPattern
     * @param toPattern
     * @return 
     * @return String
     */
    public static String format(String str, String fromPattern, String toPattern) {
        String res = "";
        String[] patterns = new String[]{fromPattern};
        Date date = null;
        try {
            date = parseDate(str, patterns);
        } catch (ParseException e) {
        	return null;
        }
        if (date != null) {
            res = format(date, toPattern);
        }
        return res;
    }
    /**
     * <b>按照指定格式转换字符串</b>
     * 
     * @param date
     * @param pattern
     * @return 
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
        return dateformat.format(date);
    }
    
    /**
     * 转换格式为yyyy-MM-dd
     * 
     * @param date
     * @return Date
     */
    public static Date parse2SampleDate(String date) {
		try {
			return new SimpleDateFormat(DATE_SAMPLE_PATTERN).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
    /**
     * 转换格式为yyyy-MM-dd
     * 
     * @param date
     * @return Date
     */
    public static Date parse2SampleDatetime(String date) {
		try {
			return new SimpleDateFormat(DATETIME_DEFAULT_PATTERN).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
    
    /**
     * 转换格式为yyyy-MM-dd
     * 
     * @param date
     * @return Date
     */
	public static Date parse2SampleDate(Object date) {
		return parse2SampleDate(Objects.toString(date));
	}
    
	/**
     * 转换格式为yyyy-MM-dd
     * 
     * @param date
     * @return Date
     */
	public static Date parse2SampleDatetime(Object date) {
		return parse2SampleDatetime(Objects.toString(date));
	}
    /**
     * Get Current Date
     * @return String 
     */
    public static String getCurrentDate() {
    	return format(new Date(), DATE_SAMPLE_PATTERN);
    }
    
    /**
     * 1900-01-01 00:00:00
     * @date 2013-11-19
     * @return
     */
    public static Date getDbDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, 0, 1);
        return clearTime(calendar.getTime());
    }
    
    /**
     * 获得当年的第一天
     * @return
     */
    public static Date getCurrentYearFirstDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), 0, 1);
        return clearTime(calendar.getTime());
    }
    
    public static Date getFirstDayOfMonth() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.getActualMinimum(Calendar.DATE));
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }
    
    // 获取本月第一周第一天
    public static Date getFirstDayOfFirstWeekOfMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        now.setTime(clearTime(date));

        while (now.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            now.add(Calendar.DATE, -1);
        }
        return now.getTime();
    }
    
    public static Date getFirstDayOfFirstWeekOfMonth() {
        Calendar now = Calendar.getInstance();
        now.setFirstDayOfWeek(Calendar.MONDAY);
        now.setTime(getFirstDayOfMonth());

        while (now.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            now.add(Calendar.DATE, -1);
        }
        return now.getTime();
    }
    
    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
    }
    
    /**
     * Get Current Year
     * @return int
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * Get Current Date Time
     * @return String 
     */
    public static String getCurrentDateTime() {
    	return format(new Date(), DATETIME_DEFAULT_PATTERN);
    }
    
    /**
     * Get Current Time  HH:mm:ss 
     * @return TIME_SAMPLE_PATTERN
     */
    public static String getCurrentTime() {
    	return format(new Date(), TIME_SAMPLE_PATTERN);
    }
    
    /**
     * Get Current Time HH:mm 
     * @return TIME_SAMPLE_PATTERN
     */
    public static String getCurrentTime2() {
    	return format(new Date(), TIME_DEFAULT_PATTERN);
    }
    
    /**
     * 计算两个日期之间的天数＋1,包含开始和结束的这两天.
     * 
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int daysOfTwoDate(Date beginDate, Date endDate) {
        return (int) ((endDate.getTime() - beginDate.getTime()) / MILLIS_PER_DAY) + 1;
    }
    
    
    /**
     * 去掉时间
     * @param date
     * @return Date 
     */
    public static Date clearTime(Date date) {
    	if (date == null) {
    		return null;
    	}
    	return truncate(date, Calendar.DATE);
    }
    
    /**
     * 判断一个日期是否介于 两个日期之间 ，包含边界
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @param date 要测试的日期
     * @param field 精确的field 比如 Calendar.YEAR Calendar.Date
     * @return boolean 
     */
    public static boolean isBeteen(Date startDate, Date endDate, Date date, int field) {
    	int res1 = truncatedCompareTo(date, startDate, field);
    	int res2 = truncatedCompareTo(endDate, date, field);
    	if (res1 >= 0 && res2 >= 0) {
    		return true;
    	}
    	return false;
	}
    
    
	/**
	 * 获取本周一的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date findFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
    		case 1:
    			//SUNDAY
    			calendar.add(Calendar.DATE, 1);
    			break;
    		case 2:
    			//MONDAY
    			calendar.add(Calendar.DATE, 0);
    			break;
    		case 3:
    			//TUESDAY
    			calendar.add(Calendar.DATE, -1);
    			break;
    		case 4:
    			//WEDNESDAY
    			calendar.add(Calendar.DATE, -2);
    			break;
    		case 5:
    			//THURSDAY
    			calendar.add(Calendar.DATE, -3);
    			break;
    		case 6:
    			//FRIDAY
    			calendar.add(Calendar.DATE, -4);
    			break;
    		case 7:
    			//SATURDAY
    			calendar.add(Calendar.DATE, -5);
    			break;
    		default : break;
		    
		}
		return calendar.getTime();
	}
	
	/**
	 * 获取下周一的日期
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date findFirstDayOfNextWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
    		case 1:
    			//SUNDAY
    			calendar.add(Calendar.DATE, 8);
    			break;
    		case 2:
    			//MONDAY
    			calendar.add(Calendar.DATE, 7);
    			break;
    		case 3:
    			//TUESDAY
    			calendar.add(Calendar.DATE, 6);
    			break;
    		case 4:
    			//WEDNESDAY
    			calendar.add(Calendar.DATE, 5);
    			break;
    		case 5:
    			//THURSDAY
    			calendar.add(Calendar.DATE, 4);
    			break;
    		case 6:
    			//FRIDAY
    			calendar.add(Calendar.DATE, 3);
    			break;
    		case 7:
    			//SATURDAY
    			calendar.add(Calendar.DATE, 2);
    			break;
    		default : break;
		}
		return calendar.getTime();
	}

    public static Date findFirstDayOfLastWeek(Date start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                //SUNDAY
                calendar.add(Calendar.DATE, -13);
                break;
            case 2:
                //MONDAY
                calendar.add(Calendar.DATE, -7);
                break;
            case 3:
                //TUESDAY
                calendar.add(Calendar.DATE, -8);
                break;
            case 4:
                //WEDNESDAY
                calendar.add(Calendar.DATE, -9);
                break;
            case 5:
                //THURSDAY
                calendar.add(Calendar.DATE, -10);
                break;
            case 6:
                //FRIDAY
                calendar.add(Calendar.DATE, -11);
                break;
            case 7:
                //SATURDAY
                calendar.add(Calendar.DATE, -12);
                break;
            default : break;
        }
        return calendar.getTime();
    }

}
