package com.pactera.pacteramap.view.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
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
import com.baidu.mapapi.map.BaiduMap.OnMapDrawFrameCallback;
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
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMTrackPointAdapter;
import com.pactera.pacteramap.business.view.ui.worktrack.PMWorkTrackCommand;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMGsonUtil;
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
public class NoUsePMWorkTrackActivity extends PMActivity implements OnClickListener,
		OnMapClickListener, OnCalendarClickListener, OnItemClickListener,
		OnMarkerClickListener {
	private TextView tvTitle, tvMoreRight;
	private LinearLayout llBack, llMore;
	private ImageView img2Remark;
	private MapView mapView;
	private PMCalendar pMCalendar;
	String date = null;// 设置默认选中的日期 格式为 “2015-06-18” 标准DATE格式
	private BaiduMap mBaiduMap;
	private Boolean firstClick = true;
	private BitmapDescriptor mBitmap;
	private Marker mMarker;
	private List<LatLng> latLngPolygon = new ArrayList<LatLng>();
	private List<LatLng> list;
	private float[] vertexs;
	private FloatBuffer vertexBuffer;
	private ListView lvTrackPoint;
	private List<AddressInfo> listTrack = new ArrayList<AddressInfo>();
	private PMTrackPointAdapter tAdapter;
	private View markerLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.work_track_activity);
		getLatlng();
		init();
	}

	/** 初始化视图 **/
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("工作轨迹");
		tvMoreRight = (TextView) findViewById(R.id.tv_base_right);
		tvMoreRight.setText("清除");
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
				.target(new LatLng(30.554175, 104.076187)).zoom(17).build();
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
			NoUsePMWorkTrackActivity.this.finish();
			break;
		case R.id.img_go_to_remark:
			PMActivityUtil.next(this, PMRemarkActivity.class);
			break;
		case R.id.ll_tv_base_right:
			mBaiduMap.clear();
			break;
		default:
			break;
		}
	}

	@Override
	public void onMapClick(LatLng latLng) {
		drawMapLine(latLng);
	}

	/** 获取地图上的工作轨迹点 */
	private List<LatLng> getLatlng() {
		list = new ArrayList<LatLng>();
		list.add(new LatLng(30.549255, 104.077306));
		list.add(new LatLng(30.553275, 104.073687));// 104.076928,30.548517
		list.add(new LatLng(30.551675, 104.072387));// 104.077575,30.548354
		list.add(new LatLng(30.556475, 104.078487));// 104.078321,30.548509
		list.add(new LatLng(30.557575, 104.074587));//
		return list;
	}

	private void drawMapLine(LatLng latLng) {
		if (firstClick) {
			mBitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_gcoding);
			OverlayOptions oofirst = new MarkerOptions().position(latLng)
					.icon(mBitmap).zIndex(9).draggable(false);
			mMarker = (Marker) (mBaiduMap.addOverlay(oofirst));
			LatLng llA = latLng;// 记录第一个点坐标
			latLngPolygon.add(llA);
			firstClick = false;
			mBaiduMap.setOnMapDrawFrameCallback(new MyDraw());
		} else {
			mBitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.icon_gcoding);
			OverlayOptions oo = new MarkerOptions().position(latLng)
					.icon(mBitmap).zIndex(9).draggable(false);
			mMarker = (Marker) (mBaiduMap.addOverlay(oo));
			mMarker.setTitle("我是marker");
			LatLng llB = latLng;
			latLngPolygon.add(llB);
		}
	}

	@Override
	public boolean onMapPoiClick(MapPoi mapPoi) {
		return false;
	}

	public class MyDraw implements OnMapDrawFrameCallback {

		@Override
		public void onMapDrawFrame(GL10 gl, MapStatus drawingMapStatus) {
			if (mBaiduMap.getProjection() != null) {
				calPolylinePoint(drawingMapStatus);
				drawPolyline(gl, Color.argb(255, 128, 123, 0), vertexBuffer,
						10, latLngPolygon.size(), drawingMapStatus);
				// drawTexture(gl, bitmap, drawingMapStatus);
			}
		}
	}

	// 计算折线 OpenGL 坐标
	public void calPolylinePoint(MapStatus mspStatus) {
		PointF[] polyPoints = new PointF[latLngPolygon.size()];
		vertexs = new float[3 * latLngPolygon.size()];
		int i = 0;
		for (LatLng xy : latLngPolygon) {
			// 将地理坐标转换成 openGL 坐标
			polyPoints[i] = mBaiduMap.getProjection().toOpenGLLocation(xy,
					mspStatus);
			vertexs[i * 3] = polyPoints[i].x;
			vertexs[i * 3 + 1] = polyPoints[i].y;
			vertexs[i * 3 + 2] = 0.0f;
			i++;
		}
		/** 只是为了打印点击地图上点的信息 */
		// for (int j = 0; j < vertexs.length; j++) {
		// L.e("vertexs[" + j + "]: " + vertexs[j]);
		// }
		vertexBuffer = makeFloatBuffer(vertexs);
	}

	// 创建OpenGL绘制时的顶点Buffer
	private FloatBuffer makeFloatBuffer(float[] fs) {
		ByteBuffer bb = ByteBuffer.allocateDirect(fs.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(fs);
		fb.position(0);
		return fb;
	}

	// 绘制折线
	private void drawPolyline(GL10 gl, int color, FloatBuffer lineVertexBuffer,
			float lineWidth, int pointSize, MapStatus drawingMapStatus) {

		gl.glEnable(GL10.GL_BLEND);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		float colorA = Color.alpha(color) / 255f;
		float colorR = Color.red(color) / 255f;
		float colorG = Color.green(color) / 255f;
		float colorB = Color.blue(color) / 255f;

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, lineVertexBuffer);
		gl.glColor4f(colorR, colorG, colorB, colorA);
		gl.glLineWidth(lineWidth);
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, pointSize);

		gl.glDisable(GL10.GL_BLEND);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
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
			pMCalendar.removeAllBgColor();
			mBaiduMap.clear();
			mapView.invalidate();
			pMCalendar.setCalendarDayBgColor(dateFormat,
					R.drawable.calendar_date_focused);
			date = dateFormat;// 最后返回给全局 date
			drawMapLine();
			// for (int i = 0; i < list.size(); i++) {
			// drawMapLine(list.get(i));
			// }
		}
	}

	/** 绘制地图路径 ***/
	private void drawMapLine() {
		getLatlng();
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
			OverlayOptions oofirst = new MarkerOptions().position(list.get(i))
					.icon(mBitmap).zIndex(9).draggable(false);
			mMarker = (Marker) (mBaiduMap.addOverlay(oofirst));
		}
		mBaiduMap.addOverlay(polygonOption);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		T.showShort(NoUsePMWorkTrackActivity.this,
				position + "   " + "纬度：" + listTrack.get(position).latitude
						+ "\n" + "经度：" + listTrack.get(position).longitude);
		drawMapLine(new LatLng(
				Double.valueOf(listTrack.get(position).latitude),
				Double.valueOf(listTrack.get(position).longitude)));
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		T.showShort(this, marker.getTitle() + "");
		return false;
	}
}
