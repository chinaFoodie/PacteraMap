package com.pactera.pacteramap.view.ui;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pactera.pacteramap.R;
import com.pactera.pacteramap.config.PMShareKey;
import com.pactera.pacteramap.util.ImageLoaderManager;
import com.pactera.pacteramap.util.PMActivityUtil;
import com.pactera.pacteramap.util.PMSharePreferce;
import com.pactera.pacteramap.util.PMUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.PMActivity;
import com.pactera.pacteramap.view.component.ActionSheetDialog;
import com.pactera.pacteramap.view.component.ActionSheetDialog.OnSheetItemClickListener;
import com.pactera.pacteramap.view.component.ActionSheetDialog.SheetItemColor;
import com.pactera.pacteramap.view.component.BaseViewPager;
import com.pactera.pacteramap.view.component.BaseViewPager.PagerBannersListener;
import com.pactera.pacteramap.view.component.CircleImageView;
import com.pactera.pacteramap.view.component.slidingmenu.SlidingMenu;

/**
 * 登录成功或是自动登录之后进来的首页
 * 
 * @author ChunfaLee
 * @create 2015年7月29日09:22:12
 * @version 1.00
 *
 */
@SuppressLint("NewApi")
public class PMWelcomeActivity extends PMActivity implements OnClickListener,
		PagerBannersListener {
	private CircleImageView circleImageView, civLeftMenu;
	private SlidingMenu menu;
	private BaseViewPager mViewPager;
	private ImageLoaderManager imageLoader;
	private ArrayList<String> bannerList;
	private TextView tvUserName;
	private String localTempPhotoDir = "/sdcard/pacterMap";
	private String localTempPhotoName;
	private Uri photoUri;
	// 本地相册返回图片地址
	private String picPath;
	// 启动相机返回码
	public static final int SELECT_PIC_WEL_PHOTO = 7001;
	// 启动本地相册返回码
	public static final int SELECT_PIC_WEL_PIC = 7002;
	private PMSharePreferce share;
	// 更新UI数据操作
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			// 通过handler更新界面并保存图片地址到内存中
			case SELECT_PIC_WEL_PIC:
				int degreePic = PMUtil.readPicDegree(picPath);
				civLeftMenu.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
						.rotateBitmap(degreePic,
								PMUtil.compressImageFromFile(picPath))));
				circleImageView.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
						.rotateBitmap(degreePic,
								PMUtil.compressImageFromFile(picPath))));
				share.setCache(PMShareKey.USERAVATAR, picPath);
				break;
			case SELECT_PIC_WEL_PHOTO:
				String tempFile = msg.obj.toString();
				int degreePhoto = PMUtil.readPicDegree(tempFile);
				civLeftMenu.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
						.rotateBitmap(degreePhoto,
								PMUtil.compressImageFromFile(tempFile))));
				circleImageView.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
						.rotateBitmap(degreePhoto,
								PMUtil.compressImageFromFile(tempFile))));
				share.setCache(PMShareKey.USERAVATAR, tempFile);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_activity);
		imageLoader = ImageLoaderManager.getInstance();
		share = PMSharePreferce.getInstance(this);
		init();
	}

	/** 初始化视图控件 **/
	private void init() {
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模式
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动菜单视图的宽度
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 为侧滑菜单设置布局
		menu.setMenu(R.layout.layout_menu);
		circleImageView = (CircleImageView) findViewById(R.id.person_info_head);
		circleImageView.setOnClickListener(this);
		mViewPager = (BaseViewPager) findViewById(R.id.view_pager_banner);
		mViewPager.setImageResources(getBannerList(), this);
		findViewById(R.id.ll_main_remark).setOnClickListener(this);
		findViewById(R.id.ll_main_loan_business).setOnClickListener(this);
		findViewById(R.id.ll_main_route_plan).setOnClickListener(this);
		findViewById(R.id.ll_main_work_track).setOnClickListener(this);
		findViewById(R.id.ll_main_message_center).setOnClickListener(this);
		findViewById(R.id.ll_main_integral_events).setOnClickListener(this);
		findViewById(R.id.ll_main_financial_products).setOnClickListener(this);
		civLeftMenu = (CircleImageView) menu
				.findViewById(R.id.left_menu_person_info_head);
		// 头像图片存储取值
		if (!"".equals(share.getString(PMShareKey.USERAVATAR))) {
			int degreePic = PMUtil.readPicDegree(share
					.getString(PMShareKey.USERAVATAR));
			civLeftMenu.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
					.rotateBitmap(degreePic, PMUtil.compressImageFromFile(share
							.getString(PMShareKey.USERAVATAR)))));
			circleImageView.setImageBitmap(PMUtil.compressBmpFromBmp(PMUtil
					.rotateBitmap(degreePic, PMUtil.compressImageFromFile(share
							.getString(PMShareKey.USERAVATAR)))));
		}
		civLeftMenu.setOnClickListener(this);
		tvUserName = (TextView) menu.findViewById(R.id.tv_left_menu_username);
		tvUserName.setText("Archer");
		menu.findViewById(R.id.rl_wel_sign_in).setOnClickListener(this);
		menu.findViewById(R.id.rl_wel_settings).setOnClickListener(this);
	}

	/** 顶部滚动图片资源获取 */
	private ArrayList<String> getBannerList() {
		bannerList = new ArrayList<String>();
		bannerList
				.add("http://img5.imgtn.bdimg.com/it/u=409711896,1521264113&fm=21&gp=0.jpg");
		bannerList.add("http://img2.3lian.com/2014/f4/201/d/85.jpg");
		bannerList.add("http://img2.3lian.com/2014/f5/101/d/6.jpg");
		bannerList.add("http://img3.3lian.com/2014/c2/61/d/10.jpg");
		return bannerList;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mViewPager.startImageCycle();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_info_head:
			menu.toggle();
			break;
		// 贷款业务
		case R.id.ll_main_loan_business:
			T.showShort(this, "贷款业务还在开发中，敬请期待");
			break;
		// 备忘录
		case R.id.ll_main_remark:
			PMActivityUtil.next(this, PMRemarkActivity.class);
			break;
		// 路径规划
		case R.id.ll_main_route_plan:
			PMActivityUtil.next(this, PMRoutePlanActivity.class);
			break;
		// 工作轨迹
		case R.id.ll_main_work_track:
			PMActivityUtil.next(this, PMWorkTrackActivity.class);
			break;
		// 消息中心
		case R.id.ll_main_message_center:
			PMActivityUtil.next(this, PMMessageCenterActivity.class);
			break;
		// 理财产品
		case R.id.ll_main_financial_products:
			T.showShort(this, "理财产品还在开发中，敬请期待");
			break;
		// 积分活动
		case R.id.ll_main_integral_events:
			T.showShort(this, "积分活动还在开发中，敬请期待");
			break;
		// 点击更换头像
		case R.id.left_menu_person_info_head:
			new ActionSheetDialog(PMWelcomeActivity.this)
					.builder()
					.setCancelable(false)
					.setCanceledOnTouchOutside(false)
					.addSheetItem("从手机选择", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									picPhoto();
								}
							})
					.addSheetItem("拍照", SheetItemColor.Blue,
							new OnSheetItemClickListener() {
								@Override
								public void onClick(int which) {
									takPhoto();
								}
							}).show();
			break;
		/** 跳转到签到界面 **/
		case R.id.rl_wel_sign_in:
			Intent signIntent = new Intent(PMWelcomeActivity.this,
					PMSignInActivity.class);
			startActivity(signIntent);
			break;
		case R.id.rl_wel_settings:
			startActivity(new Intent(PMWelcomeActivity.this,
					PMSettingsActivity.class));
			break;
		default:
			break;
		}
	}

	/** 启用相册 */
	private void picPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, SELECT_PIC_WEL_PIC);
	}

	/** 启用相机 */
	private void takPhoto() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			try {
				File dir = new File(Environment.getExternalStorageDirectory()
						+ "/" + localTempPhotoDir);
				if (!dir.exists())
					dir.mkdirs();
				localTempPhotoName = System.currentTimeMillis() + ".jpg";
				Intent intent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				File f = new File(dir, localTempPhotoName);// localTempImgDir和localTempImageFileName是自己定义的名字
				Uri u = Uri.fromFile(f);
				intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
				startActivityForResult(intent, SELECT_PIC_WEL_PHOTO);
			} catch (ActivityNotFoundException e) {
				T.showShort(PMWelcomeActivity.this, "没有找到储存目录");
			}
		} else {
			T.showShort(PMWelcomeActivity.this, "没有储存卡");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_PIC_WEL_PIC) {
				doPicture(requestCode, data);
			} else if (requestCode == SELECT_PIC_WEL_PHOTO) {
				doPhoto(resultCode, data);
			}
		}
	}

	/** 调用相机返回 */
	private void doPhoto(int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			File f = new File(Environment.getExternalStorageDirectory() + "/"
					+ localTempPhotoDir + "/" + localTempPhotoName);
			Message msg = new Message();
			msg.what = SELECT_PIC_WEL_PHOTO;
			msg.obj = f.getAbsoluteFile();
			handler.sendMessage(msg);
		}
	}

	/** 调用相机返回 */
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
		handler.sendEmptyMessage(SELECT_PIC_WEL_PIC);
	}

	/** 图片显示 */
	@Override
	public void ShowImage(String imageURL, ImageView imageView) {
		imageLoader.setViewImage(imageView, imageURL);
	}

	/** 滚动图片点击事件 */
	@Override
	public void onImageClick(int position, View imageView) {

	}
}
