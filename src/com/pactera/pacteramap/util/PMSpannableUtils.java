package com.pactera.pacteramap.util;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

/**
 * 文本样式工具
 * 
 * @author ChunfaLee
 * @create 2015年6月24日16:44:36
 *
 */
public class PMSpannableUtils {
	public PMSpannableUtils() {

	}

	/**
	 * 改变字符串中某一段文字的字号 setTextSize("",24,0,2) = null; setTextSize(null,24,0,2) =
	 * null; setTextSize("abc",-2,0,2) = null; setTextSize("abc",24,0,4) = null;
	 * setTextSize("abc",24,-2,2) = null; setTextSize("abc",24,0,2) = normal
	 * string
	 * */
	public static SpannableString setTextSize(String content, int startIndex,
			int endIndex, int fontSize) {
		if (TextUtils.isEmpty(content) || fontSize <= 0
				|| startIndex >= endIndex || startIndex < 0
				|| endIndex >= content.length()) {
			return null;
		}

		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new AbsoluteSizeSpan(fontSize), startIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextSub(String content, int startIndex,
			int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}

		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new SubscriptSpan(), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextSuper(String content, int startIndex,
			int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new SuperscriptSpan(), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextStrikethrough(String content,
			int startIndex, int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new StrikethroughSpan(), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextUnderline(String content,
			int startIndex, int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextBold(String content, int startIndex,
			int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
				startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextItalic(String content, int startIndex,
			int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(
				new StyleSpan(android.graphics.Typeface.ITALIC), startIndex,
				endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextBoldItalic(String content,
			int startIndex, int endIndex) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new StyleSpan(
				android.graphics.Typeface.BOLD_ITALIC), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextForeground(String content,
			int startIndex, int endIndex, int foregroundColor) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new ForegroundColorSpan(foregroundColor),
				startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextBackground(String content,
			int startIndex, int endIndex, int backgroundColor) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new BackgroundColorSpan(backgroundColor),
				startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	/**
	 * 设置文本的超链接
	 * 
	 * @param content
	 *            需要处理的文本
	 * @param startIndex
	 * @param endIndex
	 *            被处理文本中需要处理字串的开始和结束索引
	 * @param url
	 *            文本对应的链接地址，需要注意格式： （1）电话以"tel:"打头，比如"tel:02355692427"
	 *            （2）邮件以"mailto:"打头，比如"mailto:zmywly8866@gmail.com"
	 *            （3）短信以"sms:"打头，比如"sms:02355692427"
	 *            （4）彩信以"mms:"打头，比如"mms:02355692427"
	 *            （5）地图以"geo:"打头，比如"geo:68.426537,68.123456"
	 *            （6）网络以"http://"打头，比如"http://www.google.com"
	 * */
	public static SpannableString setTextURL(String content, int startIndex,
			int endIndex, String url) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		SpannableString spannableString = new SpannableString(content);
		spannableString.setSpan(new URLSpan(url), startIndex, endIndex,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
	}

	public static SpannableString setTextImg(String content, int startIndex,
			int endIndex, Drawable drawable) {
		if (TextUtils.isEmpty(content) || startIndex < 0
				|| endIndex >= content.length() || startIndex >= endIndex) {
			return null;
		}
		// SpannableString spannableString = new SpannableString(content);
		// spannableString.setSpan(new ImageSpan(drawable), startIndex,
		// endIndex,
		// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		SpannableString spanString = new SpannableString(content);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
		spanString.setSpan(span, startIndex, endIndex,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanString;
	}
}
