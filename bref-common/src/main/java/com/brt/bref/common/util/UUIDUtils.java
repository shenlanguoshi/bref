package com.brt.bref.common.util;

import java.util.UUID;

public class UUIDUtils {
	public static String getUUID(){
		String date = DateUtil.getYmdhmss();
		String uuid = UUID.randomUUID().toString().replace("-", "");
        return (date + uuid).substring(0, 36);
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID().length());
		System.out.println(getUUID());		
	}
}
