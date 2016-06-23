package com.ssk.sskdemo.ui.slide;


public interface ISlideLayout {
	// 滑动
	 SlideItemLayout.SlidingType getCurrentSate();

	// 开
	 void close();

	// 关
	 void open();
}
