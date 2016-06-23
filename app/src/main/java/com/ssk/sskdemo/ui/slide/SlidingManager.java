package com.ssk.sskdemo.ui.slide;

import java.util.HashSet;

//管理我们的打开的Item视图
public class SlidingManager {
	private HashSet<SlideItemLayout> itemLayouts = new HashSet<SlideItemLayout>();

	private OnSlidingItemListener onSlidingItemListener = new OnSlidingItemListener() {

		@Override
		public void onStartOpen(SlideItemLayout itemLayout) {
			closeAllLayout();
			itemLayouts.add(itemLayout);
		}

		@Override
		public void onStartClose(SlideItemLayout itemLayout) {

		}

		@Override
		public void onOpen(SlideItemLayout itemLayout) {
			itemLayouts.add(itemLayout);
		}

		@Override
		public void onClose(SlideItemLayout itemLayout) {
			itemLayouts.remove(itemLayout);
		}
	};

	public OnSlidingItemListener getOnSlidingItemListener() {
		return onSlidingItemListener;
	}

	// 获得已打开item数量
	public int getOpenCount() {
		return this.itemLayouts.size();
	}

	// 关闭所有已打开的Item
	public void closeAllLayout() {
		if (this.itemLayouts.size() == 0) {
			return;
		}
		for (SlideItemLayout slideItemLayout : itemLayouts) {
			slideItemLayout.close(true, false);
		}
		this.itemLayouts.clear();
	}

}
