package com.geektcp.alpha.chat.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author tanghaiyang
 */
public class FileUtils {

	/**
	 * 读取文件并返回utf8编码的字符流
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readText(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException();
		}
		FileInputStream in = new FileInputStream(file);
		StringBuffer result = new StringBuffer("");
		// 指定读取文件时以UTF-8的格式读取
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(in, "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line).append("\n");
			}
		}
		return result.toString();
	}

	public static String readText(InputStream inputStream) throws IOException {
		StringBuffer result = new StringBuffer("");
		// 指定读取文件时以UTF-8的格式读取
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(inputStream, "UTF-8"))) {
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line).append("\n");
			}
		}
		return result.toString();
	}

}
