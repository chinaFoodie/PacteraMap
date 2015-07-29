package com.pactera.pacteramap.adapter;

import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.holder.PMNotificationHubHolder;
import com.pactera.pacteramap.business.PMAdapterCallback;

/**
 * 消息中心
 * @author WMF
 *
 */
public class PMNotificationHubAdapter extends
		ArrayAdapter<HashMap<String, Object>> {

	private PMAdapterCallback callback;
	
	public PMNotificationHubAdapter(Context context, int textViewResourceId,
			List<HashMap<String, Object>> objects) {
		super(context, textViewResourceId, objects);
	}
  
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PMNotificationHubHolder holder;
		if(convertView==null){
	    	convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_hub_list_item, null);
	    	holder=new PMNotificationHubHolder();
	    	holder.id=(TextView) convertView.findViewById(R.id.notification_hub_list_item_id);
	    	holder.unread=(ImageView) convertView.findViewById(R.id.notification_hub_list_item_unread);
	    	holder.datetime=(TextView) convertView.findViewById(R.id.notification_hub_list_item_datetime);
	    	holder.title=(TextView) convertView.findViewById(R.id.notification_hub_list_item_title);
	    	holder.body=(TextView) convertView.findViewById(R.id.notification_hub_list_item_body);
	    	holder.right=(ImageView) convertView.findViewById(R.id.notification_hub_list_item_right);
			convertView.setTag(holder);
		}else{
			holder=(PMNotificationHubHolder)convertView.getTag();
		}
        HashMap<String, Object> item=this.getItem(position);
        holder.id.setText(item.get("nid").toString());
        if(item.get("nread").equals("1")){
        	holder.unread.setImageResource(R.drawable.iconfontyoujian);
        }else{
        	holder.unread.setImageResource(R.drawable.iconfontxinfengdakai);
        }
        //holder.datetime.setText(item.get("ndatetime").toString());
        //holder.title.setText(item.get("ntitle").toString());
        //holder.body.setText(item.get("nbody").toString());
        holder.right.setOnTouchListener(rightTouch);
        return convertView;
	}
	/**
	 * rightTouch事件
	 */
	private OnTouchListener rightTouch = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			ImageView image=(ImageView)v;
			View parent = (View) image.getParent();
			TextView tId= (TextView)parent.findViewById(R.id.notification_hub_list_item_id);
			int id=Integer.parseInt(tId.getText().toString());
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				image.setImageResource(R.drawable.iconfontright_down);
				if(callback!=null){
					callback.down(v,id);
				}
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				image.setImageResource(R.drawable.iconfontright);
				if(callback!=null){
					callback.up(v,id);
				}
			} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
				image.setImageResource(R.drawable.iconfontright);
			}
			return true;
		}
	};

	public void setCallback(PMAdapterCallback callback) {
		this.callback = callback;
	}
}
