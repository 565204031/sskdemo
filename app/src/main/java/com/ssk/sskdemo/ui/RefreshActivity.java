package com.ssk.sskdemo.ui;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;
import com.ssk.sskdemo.ui.pull.PullToRefreshLayout;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


/**
 * 重绘控件实现下拉刷新
 * @author 杀死凯 QQ565204031
 *
 */
public class RefreshActivity extends BaseToolBarActivity implements PullToRefreshLayout.OnRefreshListener {

	PullToRefreshLayout refreshLayout;

	@Override
	protected int getContentView() {
		return R.layout.ui_activity_refresh;
	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		refreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh);
		refreshLayout.setOnRefreshListener(this);
	}

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		//请求网络，请求完毕后调用
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				Log.i("INFO","onRefresh");
			}
		}.sendEmptyMessageDelayed(0, 3000);
	}
	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		//请求网络，请求完毕后调用
		new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				Log.i("INFO","onRefresh");
			}
		}.sendEmptyMessageDelayed(0, 3000);
	}
}
