package com.pactera.pacteramap.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreferce保存数据到内存中
 * 
 * @author Chunfa Lee
 * @create 2015年7月24日16:56:03
 * 
 */
@SuppressLint({ "WorldReadableFiles", "WorldWriteableFiles" })
public class PMSharePreferce {

	private static PMSharePreferce tool = null;

	private SharedPreferences shareprefece;
	private SharedPreferences.Editor editor;

	/**
	 * Construct
	 */
	@SuppressWarnings("deprecation")
	private PMSharePreferce(Context context) {
		// Preferences对象
		shareprefece = context.getSharedPreferences("jiameng",
				Context.MODE_APPEND + Context.MODE_WORLD_READABLE
						+ Context.MODE_WORLD_WRITEABLE);
		editor = shareprefece.edit();
	}

	/**
	 * 获取单例 Create at 2015-4-29
	 * 
	 * @author luomin
	 * @param context
	 * @return SharePreferce
	 */
	public static PMSharePreferce getInstance(Context context) {
		if (tool == null) {
			tool = new PMSharePreferce(context);
		}
		return tool;
	}

	public boolean isEmpty(String key) {
		return !shareprefece.contains(key);
	}

	/**
	 * 清理缓存 Create at 2013-7-1
	 * 
	 */
	public void clearCache() {
		editor.clear();
		editor.commit();
	}

	/**
	 * 设置SharedPrefere缓存 Create at 2015-4-29
	 * 
	 * @param StringUtil
	 *            key 键值
	 * @param Object
	 *            value 缓存内容
	 */
	public void setCache(String key, Object value) {
		if (value instanceof Boolean)// 布尔对象
			editor.putBoolean(key, (Boolean) value);
		else if (value instanceof String)// 字符串
			editor.putString(key, (String) value);
		else if (value instanceof Integer)// 整型数
			editor.putInt(key, (Integer) value);
		else if (value instanceof Long)// 长整型
			editor.putLong(key, (Long) value);
		else if (value instanceof Float)// 浮点数
			editor.putFloat(key, (Float) value);
		editor.commit();
	}

	/**
	 * 读取缓存中的字符串 Create at 2015-4-29
	 * 
	 * @param key
	 * @return String
	 */
	public String getString(String key) {
		return shareprefece.getString(key, "");
	}

	/**
	 * 读取缓存中的布尔型缓存 Create at 2015-4-29
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean getBoolean(String key) {
		return shareprefece.getBoolean(key, false);
	}

	/**
	 * 读取缓存中的整型数 Create at 2015-4-29
	 * 
	 * @param key
	 * @return int
	 */
	public int getInt(String key) {
		return shareprefece.getInt(key, 0);
	}

	/**
	 * 读取缓存中的长整型数 Create at 2015-4-29
	 * 
	 * @param key
	 * @return long
	 */
	public long getLong(String key) {
		return shareprefece.getLong(key, 0);
	}

	/**
	 * 读取缓存中的浮点数 Create at 2015-4-29
	 * 
	 * @param key
	 * @return float
	 */
	public float getFloat(String key) {
		return shareprefece.getFloat(key, 0.0f);
	}

	/**
	 * 判断是否有缓存
	 * 
	 * @param string
	 * @return
	 */
	public boolean ifHaveShare(String string) {
		return shareprefece.contains(string);
	}

}
