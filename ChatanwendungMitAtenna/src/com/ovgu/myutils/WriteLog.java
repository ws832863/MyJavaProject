package com.ovgu.myutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteLog {

	private boolean testExist(File f) {
		return true;
	}

	//

	public static void writeChatLog(String log) {
		//#if Logging
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		;
		String text = "[" + sdf.format(new Date()) + "] " + log;
		sdf.applyPattern("yyyy-MM");
		String path = "C://chatlogs//" + sdf.format(new Date().getTime());
		FileOutputStream fos = null;
		PrintWriter out = null;
		File file = new File(path + "/log.txt");

		try {

			if (!file.exists()) {
				write("file doesn't exist");

				File dirFile = new File(path);

				if (!dirFile.exists()) {// if the dir not exists
					write("dir doesn't exist,create a empty dir in "
							+ dirFile.getAbsolutePath());
					dirFile.mkdirs();
				}
				file.createNewFile();

			}
			fos = new FileOutputStream(file, true);
			out = new PrintWriter(fos);
			out.println(text);
			out.flush();
		} catch (Exception e) {
			write(e.getMessage());
		} finally {
			try {
				if (out != null)
					out.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
				write(e.getMessage());
			}
		}
	//#endif
	}

	public static void write(String log) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		;
		String text = "[" + sdf.format(new Date()) + "] " + log;
		sdf.applyPattern("yyyy-MM");
		String path = "C://logs//" + sdf.format(new Date().getTime());
		FileOutputStream fos = null;
		PrintWriter out = null;
		File file = new File(path + "/log.txt");

		try {

			if (!file.exists()) {
				System.out.println("file doesn't exist");

				File dirFile = new File(path);

				if (!dirFile.exists()) {// if the dir not exists
					System.out
							.println("dir doesn't exist,create a empty dir in "
									+ dirFile.getAbsolutePath());
					dirFile.mkdirs();
				}
				file.createNewFile();

			}
			fos = new FileOutputStream(file, true);
			out = new PrintWriter(fos);
			out.println(text);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (fos != null)
					fos.close();
			} catch (Exception e) {
			}
		}
	}
}
