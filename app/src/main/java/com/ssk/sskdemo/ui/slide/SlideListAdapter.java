package com.ssk.sskdemo.ui.slide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ssk.sskdemo.R;
import com.ssk.sskdemo.ui.bubble.BubbleManage;


public class SlideListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	//消息气泡管理
	private BubbleManage mBubbleManage;
	private SlidingManager slidingManager;

	public SlideListAdapter(Context mContext) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.slidingManager = new SlidingManager();
		mBubbleManage=new BubbleManage(mContext);
	}

	public SlidingManager getSlidingManager() {
		return slidingManager;
	}

	@Override
	public int getCount() {
		return 50;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.ui_item_sideslip, parent, false);
			mHolder = new ViewHolder();
			mHolder.bt_cancel = (Button) convertView
					.findViewById(R.id.bt_cancel);
			mHolder.bt_delete = (Button) convertView
					.findViewById(R.id.bt_delete);
			mHolder.tv_bubble = (TextView) convertView
					.findViewById(R.id.tv_bubble);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.bt_cancel.setTag(position);
		mHolder.bt_delete.setTag(position);

		//处理消息气泡
		mHolder.tv_bubble.setTag(position);
		mHolder.tv_bubble.setOnTouchListener(mBubbleManage);


		SlideItemLayout itemLayout = (SlideItemLayout) convertView;
		itemLayout.close(false, false);
		itemLayout.getContentView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingManager.closeAllLayout();
			}
		});
		// 给我们的条目添加点击事件
		// itemLayout.getContentView().setOnClickListener(l);
		itemLayout.setOnSlidingItemListener(this.slidingManager
				.getOnSlidingItemListener());


		return convertView;
	}

	class ViewHolder {
		public Button bt_cancel;
		public Button bt_delete;
		public TextView tv_bubble;
	}

}