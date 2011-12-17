package com.ovgu.myutils;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.*;

public class CommonUtils {

	public static boolean printFlag = true;
	public static String ServerIp;

	/**
	 * 
	 * @return format date in String
	 */
	// public static textEncryptionStrategy =new ROT13EncryptionStrategy();
	public static String getNowDateInstance() {
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		;
		strDate = "[" + sdf.format(new Date()) + "] ";
		return strDate;
	}

	/**
	 * set the frame to the center of bildschirm
	 * 
	 * @param f
	 * @param flag
	 */
	public static void setFrameCenter(Frame f, boolean flag) {
		f.getToolkit();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();// 获取屏幕尺寸对象
		Dimension myframe = f.getSize();// 获取当前窗体的尺寸对象
		int w = (screen.width - myframe.width) / 2;// 水平位置
		int h = (screen.height - myframe.width) / 2;// 垂直位置
		f.setLocation(w, h);
		f.setResizable(flag);
	}

	public static void PrintMe(String str) {
		if (printFlag) {
			System.out.println("$$$$$$$$$" + str + "$$$$$$$$$");
		}

	}

	public static void PrintMe(Object str) {
		if (printFlag) {

			System.out.println(str);
		}

	}

	public static void PrintMe(int str) {
		if (printFlag) {
			System.out.println(str);
		}

	}

	/**
	 * check the input is a nummer
	 * 
	 * @param p
	 * @return
	 */
	public static boolean checkIsNum(String str) {
		boolean flag = true;
		String regex = "^\\d+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (!m.matches()) {
			flag = false;
		}

		return flag;
	}

	public static boolean checkIsCorrectIp(String ip) {

		// import java.util.regex.*;
		Pattern p = Pattern
				.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
		Matcher m = p.matcher(ip);
		boolean b = m.matches();
		return b;

	}

	public static boolean checkIsNull(String str) {
		return true;
	}

}