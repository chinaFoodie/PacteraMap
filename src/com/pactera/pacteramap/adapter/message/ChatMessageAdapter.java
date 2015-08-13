package com.pactera.pacteramap.adapter.message;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.config.PMShareKey;
import com.pactera.pacteramap.sqlite.litepal.bean.MessageBean;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.view.ui.PMMessageDetailsActivity;

/**
 * 聊天列表适配器
 * 
 * @author ChunfaLee
 * @create 2015年8月13日09:54:08
 * @version 1.00
 *
 */
public class ChatMessageAdapter extends BaseAdapter {
	private Context context;
	private List<MessageBean> listMsg;
	private LayoutInflater lfInflater;
	private int expresWh = -1;
	private PMSharePreferce share;

	public ChatMessageAdapter(Context context, List<MessageBean> listMsg) {
		this.context = context;
		this.listMsg = listMsg;
		lfInflater = LayoutInflater.from(this.context);
		share = PMSharePreferce.getInstance(context);
		expresWh = (int) this.context.getResources().getDimension(
				R.dimen.chat_expression_wh);
	}

	@Override
	public int getCount() {
		return listMsg.size();
	}

	@Override
	public Object getItem(int position) {
		return listMsg.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MessageBean mb = listMsg.get(position);
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = lfInflater.inflate(R.layout.chat_msg_listview_item,
					null);
			holder = new ViewHolder();
			holder.rl_msg_friend = (RelativeLayout) convertView
					.findViewById(R.id.rl_msg_friend);
			holder.rl_msg_mine = (RelativeLayout) convertView
					.findViewById(R.id.rl_msg_mine);
			holder.raiv_faceico_friend = (ImageView) convertView
					.findViewById(R.id.raiv_faceico_friend);
			holder.raiv_faceico_mine = (ImageView) convertView
					.findViewById(R.id.raiv_faceico_mine);
			holder.tv_msg_content_friend = (TextView) convertView
					.findViewById(R.id.tv_msg_content_friend);
			holder.tv_msg_content_mine = (TextView) convertView
					.findViewById(R.id.tv_msg_content_mine);
			holder.tv_mine_nickname = (TextView) convertView
					.findViewById(R.id.tv_mine_nickname);
			holder.tv_friend_nickname = (TextView) convertView
					.findViewById(R.id.tv_friend_nickname);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String content = msgConvert(replaceSpaceToCode(mb.getMsgContent()));
		if (mb.getMsgFrom().equals(share.getString(PMShareKey.USERNAME))) {
			holder.rl_msg_friend.setVisibility(View.GONE);
			holder.rl_msg_mine.setVisibility(View.VISIBLE);
			holder.raiv_faceico_mine.setBackgroundResource(R.drawable.user);
			holder.tv_msg_content_mine.setText(Html.fromHtml(content,
					imageGetterResource, null));
			// holder.tv_msg_content_mine.setText(content);
			holder.tv_mine_nickname.setText(mb.getMsgFrom());
		} else {
			holder.rl_msg_friend.setVisibility(View.VISIBLE);
			holder.rl_msg_mine.setVisibility(View.GONE);
			holder.raiv_faceico_friend.setBackgroundResource(R.drawable.user);
			holder.tv_msg_content_friend.setText(Html.fromHtml(content,
					imageGetterResource, null));
			// holder.tv_msg_content_friend.setText(content);
			holder.tv_friend_nickname.setText(mb.getMsgFrom());
		}
		return convertView;
	}

	class ViewHolder {
		RelativeLayout rl_msg_mine, rl_msg_friend;// 显示消息的外层布局
		ImageView raiv_faceico_mine, raiv_faceico_friend;// 头像
		TextView tv_mine_nickname, tv_friend_nickname;// 昵称
		TextView tv_msg_content_mine, tv_msg_content_friend;// 消息内容
	}

	private String msgConvert(String content) {
		for (int i = 0; i < PMMessageDetailsActivity.expressionList.size(); i++) {
			content = content
					.replace(
							PMMessageDetailsActivity.expressionList.get(i).code,
							"<img src=\""
									+ PMMessageDetailsActivity.expressionList
											.get(i).drableId + "\" />");
		}
		return content;
	}

	final Html.ImageGetter imageGetterResource = new Html.ImageGetter() {
		@SuppressWarnings("deprecation")
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			int rId = Integer.parseInt(source);
			drawable = context.getResources().getDrawable(rId);
			drawable.setBounds(0, 0, expresWh, expresWh);// 设置显示的图像大小
			return drawable;
		};
	};

	/** 替换空格 */
	public static String replaceSpaceToCode(String str) {
		String rt = str.replace(" ", "&nbsp;");
		rt = rt.replace("\n", "<br/>");
		return rt;
	}
}
