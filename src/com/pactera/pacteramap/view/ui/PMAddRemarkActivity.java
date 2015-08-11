package com.pactera.pacteramap.view.ui;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.R;
import com.pactera.pacteramap.mapinterface.PMLocationCommand;
import com.pactera.pacteramap.mapinterface.PMLocationInterface;
import com.pactera.pacteramap.util.L;
import com.pactera.pacteramap.util.PMSpannableUtils;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.util.recorder.PMAudioRecordFunc;
import com.pactera.pacteramap.util.recorder.PMErrorCode;
import com.pactera.pacteramap.util.recorder.PMMediaRecordFunc;
import com.pactera.pacteramap.view.PMActivity;

/**
 * 新增备忘录界面
 * 
 * @author ChunfaLee
 * @create 2015年6月23日15:30:30
 */
@SuppressLint({ "InflateParams", "HandlerLeak" })
public class PMAddRemarkActivity extends PMActivity implements OnClickListener,
		PMLocationInterface {
	private int mState = -1; // -1:没再录制，0：录制wav，1：录制amr
	private final static int CMD_RECORDING_TIME = 2000;
	private final static int CMD_RECORDFAIL = 2001;
	private final static int CMD_STOP = 2002;
	private final static int FLAG_WAV = 0;
	private final static int FLAG_AMR = 1;
	private TextView tvTitle, tvRight, tvAudioing;
	private LinearLayout llBack, llRight;
	private RelativeLayout rlAuudio;
	private EditText etContent;
	private Editable etableText;
	private View viewAudio;
	private Button btnFinish;
	private UIHandler uiHandler = new UIHandler();
	private UIThread uiThread;
	@SuppressWarnings("unused")
	private ImageLoader imageLoader;
	private Drawable drawable = null;
	private static final int TAKE_PHOTO = 0x000000;
	public static final int SELECT_PIC_BY_PICK_PICTURE = 2;
	public static final String KEY_PHOTO_PATH = "photo_path";
	private Uri photoUri;
	private String picPath;
	private ScrollView scrollView;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 图片append到edittext中
			case 70086:
				etableText.append(PMSpannableUtils.setTextImg("1231 ", 0, 4,
						getImgDrawable(picPath, 300, 300)));
				setSpanClickable();
				break;
			default:
				break;
			}
		}
	};

	class UIHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle b = msg.getData();
			int vCmd = b.getInt("cmd");
			switch (vCmd) {
			case CMD_RECORDING_TIME:
				int vTime = b.getInt("msg");
				tvAudioing.setText("正在录音" + vTime + "s");
				break;
			case CMD_RECORDFAIL:
				int vErrorCode = b.getInt("msg");
				String vMsg = PMErrorCode.getErrorInfo(
						PMAddRemarkActivity.this, vErrorCode);
				T.showShort(PMAddRemarkActivity.this, vMsg);
				break;
			case CMD_STOP:
				PMMediaRecordFunc mRecord_1 = PMMediaRecordFunc.getInstance();
				long mSize = mRecord_1.getRecordFileSize();
				Spanned content = Html
						.fromHtml("火锅好吃<img src=E:\\Worksoft\\apache-tomcat-6.0.43\\apache-tomcat-6.0.43\\webapps\\tsp\\smartbill\\template\\iPad\\images\\DG.png width=\"100%\"/>黄家驹回家哈哈<img src=http://220.168.91.206:8089/my/740/other/1401417387423.jpg width=\"100%\"/>看看你还好吧");
				ImageSpan[] is = content.getSpans(0, content.length(),
						ImageSpan.class);
				for (int i = 0; i < is.length; i++) {
					// 得到图片地址，可以先下载然后通过handler更新界面
					L.e("ImageSpan内容" + is[i].getSource());
				}
				etableText.append(PMSpannableUtils.setTextImg("1231\n", 0, 4,
						createViewDrawable("录制的文件名201506301637", mSize + "")));
				setSpanClickable();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_remark_activity);
		viewAudio = LayoutInflater.from(this).inflate(R.layout.radio_layout,
				null);
		init();
		imageLoader = PMApplication.getInstance().imageLoader;
	}

	/** 初始化控件 */
	private void init() {
		tvTitle = (TextView) findViewById(R.id.tv_mid_title);
		tvTitle.setText("添加备忘录");
		llBack = (LinearLayout) findViewById(R.id.ll_tv_base_left);
		llBack.setVisibility(View.VISIBLE);
		llBack.setOnClickListener(this);
		llRight = (LinearLayout) findViewById(R.id.ll_tv_base_right);
		tvRight = (TextView) findViewById(R.id.tv_base_right);
		tvRight.setText("保存");
		llRight.setVisibility(View.VISIBLE);
		llRight.setOnClickListener(this);
		findViewById(R.id.img_add_remark_image).setOnClickListener(this);
		findViewById(R.id.img_add_remark_location).setOnClickListener(this);
		findViewById(R.id.img_add_remark_photo).setOnClickListener(this);
		findViewById(R.id.img_add_remark_psw).setOnClickListener(this);
		findViewById(R.id.img_add_remark_radio).setOnClickListener(this);
		scrollView = (ScrollView) findViewById(R.id.add_remark_scrollview);
		tvAudioing = (TextView) findViewById(R.id.tv_audio_ing);
		btnFinish = (Button) findViewById(R.id.btn_audio_finish);
		btnFinish.setOnClickListener(this);
		rlAuudio = (RelativeLayout) findViewById(R.id.rl_radio);
		etContent = (EditText) findViewById(R.id.et_add_reamark_content);
		etableText = etContent.getEditableText();
	}

	private void setSpanClickable() {

		// 此方法比较靠谱
		Spanned s = etContent.getText();
		// setMovementMethod很重要，不然ClickableSpan无法获取点击事件。
		etContent.setMovementMethod(LinkMovementMethod.getInstance());
		ImageSpan[] imageSpans = s.getSpans(0, s.length(), ImageSpan.class);

		for (ImageSpan span : imageSpans) {
			final String image_src = span.getSource();
			final int start = s.getSpanStart(span);
			final int end = s.getSpanEnd(span);
			ClickableSpan click_span = new ClickableSpan() {
				@Override
				public void onClick(View widget) {
					etContent.setCursorVisible(true);
					T.showShort(PMAddRemarkActivity.this, "Image Clicked "
							+ image_src);
				}
			};
			ClickableSpan[] click_spans = s.getSpans(start, end,
					ClickableSpan.class);
			if (click_spans.length != 0) {
				// remove all click spans
				for (ClickableSpan c_span : click_spans) {
					((Spannable) s).removeSpan(c_span);
				}
			}
			((Spannable) s).setSpan(click_span, start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_tv_base_left:
			PMAddRemarkActivity.this.finish();
			break;
		/** 保存参数 */
		case R.id.ll_tv_base_right:
			break;
		case R.id.img_add_remark_image:
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, SELECT_PIC_BY_PICK_PICTURE);
			break;
		case R.id.img_add_remark_location:
			new PMLocationCommand(this).execute(this);
			T.showShort(PMAddRemarkActivity.this, "开始定位");
			break;
		case R.id.img_add_remark_photo:
			Intent openCameraIntent = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(openCameraIntent, TAKE_PHOTO);
			break;
		case R.id.img_add_remark_psw:
			T.showShort(PMAddRemarkActivity.this, "是否加密");
			break;
		case R.id.img_add_remark_radio:
			scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			if (btnFinish.getText().equals("完成")) {
				T.showShort(this, "正在录音..请小心操作");
			} else {
				rlAuudio.setVisibility(View.VISIBLE);
				btnFinish.setText("开始");
				tvAudioing.setText("点击开始录音");
			}
			break;
		case R.id.btn_audio_finish:
			if ("完成".equals(btnFinish.getText().toString())) {
				btnFinish.setText("开始");
				rlAuudio.setVisibility(View.GONE);
				stop();
			} else {
				btnFinish.setText("完成");
				record(FLAG_AMR);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 开始录音
	 * 
	 * @param mFlag
	 *            ，0：录制wav格式，1：录音amr格式
	 */
	private void record(int mFlag) {
		if (mState != -1) {
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", PMErrorCode.E_STATE_RECODING);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			return;
		}
		int mResult = -1;
		switch (mFlag) {
		case FLAG_WAV:
			PMAudioRecordFunc mRecord_1 = PMAudioRecordFunc.getInstance();
			mResult = mRecord_1.startRecordAndFile();
			break;
		case FLAG_AMR:
			PMMediaRecordFunc mRecord_2 = PMMediaRecordFunc.getInstance();
			mResult = mRecord_2.startRecordAndFile();
			break;
		}
		if (mResult == PMErrorCode.SUCCESS) {
			uiThread = new UIThread();
			new Thread(uiThread).start();
			mState = mFlag;
		} else {
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_RECORDFAIL);
			b.putInt("msg", mResult);
			msg.setData(b);
			uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}

	/**
	 * 停止录音
	 */
	private void stop() {
		if (mState != -1) {
			switch (mState) {
			case FLAG_WAV:
				PMAudioRecordFunc mRecord_1 = PMAudioRecordFunc.getInstance();
				mRecord_1.stopRecordAndFile();
				break;
			case FLAG_AMR:
				PMMediaRecordFunc mRecord_2 = PMMediaRecordFunc.getInstance();
				mRecord_2.stopRecordAndFile();
				break;
			}
			if (uiThread != null) {
				uiThread.stopThread();
			}
			if (uiHandler != null)
				uiHandler.removeCallbacks(uiThread);
			Message msg = new Message();
			Bundle b = new Bundle();// 存放数据
			b.putInt("cmd", CMD_STOP);
			b.putInt("msg", mState);
			msg.setData(b);
			uiHandler.sendMessageDelayed(msg, 1000); // 向Handler发送消息,更新UI
			mState = -1;
		}
	}

	class UIThread implements Runnable {
		int mTimeMill = 0;
		boolean vRun = true;

		public void stopThread() {
			vRun = false;
		}

		public void run() {
			while (vRun) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mTimeMill++;
				Message msg = new Message();
				Bundle b = new Bundle();// 存放数据
				b.putInt("cmd", CMD_RECORDING_TIME);
				b.putInt("msg", mTimeMill);
				msg.setData(b);
				PMAddRemarkActivity.this.uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
			}
		}
	}

	@Override
	public void CallBack(int tag, Object value) {
		super.CallBack(tag, value);
	}

	/** 获取图片文件的drawable */
	@SuppressWarnings("deprecation")
	private Drawable getImgDrawable(String filePath, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeFile(filePath, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(filePath, opts));
		Bitmap tempBitMap = Bitmap.createScaledBitmap(weak.get(), width / 3,
				height / 3, true);
		return new BitmapDrawable(tempBitMap);
	}

	/** 获取录音文件的drawable */
	@SuppressWarnings("deprecation")
	public Drawable createViewDrawable(String name, String size) {
		TextView tvName = (TextView) viewAudio.findViewById(R.id.tv_audio_name);
		TextView tvSize = (TextView) viewAudio.findViewById(R.id.tv_audio_size);
		tvName.setText(name);
		tvSize.setText(size + "KB");
		viewAudio.setDrawingCacheEnabled(true);
		viewAudio.measure(
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		viewAudio.layout(0, 0, viewAudio.getMeasuredWidth(),
				viewAudio.getMeasuredHeight());
		viewAudio.buildDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(viewAudio.getWidth(),
				viewAudio.getHeight(), Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		viewAudio.draw(canvas);
		drawable = new BitmapDrawable(bitmap);
		return drawable;
	}

	@SuppressWarnings("unused")
	private Object addRemarkParams(BDLocation location) {
		RequestParams params = new RequestParams();
		params.add("id", PMApplication.getInstance().getImei());
		params.add("province", location.getProvince());
		params.add("city", location.getCity());
		params.add("district", location.getDistrict());
		params.add("Street", location.getStreet());
		params.add("Street_number", location.getStreetNumber());
		params.add("has_addr", location.hasAddr() + "");
		params.add("addr_str", location.getAddrStr());
		params.add("time", location.getTime());
		params.add("satellite_number", location.getSatelliteNumber() + "");
		params.add("radius", location.getRadius() + "");
		params.add("has_speed", location.hasSpeed() + "");
		params.add("speed", location.getSpeed() + "");
		params.add("direction", location.getDirection() + "");
		params.add("floor", location.getFloor());
		params.add("network_location_type", location.getNetworkLocationType());
		params.add("operators", location.getOperators() + "");
		params.add("longitude", location.getLongitude() + "");
		params.add("latitude", location.getLatitude() + "");
		return params;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_PIC_BY_PICK_PICTURE) {
				doPicture(requestCode, data);
			} else if (requestCode == TAKE_PHOTO) {
				doPhoto(resultCode, data);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 得到返回相机的图片资源
	private void doPhoto(int resultCode, Intent data) {
	}

	/**
	 * 选择图片后，获取图片的路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	@SuppressWarnings("deprecation")
	private void doPicture(int requestCode, Intent data) {
		if (data == null) {
			T.showShort(this, "选择图片文件出错");
			return;
		}
		photoUri = data.getData();
		if (photoUri == null) {
			T.showShort(this, "选择图片文件出错");
			return;
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			// cursor.close();
		}
		handler.sendEmptyMessage(70086);
	}

	@Override
	public void locationCallBack(Object value) {
		@SuppressWarnings("unused")
		BDLocation bdLocation = (BDLocation) value;
	}
}
