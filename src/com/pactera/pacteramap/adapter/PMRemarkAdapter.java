package com.pactera.pacteramap.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.vo.PMRemark.Data;

/**
 * 备忘录适配器
 * 
 * @author ChunfaLee
 * @create 2015年6月24日17:15:16
 *
 */
@SuppressLint("InflateParams")
public class PMRemarkAdapter extends BaseAdapter implements OnClickListener {
	private List<Data> list;
	private Context context;
	private int currentType;
	private final int FIRST_TYPE = 0;
	private final int OTHERS_TYPE = 1;
	private final int TYPE_COUNT = 2;

	public PMRemarkAdapter(Context context, List<Data> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return FIRST_TYPE;
		} else {
			return OTHERS_TYPE;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View firstItemView = null;
		View othersItemView = null;
		currentType = getItemViewType(position);
		FirstItemViewHolder firstItemViewHolder = null;
		firstItemView = convertView;
		if (currentType == FIRST_TYPE) {
			if (firstItemView == null) {
				firstItemView = LayoutInflater.from(context).inflate(
						R.layout.remark_list_right_pic, null);
				firstItemViewHolder = new FirstItemViewHolder();
				firstItemViewHolder.tvFirstTitle = (TextView) firstItemView
						.findViewById(R.id.remark_list_item_first_title);
				firstItemViewHolder.tvFirstContent = (TextView) firstItemView
						.findViewById(R.id.remark_list_item_first_content);
				firstItemViewHolder.tvReadAll = (TextView) firstItemView
						.findViewById(R.id.remark_list_item_first_read_all);
				firstItemViewHolder.imgContent = (ImageView) firstItemView
						.findViewById(R.id.remark_list_item_first_img_content);
				firstItemViewHolder.imgSpeaker = (ImageView) firstItemView
						.findViewById(R.id.remark_list_item_first_img_speaker);
				firstItemView.setTag(firstItemViewHolder);
			} else {
				firstItemViewHolder = (FirstItemViewHolder) firstItemView
						.getTag();
			}
			firstItemViewHolder.tvFirstTitle.setText("标题一");
			firstItemViewHolder.tvFirstContent
					.setText("Eclipse ADT 是 Eclipse 平台下用来开发 Android 应用程序的插件。 在线安装地址:https://d... rezerwar 益智游戏 DroidDraw logo DroidDraw Android组件界面设计");
			firstItemViewHolder.tvReadAll.setOnClickListener(this);
			firstItemViewHolder.imgContent.setOnClickListener(this);
			firstItemViewHolder.imgSpeaker.setOnClickListener(this);
			convertView = firstItemView;
		} else {
			othersItemView = convertView;
			OthersViewHolder othersViewHolder = null;
			if (othersItemView == null) {
				othersItemView = LayoutInflater.from(context).inflate(
						R.layout.remark_list_left_pic, null);
				othersViewHolder = new OthersViewHolder();
				othersViewHolder.tvFirstTitle = (TextView) othersItemView
						.findViewById(R.id.remark_list_item_first_title);
				othersViewHolder.tvFirstContent = (TextView) othersItemView
						.findViewById(R.id.remark_list_item_first_content);
				othersViewHolder.tvReadAll = (TextView) othersItemView
						.findViewById(R.id.remark_list_item_first_read_all);
				othersViewHolder.imgContent = (ImageView) othersItemView
						.findViewById(R.id.remark_list_item_first_img_content);
				othersViewHolder.imgSpeaker = (ImageView) othersItemView
						.findViewById(R.id.remark_list_item_first_img_speaker);
				othersItemView.setTag(othersViewHolder);
			} else {
				othersViewHolder = (OthersViewHolder) othersItemView.getTag();
			}
			othersViewHolder.tvFirstTitle.setText("标题" + (position + 1));
			othersViewHolder.tvFirstContent
					.setText("Eclipse ADT 是 Eclipse 平台下用来开发 Android 应用程序的插件。 在线安装地址:https://d... rezerwar 益智游戏 DroidDraw logo DroidDraw Android组件界面设计");
			othersViewHolder.tvReadAll.setOnClickListener(this);
			othersViewHolder.imgContent.setOnClickListener(this);
			othersViewHolder.imgSpeaker.setOnClickListener(this);
			convertView = othersItemView;
		}
		return convertView;
	}

	/**
	 * list第一类itemviewholder
	 * 
	 * @author ChunfaLee
	 *
	 */
	public class FirstItemViewHolder {
		private TextView tvFirstTitle;
		private TextView tvFirstContent;
		private TextView tvReadAll;
		private ImageView imgContent;
		private ImageView imgSpeaker;
	}

	/**
	 * list第二类itemviewholder
	 * 
	 * @author ChunfaLee
	 *
	 */
	public class OthersViewHolder {
		private TextView tvFirstTitle;
		private TextView tvFirstContent;
		private TextView tvReadAll;
		private ImageView imgContent;
		private ImageView imgSpeaker;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 阅读全文
		case R.id.remark_list_item_first_read_all:
			T.showShort(context, "阅读全文...");
			break;
		// 声音文件
		case R.id.remark_list_item_first_img_speaker:
			T.showShort(context, "声音文件...");
			break;
		// 图片类容
		case R.id.remark_list_item_first_img_content:
			T.showShort(context, "图片类容...");
			break;
		default:
			break;
		}
	}
}
