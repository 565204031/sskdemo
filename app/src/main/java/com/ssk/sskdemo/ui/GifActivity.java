package com.ssk.sskdemo.ui;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;



import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * GIF动画页面
 * @author 杀死凯 QQ565204031
 *
 */
public class GifActivity extends BaseToolBarActivity{

	private FrameLayout fl;

	@Override
	protected int getContentView() {
		return R.layout.ui_activity_gif;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		fl=(FrameLayout) findViewById(R.id.fl);
		GifSurfaceView gif=new GifSurfaceView(this);
		fl.addView(gif);
	}
}
