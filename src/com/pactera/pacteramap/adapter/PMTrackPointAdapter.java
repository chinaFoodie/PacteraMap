package com.pactera.pacteramap.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.ui.PMRemarkActivity;
import com.pactera.pacteramap.vo.PMWorkTrack.AddressInfo;

/**
 * 工作轨迹点适配器
 * 
 * @author ChunfaLee
 * @create 2015年6月18日17:15:19
 *
 */
public class PMTrackPointAdapter extends BaseAdapter {
	private Context context;
	private List<AddressInfo> list;

	public PMTrackPointAdapter(Context context, List<AddressInfo> list) {
		this.context = context;
		this.list = list;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.track_point_item, null);
			holder = new ViewHolder();
			holder.tvTrackInfo = (TextView) convertView
					.findViewById(R.id.tv_track_point_info);
			holder.imgRemark = (ImageView) convertView
					.findViewById(R.id.img_track_go_to_remark);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTrackInfo.setText((position + 1) + ":"
				+ list.get(position).addr_str);
		if (position % 2 == 1) {
			holder.imgRemark.setVisibility(View.VISIBLE);
		} else {
			holder.imgRemark.setVisibility(View.GONE);
		}
		holder.imgRemark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(context, PMRemarkActivity.class);
				context.startActivity(it);
				T.showShort(context, "备忘录...");
			}
		});
		return convertView;
	}

	public class ViewHolder {
		private TextView tvTrackInfo;
		private ImageView imgRemark;
	}

}
