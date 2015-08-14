package com.pactera.pacteramap.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pactera.pacteramap.R;

/**
 * 选择终点位置适配器
 * 
 * @author ChunfaLee
 * @create 2015年8月14日17:07:17
 * @version 1.00
 *
 */
public class PMChoicePositionAdapter extends BaseAdapter {
	private Context context;
	private List<String> listPosition;

	public PMChoicePositionAdapter(Context context, List<String> listPostion) {
		this.context = context;
		this.listPosition = listPostion;
	}

	@Override
	public int getCount() {
		return listPosition.size();
	}

	@Override
	public Object getItem(int position) {
		return listPosition.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.choice_position_item, null);
			holder = new ViewHolder();
			holder.tvPosition = (TextView) convertView
					.findViewById(R.id.tv_choice_position);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvPosition.setText(listPosition.get(position));
		return convertView;
	}

	public class ViewHolder {
		private TextView tvPosition;
	}

}
