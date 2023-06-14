package com.samton.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterUtil {

	public static boolean isEmail(String email) {
		 String strPattern ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
	     Pattern p = Pattern.compile(strPattern);
	     Matcher m = p.matcher(email);
	     return m.matches();
    }
	
}