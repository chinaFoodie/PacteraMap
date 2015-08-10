/**
 * 
 */
package com.pactera.pacteramap.util;

import java.util.List;
import java.util.Locale;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;

/**
 * @author ChunfaLee
 *
 */
@SuppressWarnings("deprecation")
public class StringUtil {
	/**
	 * check the string
	 * 
	 * @return true:empty;false:not empty
	 * */
	public static boolean isEmpty(String str) {
		boolean isEmpty = false;
		if (null == str || "".equals(str)) {
			isEmpty = true;
		}
		return isEmpty;
	}

	/**
	 * parse byte to string
	 * 
	 * @param bytes
	 * */
	public static String toString(byte[] bytes) {
		String str = "";
		if (null != bytes) {
			str = new String(bytes);
		}
		return str;
	}

	/**
	 * get the url with the params
	 * 
	 * @param params
	 * @param url
	 * @return
	 * */
	public static String getGetUrl(List<BasicNameValuePair> params, String url) {
		StringBuilder getUrl = new StringBuilder("");
		if (null != params && !StringUtil.isEmpty(url)) {
			String param = URLEncodedUtils.format(params, "UTF-8");
			getUrl.append(url + "?" + param);
		} else {
			getUrl.append(url);
		}
		return getUrl.toString();
	}

	/**
	 * import one line,use the string "\r\n"
	 * */
	public static String importLine() {
		return "\r\n";
	}

	/**
	 * get folder path according to file path
	 * 
	 * @param filePath
	 * */
	public static String getFolderPathFromFilePath(String filePath) {
		String folderPath = "";
		if (!isEmpty(filePath)) {
			int lastChar = filePath.lastIndexOf("/") == 0 ? filePath
					.lastIndexOf("\\") : filePath.lastIndexOf("/");
			folderPath = filePath.substring(0, lastChar);
		}
		return folderPath;
	}

	/**
	 * get file name according to file path
	 * 
	 * @param filePath
	 * */
	public static String getFileNameFromFilePath(String filePath) {
		String fileName = "";
		if (!isEmpty(filePath)) {
			int lastChar = filePath.lastIndexOf("/") == 0 ? filePath
					.lastIndexOf("\\") : filePath.lastIndexOf("/");
			fileName = filePath.substring(lastChar + 1, filePath.length());
		}
		return fileName;
	}

	@SuppressLint("DefaultLocale")
	public static String getSuffix(String filePath) {
		String suffix = "";
		if (!StringUtil.isEmpty(filePath)) {
			suffix = filePath.substring(filePath.lastIndexOf(".") + 1,
					filePath.length()).toLowerCase(Locale.getDefault());
		}
		return suffix;
	}

	public static String getFilePreFix(String fileName) {
		String prefix = "";
		if (!StringUtil.isEmpty(fileName)) {
			prefix = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return prefix;
	}

	// public static String getRequestHeader(HttpGet request)
	// {
	// StringBuilder sb = new StringBuilder();
	// sb.append(request.getRequestLine()).append("\r\n");
	// Header[] requestHeaders = request.getAllHeaders();
	// for(Header header : requestHeaders) {
	// sb.append(header.toString());
	// }
	//
	// return sb.toString();
	//
	// }
	//
	//
	// public static String getRequestHeader(HttpPost request)
	// {
	// StringBuilder sb = new StringBuilder();
	// sb.append(request.getRequestLine()).append("\r\n");
	//
	// Header[] requestHeaders = request.getAllHeaders();
	// for(Header header : requestHeaders) {
	// sb.append(header.toString());
	// }
	// sb.append("\r\n");
	// try
	// {
	// if(request.getEntity() != null)
	// {
	// sb.append(EntityUtils.toString(request.getEntity(),
	// "utf-8"));
	// }
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// return sb.toString();
	//
	// }
}
