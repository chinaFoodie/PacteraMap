package com.pactera.pacteramap.view.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.mapinterface.PMLocationCommand;
import com.pactera.pacteramap.mapinterface.PMLocationInterface;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.CustomProgress;

/**
 * 路径规划界面
 * 
 * @author ChunfaLee
 * @create 2015年7月16日15:48:47
 *
 */
public class PMRoutePlanActivity extends PMActivity implements OnClickListener,
		OnGetRoutePlanResultListener, OnMapClickListener, PMLocationInterface {
	// 浏览路线节点相关
	int nodeIndex = -1;// 节点索引,供浏览节点时使用
	@SuppressWarnings("rawtypes")
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;
	private TextView popupText, mTvPre, mTvNext, tvTitle;// 泡泡view
	// 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null; // 地图View
	BaiduMap mBaidumap = null;
	// 搜索相关
	private RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private LinearLayout llBack;
	private EditText editSt, editEn;
	private CustomProgress mProgress;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 90501:
				editSt.setText(msg.obj.toString());
				editSt.setSelection(msg.obj.toString().length());
				PMLocationCommand.mLocationClient.stop();
				break;
			default:
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route_plan_activity);
		init();
	}

	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("路线规划");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		// 初始化地图
		mMapView = (MapView) findViewById(R.id.route_plan_map);
		mBaidumap = mMapView.getMap();
		mTvPre = (TextView) findViewById(R.id.tv_route_plan_pre);
		mTvNext = (TextView) findViewById(R.id.tv_route_plan_next);
		mTvPre.setVisibility(View.INVISIBLE);
		mTvNext.setVisibility(View.INVISIBLE);
		// 处理搜索按钮响应
		editSt = (EditText) findViewById(R.id.et_route_plan_start);
		editEn = (EditText) findViewById(R.id.et_route_plan_end);
		findViewById(R.id.img_route_plan_start).setOnClickListener(this);
		mProgress = new CustomProgress(this, "请稍后...");
		// 地图点击事件处理
		int count = mMapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = mMapView.getChildAt(i);
			if (child instanceof ZoomControls || child instanceof ImageView) {
				child.setVisibility(View.INVISIBLE);
			}
		}
		MapStatus mMapStatus = new MapStatus.Builder().target(
				new LatLng(30.554175, 104.076187)).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		mBaidumap.setMapStatus(mMapStatusUpdate);
		mBaidumap.setOnMapClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
	}

	/**
	 * 发起路线规划搜索
	 *
	 * @param v
	 */
	public void SearchButtonProcess(View v) {
		// 重置浏览节点的路线数据
		mProgress.show();
		route = null;
		mTvPre.setVisibility(View.INVISIBLE);
		mTvNext.setVisibility(View.INVISIBLE);
		mBaidumap.clear();
		// 设置起终点信息，对于tranist search 来说，城市名无意义
		PlanNode stNode = PlanNode.withCityNameAndPlaceName("成都", editSt
				.getText().toString());
		PlanNode enNode = PlanNode.withCityNameAndPlaceName("成都", editEn
				.getText().toString());

		// 实际使用中请对起点终点城市进行正确的设定
		if (v.getId() == R.id.route_plan_car) {
			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode)
					.to(enNode));
		} else if (v.getId() == R.id.route_plan_bus) {
			mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode)
					.city("成都").to(enNode));
		} else if (v.getId() == R.id.route_plan_walk) {
			mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode)
					.to(enNode));
		}
	}

	/**
	 * 节点浏览
	 *
	 * @param v
	 */
	public void nodeClick(View v) {
		if (route == null || route.getAllStep() == null) {
			return;
		}
		if (nodeIndex == -1 && v.getId() == R.id.tv_route_plan_pre) {
			return;
		}
		// 设置节点索引
		if (v.getId() == R.id.tv_route_plan_next) {
			if (nodeIndex < route.getAllStep().size() - 1) {
				nodeIndex++;
			} else {
				return;
			}
		} else if (v.getId() == R.id.tv_route_plan_pre) {
			if (nodeIndex > 0) {
				nodeIndex--;
			} else {
				return;
			}
		}
		// 获取节结果信息
		LatLng nodeLocation = null;
		String nodeTitle = null;
		Object step = route.getAllStep().get(nodeIndex);
		if (step instanceof DrivingRouteLine.DrivingStep) {
			nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance()
					.getLocation();
			nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
		} else if (step instanceof WalkingRouteLine.WalkingStep) {
			nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance()
					.getLocation();
			nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
		} else if (step instanceof TransitRouteLine.TransitStep) {
			nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance()
					.getLocation();
			nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
		}

		if (nodeLocation == null || nodeTitle == null) {
			return;
		}
		// 移动节点至中心
		mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
		// show popup
		popupText = new TextView(PMRoutePlanActivity.this);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setTextColor(0xFF000000);
		popupText.setText(nodeTitle);
		mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
	}

	/**
	 * 切换路线图标，刷新地图使其生效 注意： 起终点图标使用中心对齐.
	 */
	public void changeRouteIcon(View v) {
		if (routeOverlay == null) {
			return;
		}
		if (useDefaultIcon) {
			((Button) v).setText("自定义起终点图标");
			Toast.makeText(this, "将使用系统起终点图标", Toast.LENGTH_SHORT).show();

		} else {
			((Button) v).setText("系统起终点图标");
			Toast.makeText(this, "将使用自定义起终点图标", Toast.LENGTH_SHORT).show();

		}
		useDefaultIcon = !useDefaultIcon;
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	/******** 步行路径规划 ******/
	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		mProgress.dismiss();
		// }
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			result.getSuggestAddrInfo();
			/********* 利用回调规划路径 ************/
			jumpToChoicePostion(result);
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mTvPre.setVisibility(View.VISIBLE);
			mTvNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	private void jumpToChoicePostion(WalkingRouteResult result) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < result.getSuggestAddrInfo().getSuggestEndNode()
				.size(); i++) {
			if (!result.getSuggestAddrInfo().getSuggestEndNode().get(i).name
					.endsWith(editEn.getText().toString())) {
				temp.add(result.getSuggestAddrInfo().getSuggestEndNode().get(i).name);
			}
		}
		Intent intent = new Intent(PMRoutePlanActivity.this,
				PMChoicePositionActivity.class);
		intent.putStringArrayListExtra("list_position", temp);
		startActivityForResult(intent, 100);
	}

	/******** 公交路径规划 ******/
	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {
		mProgress.dismiss();
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			result.getSuggestAddrInfo();
			/********* 利用回调规划路径 ************/
			jumpToChoicePostion(result);
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mTvPre.setVisibility(View.VISIBLE);
			mTvNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	/******** 驾车路径规划 ******/
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		mProgress.dismiss();
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			result.getSuggestAddrInfo();
			/********* 利用回调规划路径 ************/
			jumpToChoicePostion(result);
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mTvPre.setVisibility(View.VISIBLE);
			mTvNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
			routeOverlay = overlay;
			mBaidumap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	private void jumpToChoicePostion(DrivingRouteResult result) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < result.getSuggestAddrInfo().getSuggestEndNode()
				.size(); i++) {
			if (!result.getSuggestAddrInfo().getSuggestEndNode().get(i).name
					.endsWith(editEn.getText().toString())) {
				temp.add(result.getSuggestAddrInfo().getSuggestEndNode().get(i).name);
			}
		}
		Intent intent = new Intent(PMRoutePlanActivity.this,
				PMChoicePositionActivity.class);
		intent.putStringArrayListExtra("list_position", temp);
		startActivityForResult(intent, 100);
	}

	/************************ 跳转到选择终点位置界面 *************************/
	private void jumpToChoicePostion(TransitRouteResult result) {
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < result.getSuggestAddrInfo().getSuggestEndNode()
				.size(); i++) {
			if (!result.getSuggestAddrInfo().getSuggestEndNode().get(i).name
					.endsWith(editEn.getText().toString())) {
				temp.add(result.getSuggestAddrInfo().getSuggestEndNode().get(i).name);
			}
		}
		Intent intent = new Intent(PMRoutePlanActivity.this,
				PMChoicePositionActivity.class);
		intent.putStringArrayListExtra("list_position", temp);
		PMRoutePlanActivity.this.startActivityForResult(intent, 100);
	}

	/************************ 跳转到选择终点位置界面 *************************/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (100 == requestCode) {
			if (00002 == resultCode) {
				Bundle b = data.getExtras();
				editEn.setText(b.getString("position"));
			}
		}
	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	@Override
	public void onMapClick(LatLng point) {
		mBaidumap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		mMapView.onDestroy();
		super.onDestroy();
	}

	@Override
	public void locationCallBack(Object value) {
		BDLocation bdLocation = (BDLocation) value;
		Message msg = new Message();
		msg.what = 90501;
		msg.obj = bdLocation.getStreet();
		handler.sendMessage(msg);
		mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(
				Double.valueOf(bdLocation.getLatitude()), Double
						.valueOf(bdLocation.getLongitude()))));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMRoutePlanActivity.this.finish();
			break;
		case R.id.img_route_plan_start:
			new PMLocationCommand(this).execute(this);
			break;
		default:
			break;
		}
	}
}
