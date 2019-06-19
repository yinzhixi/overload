package com.jm.web.realm;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Test {
	public static void main(String[] args) {
		Md5Hash md = new Md5Hash("111", "BFE5", 5);
		System.out.println(md);
		
	}
	
	
}
