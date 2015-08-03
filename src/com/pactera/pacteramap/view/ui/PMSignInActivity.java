package com.pactera.pacteramap.view.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.PMCalendar;
import com.pactera.pacteramap.view.component.PMCalendar.OnCalendarClickListener;
import com.pactera.pacteramap.view.component.PMCalendar.OnCalendarDateChangedListener;

/**
 * 签到界面
 * 
 * @author ChunfaLee
 * @create 2015年8月3日09:04:43
 * @version 1.00
 *
 */
public class PMSignInActivity extends PMActivity implements OnClickListener {
	private TextView tvTitle, tvCalMonth;
	private LinearLayout llBack;
	private PMCalendar calendar;
	private String date = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in_layout);
		init();
	}

	/**
	 * 
	 */
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("签到中心");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		calendar = (PMCalendar) findViewById(R.id.sign_in_calendar);
		tvCalMonth = (TextView) findViewById(R.id.sign_in_calendar_month);
		tvCalMonth.setText(calendar.getCalendarYear() + "年"
				+ calendar.getCalendarMonth() + "月");
		if (null != date) {
			int years = Integer.parseInt(date.substring(0, date.indexOf("-")));
			int month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
					date.lastIndexOf("-")));
			tvCalMonth.setText(years + "年" + month + "月");
			calendar.showCalendar(years, month);
			calendar.setCalendarDayBgColor(date,
					R.drawable.calendar_date_focused);
		}
		calendar.setOnCalendarClickListener(new OnCalendarClickListener() {

			public void onCalendarClick(int row, int col, String dateFormat) {
				int month = Integer.parseInt(dateFormat.substring(
						dateFormat.indexOf("-") + 1,
						dateFormat.lastIndexOf("-")));

				if (calendar.getCalendarMonth() - month == 1// 跨年跳转
						|| calendar.getCalendarMonth() - month == -11) {
					calendar.lastMonth();

				} else if (month - calendar.getCalendarMonth() == 1 // 跨年跳转
						|| month - calendar.getCalendarMonth() == -11) {
					calendar.nextMonth();
				} else {
					calendar.removeAllBgColor();
					calendar.setCalendarDayBgColor(dateFormat,
							R.drawable.calendar_date_focused);
					date = dateFormat;
				}
			}
		});
		// 监听当前月份
		calendar.setOnCalendarDateChangedListener(new OnCalendarDateChangedListener() {
			public void onCalendarDateChanged(int year, int month) {
				tvCalMonth.setText(year + "年" + month + "月");
			}
		});
		// 上月监听按钮
		RelativeLayout calendar_last_month = (RelativeLayout) findViewById(R.id.sign_in_calendar_last_month);
		calendar_last_month.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				calendar.lastMonth();
			}
		});
		// 下月监听按钮
		RelativeLayout calendar_next_month = (RelativeLayout) findViewById(R.id.sign_in_calendar_next_month);
		calendar_next_month.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				calendar.nextMonth();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMSignInActivity.this.finish();
			break;
		default:
			break;
		}
	}

}
