package com.ssk.sskdemo.ui.slide;

public interface OnSlidingItemListener {
	 void onOpen(SlideItemLayout itemLayout);

	 void onClose(SlideItemLayout itemLayout);

	 void onStartOpen(SlideItemLayout itemLayout);

	 void onStartClose(SlideItemLayout itemLayout);
}
