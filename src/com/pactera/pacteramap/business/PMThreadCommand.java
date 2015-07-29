package com.pactera.pacteramap.business;

import android.os.Handler;
import android.util.Log;

/** 
 * 多线程基类
 * @author  WMF
 */
public class PMThreadCommand extends PMCommand {

	protected Handler handler;
	protected boolean success;
	protected Thread thread;
	protected int priority;
	protected PMInterface iface;
	protected Object value;
	
	public PMThreadCommand() {
		//线程的优先级用1-10之间的整数表示，数值越大优先级越高，默认的优先级为5。
		priority=5;
		success=false;
	}

	@Override
	public void execute(PMInterface iface) {
		this.iface=iface;
		intThread();
	}

	@Override
	public void execute(PMInterface iface, Object value) {
		this.iface=iface;
		this.value=value;
		intThread();
	}
	
	/**
	 * 初始化线程
	 */
	public void intThread() {
		Log.e("currentThread1=",Thread.currentThread().getName());
		handler=new Handler();
		thread=new Thread(new childThread());
		thread.setPriority(priority);
		thread.start();
	}
	
	/** 
	 * 子线程
	 * @author  WMF 
	 */
	class childThread implements Runnable{

		@Override
		public void run() {
			Log.e("currentThread2=",Thread.currentThread().getName());
			childRunnable();
			handler.post(mainThread);
		}
	}
	
	/**
	 * 主线程
	 */
	private Runnable mainThread=new Runnable() {
		
		@Override
		public void run() {
			Log.e("currentThread3=",Thread.currentThread().getName());
			handler=null;
			thread=null;
			mainRunnable();
		}
	};
	
	/**
	 * 子线程运行(多线程执行)
	 * -子类需继承
	 */
	protected void childRunnable(){
		
	}
	
	/**
	 * 主线程运行(多线程执行执行完，转到主线程执行)
	 * -子类需继承
	 */
	protected void mainRunnable(){
		
	}
}
