package com.ssk.sskdemo.ui;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;


import android.app.Activity;
import android.os.Bundle;

/**
 * 微信雷达加好友
 * @author 杀死凯 QQ565204031
 *
 */
public class SatelliteActivity extends BaseToolBarActivity{

	private SatelliteView satellite;

	@Override
	protected int getContentView() {
		return R.layout.ui_activity_satellite;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		satellite= (SatelliteView) findViewById(R.id.satellite);
	}
}
