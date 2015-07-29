package com.pactera.pacteramap.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pactera.pacteramap.R;

/**
 * 图片请求管理
 * 
 * @author ChunfaLee
 * 
 */
public class ImageLoaderManager {
	public static ImageLoaderManager imageLoaderManager;
	private static Context mContext;

	@SuppressWarnings("static-access")
	public ImageLoaderManager(Context context) {
		this.mContext = context;
	}

	public static ImageLoaderManager getInstance() {
		if (imageLoaderManager == null)
			imageLoaderManager = new ImageLoaderManager(mContext);
		return imageLoaderManager;
	}

	/**
	 * @description: 异步加载图片
	 * @param context
	 * @param v
	 * @param url
	 */
	@SuppressWarnings("deprecation")
	public void setViewImage(ImageView v, String url) {
		// 如果只是单纯的把图片显示，而不进行缓存。直接用下面的方法拿到URL的Bitmap就行显示就OK
		// Bitmap bitmap = WebImageBuilder.returnBitMap(url);
		// ((ImageView) v).setImageBitmap(bitmap);

		// ImageLoaderConfiguration config = new
		// ImageLoaderConfiguration.Builder(
		// mContext).threadPriority(Thread.NORM_PRIORITY - 2)
		// .denyCacheImageMultipleSizesInMemory()
		// .discCacheFileNameGenerator(new Md5FileNameGenerator())
		// .tasksProcessingOrder(QueueProcessingType.LIFO)
		// .writeDebugLogs() // Remove for release app
		// .build();
		// // Initialize ImageLoader with configuration.
		// ImageLoader.getInstance().init(config);
		ImageLoader imageLoader = ImageLoader.getInstance();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.loading)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.loading)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.loading)// /设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();

		ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
		imageLoader.displayImage(url, v, options, animateFirstListener);

	}

	public void clearCache() {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.clearMemoryCache();
		imageLoader.clearDiscCache();
	}

	public String setUrl(String url) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				mContext).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		ImageLoader imageLoader = ImageLoader.getInstance();
		String imgUrl = imageLoader.getDiscCache().get(url).getPath();
		return imgUrl;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
