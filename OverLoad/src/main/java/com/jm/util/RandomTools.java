/**
 * 
 */
package com.jm.util;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 随即字符串生成器.
 *
 */
public class RandomTools {

	/**
	 * 生成一个随机码,长度36.
	 * @return
	 * 
	 */
	public static String randomString() {
		return randomStringByUUID();
	}
	
	/**
	 * 生成一个随机码,长度36,全大写.
	 * @return
	 * 
	 */
	public static String randomStringUpper() {
		return randomString().toUpperCase();
	}
	
	/**
	 * 使用java5的UUID类,返回一个随机码,长度36.
	 * 
	 * @return 一个随机码,形如:5ec24ed3-ff1a-41c1-8d23-a37af006bbb3
	 * 
	 */
	private static String randomStringByUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 生成随机数字，长度固定
	 * @param max 最大值
	 * @return
	 */
	public static String randomNo(int max){
	    if(max <= 0 ){
	        return null;
	    }
	    Random r = new Random();
        String sqNo = StringUtils.leftPad(Integer.toString(r.nextInt(max)), String.valueOf(max).length()-1, "0");
        return sqNo;
    }
	
	/**
	 * 生成唯一的一组随机数字
	 * @param max 最大值
	 * @param num 个数
	 * @return
	 */
	public static Set<String> randomUniqueNo(int max,int num){
	    if(max <= 0 || num <= 0 || max < num){
	        return null;
	    }
	    
	    Set<String> set = new HashSet<>();
	    do{
	        set.add(randomNo(max));
	    }while(set.size() < num);
	    
	    return set;
	}
	
	public static String nextNewOrderNo(){
        String orderNo = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String sqNo = RandomTools.randomNo(100000000);
        return "10"+orderNo+sqNo;
    }
	
	public static void main(String[] args) {
		System.out.println(RandomTools.randomNo(100));
	}
	
	
	
}