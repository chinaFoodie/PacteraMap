package com.pactera.pacteramap.view.component.sortlist;

import java.util.Comparator;

import com.pactera.pacteramap.sqlite.litepal.bean.UserInfo;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<UserInfo> {

	public int compare(UserInfo o1, UserInfo o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
