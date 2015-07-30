package com.pactera.pacteramap.business.view.ui.xmpp;

import java.io.IOException;

import org.apache.harmony.javax.security.sasl.SaslException;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

/**
 * XMPP监听
 * 
 * @author ChunfaLee
 * 
 */
public class PMListener{

	private PMTool tool;
	private Handler handler;
	
	public PMListener(Handler handler) {
		this.handler=handler;
		tool=new PMTool();
	}
	
	/**
	 * 启动
	 * @param context
	 */
	public void start(Context context) {
		//xmpp初始化
		SmackAndroid.init(context);
		//SASL的认证方式为PLAIN模式
		SASLAuthentication.supportSASLMechanism("PLAIN");
		//必须使用子线程连接
		final Thread thread=new Thread(new Runnable() {
			@Override
			public void run() {
				String message;
				// 连接服务器
				XMPPConnection connect = tool.getConnection();
				// 连接成功
				if (connect != null && connect.isConnected()) {
					// 登录服务器
					try {
						connect.login("wangmingfan", "wangmingfan");
					} catch (SaslException e) {
						e.printStackTrace();
					} catch (XMPPException e) {
						e.printStackTrace();
					} catch (SmackException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(connect.isAuthenticated()){//如果登录成功
						// 发送在线状态到服务端
						Presence presence = new Presence(Presence.Type.available);
						try {
							tool.getConnection().sendPacket(presence);
						} catch (NotConnectedException e) {
							e.printStackTrace();
						}
						presence = null;
						//监听服务端发来的消息
						ChatManager cm =ChatManager.getInstanceFor(tool.getConnection());
						cm.addChatListener(chat);
						message="登录openfire服务端成功";
					}else{
						message="登录openfire服务端失败";
					}
					message="连接openfire服务端成功";
				} else {
					message="连接openfire服务端失败";
				}
				Log.e("processMessage", message);
			}
		});
		thread.start();
	}
	

	/**
	 * 接收到消息
	 */
	private ChatManagerListener chat=new ChatManagerListener() {
		
		@Override
		public void chatCreated(Chat arg0, boolean arg1) {
			
			arg0.addMessageListener(new MessageListener() {
				@Override
				public void processMessage(Chat chat2, Message message)
				{
					String e=message.getFrom()+","+
							message.getTo()+","+
							message.getType()+","+
							message.getBody();
					Log.e("processMessage",e);
					android.os.Message msg=new android.os.Message();
					msg.obj=message;
					handler.sendMessage(msg);
					msg=null;
				}
			});			
		}
	};
	/**
	 * 清理
	 */
	public void clean(){
		if(tool!=null){
			tool.closeConnection();
			tool=null;
		}
	}
}
