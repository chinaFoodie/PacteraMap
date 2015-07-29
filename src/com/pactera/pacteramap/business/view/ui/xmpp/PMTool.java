package com.pactera.pacteramap.business.view.ui.xmpp;

import java.io.IOException;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import com.pactera.pacteramap.config.AppConfig;

/**
 * http://172.19.64.52:9090/login.jsp?url=%2Findex.jsp
 * @author WMF
 *
 */
public class PMTool {

	private XMPPTCPConnection connect = null;

	private void openConnection() {
		ConnectionConfiguration conf = new ConnectionConfiguration(
				AppConfig.IP, AppConfig.XMPP_PORT);
		conf.setSecurityMode(SecurityMode.disabled); //关闭安全连接，否则认证失败。
	    conf.setCompressionEnabled(false);
	    conf.setReconnectionAllowed(false);
	    conf.setSendPresence(false);
	    conf.setDebuggerEnabled(true);
		connect = new XMPPTCPConnection(conf);
		try {
			connect.connect();
		} catch (SmackException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		conf = null;
	}

	public XMPPConnection getConnection() {
		if (connect == null) {
			openConnection();
		}else{
			if(!connect.isConnected()){
				try {
					connect.connect();
				} catch (SmackException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}
		}
		return connect;
	}

	public boolean isConnected() {
		return connect.isConnected();
	}

	public void closeConnection() {
		try {
			connect.disconnect();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		connect = null;
	}
}
