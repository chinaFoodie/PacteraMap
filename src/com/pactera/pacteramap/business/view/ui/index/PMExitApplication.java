package com.pactera.pacteramap.business.view.ui.index;

import android.os.Handler;

import com.pactera.pacteramap.PMApplication;
import com.pactera.pacteramap.business.PMCommand;
import com.pactera.pacteramap.business.PMInterface;
import com.pactera.pacteramap.model.PMDataProxy;
import com.pactera.pacteramap.util.PMDateUtil;
import com.pactera.pacteramap.util.T;
import com.pactera.pacteramap.view.ui.PMWelcomeActivity;

/**
 *
 * @author Mingfan.Wang 2015-7-14
 */
public class PMExitApplication extends PMCommand {

	public PMExitApplication() {

	}

	@Override
	public void execute(PMInterface iface) {
		PMWelcomeActivity index = (PMWelcomeActivity) iface;
		PMDataProxy data = PMApplication.getInstance().data;
		if (data.exitCount == 1) {
			long current = PMDateUtil.getMillisecond();
			if (current - data.exitMillisecond < 800) {
				index.exit(0);
			}
		} else {
			data.exitCount++;
			data.exitMillisecond = PMDateUtil.getMillisecond();
			T.showShort(index, "再按一次退出应用");
			// 异步延迟0.8秒操作
			Handler handler = new Handler();
			handler.postDelayed(R1, 2000);
			handler = null;
		}
	}

	@Override
	public void execute(PMInterface iface, Object value) {

	}

	private Runnable R1 = new Runnable() {

		@Override
		public void run() {
			PMDataProxy data = PMApplication.getInstance().data;
			data.exitCount = 0;
			data.exitMillisecond = 0;
		}
	};
}
