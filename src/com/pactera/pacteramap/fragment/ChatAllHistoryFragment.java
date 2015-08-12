package com.pactera.pacteramap.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.base.BaseFragment;
import com.pactera.pacteramap.config.PMShareKey;
import com.pactera.pacteramap.sqlite.litepal.bean.MessageBean;
import com.pactera.pacteramap.util.PMDateUtil;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.view.component.AlertDialog;
import com.pactera.pacteramap.view.component.PullToRefreshSwipeMenuListView;
import com.pactera.pacteramap.view.component.PullToRefreshSwipeMenuListView.IXListViewListener;
import com.pactera.pacteramap.view.component.PullToRefreshSwipeMenuListView.OnMenuItemClickListener;
import com.pactera.pacteramap.view.component.PullToRefreshSwipeMenuListView.OnSwipeListener;
import com.pactera.pacteramap.view.component.pulltorefresh.RefreshTime;
import com.pactera.pacteramap.view.component.swipemenu.SwipeMenu;
import com.pactera.pacteramap.view.component.swipemenu.SwipeMenuCreator;
import com.pactera.pacteramap.view.component.swipemenu.SwipeMenuItem;
import com.pactera.pacteramap.view.ui.PMMessageDetailsActivity;

/**
 * 消息历史记录界面
 * 
 * @author ChunfaLee
 * @create 2015年8月11日10:29:11
 *
 */
public class ChatAllHistoryFragment extends BaseFragment implements
		OnClickListener, IXListViewListener {
	private List<MessageBean> listMsg, listFrom;
	private MessageAdapter mAdapter;
	private PullToRefreshSwipeMenuListView mListView;
	private Handler mHandler;
	private TextView tvTitle;
	private LinearLayout llBack, llRemove;
	private ImageView imgRemove;
	private RelativeLayout rlNoMsg;
	private PMSharePreferce share;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_chat_history, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		share = PMSharePreferce.getInstance(activity);
		init();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/** 初始化视图 **/
	private void init() {
		tvTitle = (TextView) getView().findViewById(R.id.tv_mid_title);
		tvTitle.setText("消息");
		llBack = (LinearLayout) getView().findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		llRemove = (LinearLayout) getView()
				.findViewById(R.id.ll_img_base_right);
		llRemove.setVisibility(View.VISIBLE);
		llRemove.setOnClickListener(this);
		rlNoMsg = (RelativeLayout) getView().findViewById(
				R.id.rl_no_message_center);
		imgRemove = (ImageView) getView().findViewById(R.id.img_base_right);
		imgRemove.setImageResource(R.drawable.pactera_remove);
		/** 获取消息中心数据，这里是根据当前注册用户获取显示 **/
		listMsg = getMessage();
		mListView = (PullToRefreshSwipeMenuListView) getView().findViewById(
				R.id.ptrlv_message_center);
		mAdapter = new MessageAdapter();
		if (listMsg.size() > 0) {
			rlNoMsg.setVisibility(View.GONE);
			mListView.setVisibility(View.VISIBLE);
		} else {
			mListView.setVisibility(View.GONE);
			rlNoMsg.setVisibility(View.VISIBLE);
		}
		mListView.setAdapter(mAdapter);
		mListView.setPullRefreshEnable(true);
		mListView.setPullLoadEnable(true);
		mListView.setXListViewListener(this);
		mHandler = new Handler();

		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem openItem = new SwipeMenuItem(activity);
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				openItem.setWidth(dp2px(90));
				openItem.setTitle("Open");
				openItem.setTitleSize(18);
				openItem.setTitleColor(Color.WHITE);
				menu.addMenuItem(openItem);
				SwipeMenuItem deleteItem = new SwipeMenuItem(activity);
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setIcon(R.drawable.ic_delete);
				menu.addMenuItem(deleteItem);
			}
		};
		mListView.setMenuCreator(creator);
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				MessageBean item = listMsg.get(position);
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
					delete(item);
					listMsg.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
			}
		});

		// set SwipeListener
		mListView.setOnSwipeListener(new OnSwipeListener() {

			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}

			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
		// other setting
		// listView.setCloseInterpolator(new BounceInterpolator());
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent msgIntent = new Intent();
				msgIntent.setClass(activity, PMMessageDetailsActivity.class);
				msgIntent.putExtra("chat_name", listMsg.get(position - 1)
						.getMsgFrom());
				startActivity(msgIntent);
			}
		});
	}

	private List<MessageBean> getMessage() {
		listMsg = DataSupport
				.where("msgTo = ?", share.getString(PMShareKey.USERNAME))
				.order("msgDate desc").find(MessageBean.class);
		listFrom = DataSupport
				.where("msgFrom = ?", share.getString(PMShareKey.USERNAME))
				.order("msgDate desc").find(MessageBean.class);
		listMsg.addAll(listFrom);
		return listMsg;
	}

	private void onLoad() {
		mListView.setRefreshTime(RefreshTime.getRefreshTime(activity));
		mListView.stopRefresh();
		mListView.stopLoadMore();
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm");
				RefreshTime.setRefreshTime(activity, df.format(new Date()));
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				onLoad();
			}
		}, 2000);
	}

	private void delete(MessageBean item) {
		// delete app
	}

	private void open(MessageBean item) {
		// open app
	}

	class MessageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listMsg.size();
		}

		@Override
		public MessageBean getItem(int position) {
			return listMsg.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(activity,
						R.layout.message_center_item, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			MessageBean item = getItem(position);
			if (share.getString(PMShareKey.USERNAME).equals(item.getMsgFrom())) {
				holder.tv_name.setText(item.getMsgTo());
			} else {
				holder.tv_name.setText(item.getMsgFrom());
			}
			holder.tv_content.setText(item.getMsgContent());
			holder.tv_date
					.setText(PMDateUtil.getStandardDate(item.getMsgDate()));
			return convertView;
		}

		class ViewHolder {
			TextView tv_name, tv_content, tv_date;

			public ViewHolder(View view) {
				tv_name = (TextView) view.findViewById(R.id.tv_name);
				tv_content = (TextView) view.findViewById(R.id.tv_content);
				tv_date = (TextView) view.findViewById(R.id.tv_date);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ll_tv_base_left:
			activity.finish();
			break;
		case R.id.ll_img_base_right:
			removeAll();
			break;
		default:
			break;
		}
	}

	/** 清空消息 */
	private void removeAll() {
		new AlertDialog(activity).builder().setTitle("清空全部")
				.setMsg("清空之后不能恢复，确定清空？")
				.setPositiveButton("确认", new OnClickListener() {
					@Override
					public void onClick(View v) {
						listMsg.removeAll(listMsg);
						mAdapter.notifyDataSetChanged();
					}
				}).setNegativeButton("取消", new OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				}).show();
	}
}
