package com.pactera.pacteramap.business;

import com.pactera.pacteramap.PMApplication;


/**
 * 公共Command
 * @author ChunfaLee
 *
 */
public abstract class PMCommand {

	public PMApplication app;
	
	public PMCommand() {
		app=PMApplication.getInstance();
	}

	public abstract void execute(PMInterface iface);
	
	public abstract void execute(PMInterface iface,Object value);
}
