package com.pactera.pacteramap.view.ui;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMTrackPointAdapter;
import com.pactera.pacteramap.business.database.bean.WorkTrack;
import com.pactera.pacteramap.business.database.dao.DaoMaster;
import com.pactera.pacteramap.business.database.dao.DaoMaster.DevOpenHelper;
import com.pactera.pacteramap.business.database.dao.DaoSession;
import com.pactera.pacteramap.business.database.dao.WorkTrackDao;
import com.pactera.pacteramap.business.database.dao.WorkTrackDao.Properties;
import com.pactera.pacteramap.business.view.ui.worktrack.PMWorkTrackCommand;
import com.pactera.pacteramap.config.PMShareKey;
import com.pactera.pacteramap.util.L;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.util.PMUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.PMCalendar;
import com.pactera.pacteramap.view.component.PMCalendar.OnCalendarClickListener;
import com.pactera.pacteramap.vo.PMWorkTrack;
import com.pactera.pacteramap.vo.PMWorkTrack.AddressInfo;

/**
 * 工作轨迹
 * 
 * @author Chunfa Lee
 * @create 2015年6月17日13:49:08
 *
 */
public class PMWorkTrackActivity extends PMActivity implements OnClickListener,
		OnMapClickListener, OnCalendarClickListener, OnItemClickListener,
		OnMarkerClickListener {
	private TextView tvTitle, tvMoreRight;
	private LinearLayout llBack, llMore;
	private ImageView img2Remark;
	private MapView mapView;
	private PMCalendar pMCalendar;
	private String date = null;// 设置默认选中的日期 格式为 “2015-06-18” 标准DATE格式
	private BaiduMap mBaiduMap;
	private BitmapDescriptor mBitmap;
	private List<LatLng> list;
	private ListView lvTrackPoint;
	private List<AddressInfo> listTrack = new ArrayList<AddressInfo>();
	private PMTrackPointAdapter tAdapter;
	private View markerLayout;
	private PMSharePreferce share;
	private DaoSession daoSession;
	private WorkTrackDao workTrackDao;
	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private List<WorkTrack> listWorkTrack;
	private Boolean isStartRun = false;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 9000:
				T.showShort(PMWorkTrackActivity.this, "没有轨迹路线");
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_track_activity);
		date = PMUtil.getCurrentDate();
		share = PMSharePreferce.getInstance(this);
		// 创建对象
		DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "jredu.db",
				null);
		db = helper.getWritableDatabase();
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		workTrackDao = daoSession.getWorkTrackDao();
		init();
	}

	/** 初始化视图 **/
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("工作轨迹");
		tvMoreRight = (TextView) findViewById(R.id.tv_base_right);
		tvMoreRight.setText("播放");
		pMCalendar = (PMCalendar) findViewById(R.id.work_track_kcalendar);
		lvTrackPoint = (ListView) findViewById(R.id.lv_location_point);
		lvTrackPoint.setOnItemClickListener(this);
		pMCalendar.setOnCalendarClickListener(this);
		/** 签到日期标记列表 start ***/
		List<String> list = new ArrayList<String>(); // 设置标记列表
		list.add("2015-06-01");
		list.add("2015-06-08");
		/** 签到日期标记列表 end ***/
		pMCalendar.addMarks(list, 0);
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		llMore = (LinearLayout) findViewById(R.id.ll_tv_base_right);
		llMore.setVisibility(View.VISIBLE);
		llMore.setOnClickListener(this);
		img2Remark = (ImageView) findViewById(R.id.img_go_to_remark);
		img2Remark.setOnClickListener(this);
		mapView = (MapView) findViewById(R.id.work_track_map);
		// 去掉百度自带的ZoomControls控件
		int count = mapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = mapView.getChildAt(i);
			if (child instanceof ZoomControls || child instanceof ImageView) {
				child.setVisibility(View.INVISIBLE);
			}
		}
		mapView.getMap().setOnMapClickListener(this);
		MapStatus mMapStatus = new MapStatus.Builder()
				.target(new LatLng(30.554175, 104.076187)).zoom(18).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		mBaiduMap = mapView.getMap();
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		mBaiduMap.setOnMarkerClickListener(this);
		markerLayout = LayoutInflater.from(this).inflate(
				R.layout.baidu_map_marker_layout, null);
		getTrackPoints();
	}

	/** 请求工作轨迹 */
	private void getTrackPoints() {
		new PMWorkTrackCommand().execute(this, getTrackParams("2015-06-18"));
	}

	/** 请求查询工作轨迹参数 */
	private Object getTrackParams(String date) {
		RequestParams params = new RequestParams();
		params.add("IMEINum", PMApplication.getInstance().getImei());
		params.add("trackTime", date);
		return params;
	}

	/** 轨迹回放线程 */
	private void worckTrackBack() {
		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				drawMapLine();
			}
		});
		newThread.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMWorkTrackActivity.this.finish();
			break;
		case R.id.img_go_to_remark:
			PMActivityUtil.next(this, PMRemarkActivity.class);
			break;
		// 保存运动轨迹到本地数据库
		case R.id.ll_tv_base_right:
			mBaiduMap.clear();
			isStartRun = true;
			worckTrackBack();
			break;
		default:
			break;
		}
	}

	@Override
	public void onMapClick(LatLng latLng) {
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		OverlayOptions oofirst = new MarkerOptions().position(latLng)
				.icon(bitmap).zIndex(9).draggable(false);
		mBaiduMap.addOverlay(oofirst);
		WorkTrack workTrack = new WorkTrack();
		workTrack.setDate(date);
		workTrack.setDesc("这是我的工作轨迹点");
		workTrack.setIsMark("1");
		workTrack.setLatitude(latLng.latitude + "");
		workTrack.setLongitude(latLng.longitude + "");
		workTrack.setMarkIndex("1");
		workTrack.setUserImei(PMApplication.getInstance().getImei());
		workTrack.setUserName(share.getString(PMShareKey.USERNAME));
		long workTrackId = workTrackDao.insert(workTrack);
		L.e("插入数据成功返回的ID" + workTrackId + "/n" + workTrack.getDate());
	}

	/** 获取地图上的工作轨迹点 */
	private List<LatLng> getWorkTrackLatlng() {
		listWorkTrack = workTrackDao.queryBuilder()
				.where(Properties.Date.eq(date)).orderAsc(Properties.Id).list();
		list = new ArrayList<LatLng>();
		if (listWorkTrack != null && listWorkTrack.size() > 0) {
			for (WorkTrack workTrack : listWorkTrack) {
				list.add(new LatLng(Double.valueOf(workTrack.getLatitude()),
						Double.valueOf(workTrack.getLongitude())));
			}
		}
		return list;
	}

	@Override
	public boolean onMapPoiClick(MapPoi mapPoi) {
		return false;
	}

	/***
	 * 服务端数据回调接口
	 */
	@Override
	public void CallBack(Object value) {
		super.CallBack(value);
		PMWorkTrack wTrack = PMGsonUtil.getPerson(value.toString(),
				PMWorkTrack.class);
		if (wTrack != null) {
			listTrack = wTrack.data.get(0).addressInfo;
			tAdapter = new PMTrackPointAdapter(this, listTrack);
			lvTrackPoint.setAdapter(tAdapter);
		} else {
			T.showShort(this, "返回结果有误");
		}
	}

	@Override
	public void onCalendarClick(int row, int col, String dateFormat) {
		int month = Integer.parseInt(dateFormat.substring(
				dateFormat.indexOf("-") + 1, dateFormat.lastIndexOf("-")));
		if (pMCalendar.getCalendarMonth() - month == 1// 跨年跳转
				|| pMCalendar.getCalendarMonth() - month == -11) {
			pMCalendar.lastMonth();
		} else if (month - pMCalendar.getCalendarMonth() == 1 // 跨年跳转
				|| month - pMCalendar.getCalendarMonth() == -11) {
			pMCalendar.nextMonth();
		} else {
			// 点击日历请求路线轨迹
			pMCalendar.removeAllBgColor();
			mBaiduMap.clear();
			isStartRun = true;
			mapView.invalidate();
			pMCalendar.setCalendarDayBgColor(dateFormat,
					R.drawable.calendar_date_focused);
			date = dateFormat;// 最后返回给全局 date
			worckTrackBack();
		}
	}

	/** 绘制地图路径 ***/
	private void drawMapLine() {
		getWorkTrackLatlng();
		if (list != null && list.size() > 0) {
			OverlayOptions polygonOption = new PolylineOptions().points(list)
					.color(Color.RED);
			for (int i = 0; i < list.size(); i++) {
				TextView tvIndex = (TextView) markerLayout
						.findViewById(R.id.tv_baidu_marker_index);
				if (i == 0) {
					tvIndex.setText("起");
				} else if (i == list.size() - 1) {
					tvIndex.setText("终");
				} else {
					tvIndex.setText("" + i);
				}
				mBitmap = BitmapDescriptorFactory.fromView(markerLayout);
				OverlayOptions oofirst = new MarkerOptions()
						.position(list.get(i)).icon(mBitmap).zIndex(9)
						.draggable(false);
				mBaiduMap.addOverlay(oofirst);
				mBaiduMap.addOverlay(polygonOption);
				if (isStartRun) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			handler.sendEmptyMessage(9000);
		}
		isStartRun = false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		T.showShort(PMWorkTrackActivity.this,
				position + "   " + "纬度：" + listTrack.get(position).latitude
						+ "\n" + "经度：" + listTrack.get(position).longitude);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		T.showShort(this, marker.getPosition().latitude + "");
		return false;
	}

}
