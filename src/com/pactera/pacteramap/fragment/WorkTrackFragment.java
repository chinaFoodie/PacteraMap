package com.pactera.pacteramap.fragment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDrawFrameCallback;
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
import com.baidu.mapapi.model.LatLng;
import com.loopj.android.http.RequestParams;
import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.adapter.PMTrackPointAdapter;
import com.pactera.pacteramap.base.BaseFragment;
import com.pactera.pacteramap.business.view.ui.worktrack.PMWorkTrackCommand;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMGsonUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.component.PMCalendar;
import com.pactera.pacteramap.view.component.PMCalendar.OnCalendarClickListener;
import com.pactera.pacteramap.view.component.MySlidingMenu;
import com.pactera.pacteramap.view.ui.PMMainActivity;
import com.pactera.pacteramap.view.ui.PMRemarkActivity;
import com.pactera.pacteramap.vo.PMWorkTrack;
import com.pactera.pacteramap.vo.PMWorkTrack.AddressInfo;

public class WorkTrackFragment extends BaseFragment implements OnClickListener,
		OnMapClickListener, OnCalendarClickListener, OnItemClickListener {
	private ImageView img2Remark;
	private MapView mapView;
	private PMCalendar pMCalendar;
	String date = null;// 设置默认选中的日期 格式为 “2015-06-18” 标准DATE格式
	private BaiduMap mBaiduMap;
	private Boolean firstClick = true;
	private BitmapDescriptor mBitmap;
	@SuppressWarnings("unused")
	private Marker mMarker;
	private List<LatLng> latLngPolygon = new ArrayList<LatLng>();
	private float[] vertexs;
	private FloatBuffer vertexBuffer;
	private ListView lvTrackPoint;
	private List<AddressInfo> listTrack = new ArrayList<AddressInfo>();
	private PMTrackPointAdapter tAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (super.onCreateView(inflater, container, savedInstanceState) != null) {
			return rootView;
		}
		rootView = inflater.inflate(R.layout.work_track_fragment, null);
		init(rootView);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	private void init(View view) {
		pMCalendar = (PMCalendar) view.findViewById(R.id.work_track_kcalendar);
		lvTrackPoint = (ListView) view.findViewById(R.id.lv_location_point);
		lvTrackPoint.setOnItemClickListener(this);
		pMCalendar.setOnCalendarClickListener(this);
		/** 签到日期标记列表 start ***/
		List<String> list = new ArrayList<String>(); // 设置标记列表
		list.add("2015-06-01");
		list.add("2015-06-08");
		/** 签到日期标记列表 end ***/
		pMCalendar.addMarks(list, 0);
		img2Remark = (ImageView) view.findViewById(R.id.img_go_to_remark);
		img2Remark.setOnClickListener(this);
		mapView = (MapView) view.findViewById(R.id.work_track_map);
		// 去掉百度自带的ZoomControls控件
		int count = mapView.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = mapView.getChildAt(i);
			if (child instanceof ZoomControls || child instanceof ImageView) {
				child.setVisibility(View.INVISIBLE);
			}
		}
		mapView.getMap().setOnMapClickListener(this);
		activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
		MapStatus mMapStatus = new MapStatus.Builder()
				.target(new LatLng(30.554175, 104.076187)).zoom(18).build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		mBaiduMap = mapView.getMap();
		mBaiduMap.setMapStatus(mMapStatusUpdate);
		getTrackPoints();
	}
	
	private void getTrackPoints() {
		new PMWorkTrackCommand().execute(this, getTrackParams("2015-06-18"));
	}

	private Object getTrackParams(String string) {
		RequestParams params = new RequestParams();
		params.add("IMEINum", PMApplication.getInstance().getImei());
		params.add("trackTime", date);
		return params;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		T.showShort(activity,
				position + "   " + "纬度：" + listTrack.get(position).latitude
						+ "\n" + "经度：" + listTrack.get(position).longitude);
		drawMapLine(new LatLng(
				Double.valueOf(listTrack.get(position).latitude),
				Double.valueOf(listTrack.get(position).longitude)));
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
			pMCalendar.setCalendarDayBgColor(dateFormat,
					R.drawable.calendar_date_focused);
			date = dateFormat;// 最后返回给全局 date
			T.showShort(activity, "时间参数" + dateFormat + "开始请求轨迹点");
		}
	}

	@Override
	public void onMapClick(LatLng latLng) {
		drawMapLine(latLng);
	}

	private void drawMapLine(LatLng latLng) {
		if (firstClick) {
			mBitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher);
			OverlayOptions oofirst = new MarkerOptions().position(latLng)
					.icon(mBitmap).zIndex(9).draggable(false);
			mMarker = (Marker) (mBaiduMap.addOverlay(oofirst));
			LatLng llA = latLng;// 记录第一个点坐标
			latLngPolygon.add(llA);
			firstClick = false;
			mBaiduMap.setOnMapDrawFrameCallback(new MyDraw());
		} else {
			mBitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_launcher);
			OverlayOptions oo = new MarkerOptions().position(latLng)
					.icon(mBitmap).zIndex(9).draggable(false);
			mMarker = (Marker) (mBaiduMap.addOverlay(oo));
			LatLng llB = latLng;
			latLngPolygon.add(llB);
		}
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		return false;
	}

	@Override
	public void CallBack(Object value) {
		super.CallBack(value);
		PMWorkTrack wTrack = PMGsonUtil.getPerson(value.toString(),
				PMWorkTrack.class);
		if (wTrack != null) {
			listTrack = wTrack.data.get(0).addressInfo;
			tAdapter = new PMTrackPointAdapter(activity, listTrack);
			lvTrackPoint.setAdapter(tAdapter);
		} else {
			T.showShort(activity, "返回结果有误");
		}
	}

	public class MyDraw implements OnMapDrawFrameCallback {
		public void onMapDrawFrame(GL10 gl, MapStatus drawingMapStatus) {
			if (mBaiduMap.getProjection() != null) {
				calPolylinePoint(drawingMapStatus);
				drawPolyline(gl, Color.argb(255, 255, 0, 0), vertexBuffer, 10,
						latLngPolygon.size(), drawingMapStatus);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_go_to_remark:
			PMActivityUtil.next(activity, PMRemarkActivity.class);
			break;
		default:
			break;
		}
	}
}
