package com.pactera.pacteramap.adapter.message;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.vo.Expression;

/**
 * 表情列表
 * 
 * @author ChunfaLee
 *
 */
public class ExpressionImageAdapter extends BaseAdapter {
	List<Expression> expressionList;
	Context context;
	LayoutInflater lfInflater;

	public ExpressionImageAdapter(Context context,
			List<Expression> expressionList) {
		this.context = context;
		this.expressionList = expressionList;
		lfInflater = LayoutInflater.from(this.context);
	}

	public int getCount() {
		return expressionList.size();
	}

	public Object getItem(int position) {
		return expressionList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Expression exp = expressionList.get(position);
		if (convertView == null) {
			convertView = lfInflater.inflate(R.layout.expression_list_item,
					null);
			holder = new ViewHolder();
			holder.iv_id = (ImageView) convertView.findViewById(R.id.iv_id);
			holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 删除
		if (exp.getDrableId() == -1) {
			holder.iv_id.setBackgroundResource(R.drawable.chat_del);
		} else {
			holder.iv_id.setBackgroundResource(exp.getDrableId());
		}
		holder.tv_id.setText(exp.getCode());
		return convertView;
	}

	class ViewHolder {
		ImageView iv_id;
		TextView tv_id;
	}
}