package com.ssk.sskdemo.ui.slide;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

//拦截事件
public class SlideContentLayout extends RelativeLayout {

	private ISlideLayout slideLayout;

	public SlideContentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlideContentLayout(Context context) {
		super(context);
	}

	public void setSlideLayout(ISlideLayout slideLayout) {
		this.slideLayout = slideLayout;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(this.slideLayout.getCurrentSate() == SlideItemLayout.SlidingType.Close){
			return super.onInterceptTouchEvent(ev);
		}else{
			return true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(this.slideLayout.getCurrentSate() == SlideItemLayout.SlidingType.Close){
			return super.onTouchEvent(event);
		}else{
			if(event.getActionMasked() == MotionEvent.ACTION_UP){
				//弹起，关闭滑动视图
				this.slideLayout.close();
			}
			return true;
		}
	}
	
	
}
