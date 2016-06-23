package com.ssk.sskdemo.ui.bubble;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class BubbleLayout extends FrameLayout {

	private int centerX;
	private int centerY;

	public BubbleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BubbleLayout(Context context) {
		super(context);
	}

	public void setCenterPoint(int x, int y) {
		this.centerX = x;
		this.centerY = y;
		requestLayout();
	}

	// 摆放我的子控件(这个步骤做不做都没关系)
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (getChildCount() > 0) {
			View childView = getChildAt(0);
			if (childView.getVisibility() != View.GONE) {
				int width = childView.getMeasuredWidth();
				int height = childView.getMeasuredHeight();

				// 让我们的childView居中
				childView.layout((int) (centerX - width / 2.0f),
						(int) (centerY - height / 2.0f),
						(int) (centerX + width / 2.0f),
						(int) (centerY + height / 2.0f));
			}
		}
	}
}
