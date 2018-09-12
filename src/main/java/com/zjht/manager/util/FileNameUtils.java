package com.zjht.manager.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zjht.manager.common.web.Constants;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 文件名生成帮助类
 * 
 * @author liufang
 * 
 */
public class FileNameUtils {
	/**
	 * 日期格式化对象，将当前日期格式化成yyyyMM格式，用于生成目录。
	 */
	public static final DateFormat pathDf = new SimpleDateFormat("yyyyMM");
	/**
	 * 日期格式化对象，将当前日期格式化成yyyy格式，用于生成目录。
	 */
	public static final DateFormat yearDf = new SimpleDateFormat("yyyy");
	/**
	 * 日期格式化对象，将当前日期格式化成MM格式，用于生成目录。
	 */
	public static final DateFormat monthDf = new SimpleDateFormat("MM");
	/**
	 * 日期格式化对象，将当前日期格式化成ddHHmmss格式，用于生成文件名。
	 */
	public static final DateFormat pathNameDf = new SimpleDateFormat("yyyy/MM/ddHHmmssSSS");
	/**
	 * 用于生产文件名，不带文件夹名
	 */
	public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmssSSS");
	/**
	 * 生成当前年/月格式的文件路径
	 * 
	 * yyyy/MM 200806
	 * 
	 * @return
	 */
	public static String genPathName() {
		//return pathDf.format(new Date());
		return yearDf.format(new Date())+ Constants.SPT+monthDf.format(new Date());
	}

	/**
	 * 生产以当前日、时间开头加4位随机数的文件名
	 * 
	 * ddHHmmss 03102230
	 * 
	 * @return 10位长度文件名
	 */
	public static String genFileName() {
		return pathNameDf.format(new Date())
				+ RandomStringUtils.random(4, Num62.N36_CHARS);
	}

	/**
	 * 生产以当前时间开头加4位随机数的文件名
	 * 
	 * @param ext
	 *            文件名后缀，不带'.'
	 * @return 10位长度文件名+文件后缀
	 */
	public static String genFileName(String ext) {
		return genFileName() + "." + ext;
	}
	/**
	 * 生产以当前时间开头加4位随机数的文件名
	 * 
	 * @param ext
	 *            文件名后缀，不带'.'
	 * @return 15位长度文件后缀
	 */
	public static String genFileNameWithOutPath(String ext){
		return nameDf.format(new Date())+ RandomStringUtils.random(4, Num62.N36_CHARS)+"."+ext;
	}

	public static void main(String[] args) {
		System.out.println(genPathName());
		System.out.println(genFileName());
	}
}
