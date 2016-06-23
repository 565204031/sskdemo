package com.ssk.sskdemo.ui.slide;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;
import android.widget.FrameLayout;

//1、自定义布局继承FrameLayout
public class SlideItemLayout extends FrameLayout implements ISlideLayout {

	// 滑动视图的摆放（右、左）
	public static enum SlideDirection {
		Left, Right;
	}

	// 滑动视图的状态（Close、Open、Sliding）
	public static enum SlidingType {
		Close, Open, Sliding, SlidingType;
	}

	private SlideDirection slideDirection = SlideDirection.Right;
	private SlidingType slidingType = SlidingType.Close;

	private View appendView;
	private View contentView;

	// 水平滑动偏移量(其实就是appendView的宽度)
	private int horizontalOffset;

	private ViewDragHelper viewDragHelper;

	private OnSlidingItemListener onSlidingItemListener;
	private GestureDetectorCompat detectorCompat;

	public void setOnSlidingItemListener(
			OnSlidingItemListener onSlidingItemListener) {
		this.onSlidingItemListener = onSlidingItemListener;
	}

	public void setSlideDirection(SlideDirection slideDirection) {
		this.slideDirection = slideDirection;
	}

	public void setSlidingType(SlidingType slidingType) {
		this.slidingType = slidingType;
	}

	public View getContentView() {
		return contentView;
	}

	public SlideItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initEvent();
	}

	public SlideItemLayout(Context context) {
		super(context);
		initEvent();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// 当前的视图只允许有两个儿子
		if (getChildCount() != 2) {
			return;
		}

		contentView = getChildAt(0);
		if (contentView instanceof SlideContentLayout) {
			// 判断的目的是为了拦截事件
			SlideContentLayout slideContentLayout = (SlideContentLayout) contentView;
			slideContentLayout.setSlideLayout(this);
		}
		// 第二个视图
		appendView = getChildAt(1);
	}

	//布局的测量和摆放
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获取滑动偏移量
		this.horizontalOffset = this.appendView.getMeasuredWidth();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 处理视图的状态----默认：不打开
		this.layoutContent(false);
	}

	/**
	 *摆放我们的子控件
	 * 
	 * @param isOpen
	 */
	private void layoutContent(boolean isOpen) {
		// 摆放contentView
		Rect contentRect = layoutContentView(isOpen);
		this.contentView.layout(contentRect.left, contentRect.top,
				contentRect.right, contentRect.bottom);
		// 摆放appendView
		Rect appendRect = layoutAppendView(contentRect);
		this.appendView.layout(appendRect.left, appendRect.top,
				appendRect.right, appendRect.bottom);
		// 视图置顶(FrameLayout里面的子控件覆盖了很多层)
		bringChildToFront(contentView);
	}

	// 摆放contentView
	private Rect layoutContentView(boolean isOpen) {
		int left = 0;
		int top = 0;
		if (isOpen) {
			if (this.slideDirection == SlideDirection.Left) {
				left = 0 + this.horizontalOffset;
			} else if (this.slideDirection == SlideDirection.Right) {
				left = 0 - this.horizontalOffset;
			}
		}
		int right = left + this.getMeasuredWidth();
		int bottom = top + getMeasuredHeight();
		return new Rect(left, top, right, bottom);
	}

	//摆放appendView
	private Rect layoutAppendView(Rect contentRect) {
		int left = contentRect.left;
		int top = contentRect.top;
		int right = contentRect.right;
		int bottom = contentRect.bottom;
		if (this.slideDirection == SlideDirection.Left) {
			// 左边
			left = contentRect.left - this.horizontalOffset;
		} else if (this.slideDirection == SlideDirection.Right) {
			// 右边
			left = contentRect.right;
		}
		// right没计算
		right = left + this.horizontalOffset;
		return new Rect(left, top, right, bottom);
	}

	//添加拖拽手势
	private void initEvent() {
		//添加手势监听，处理手势方向
		detectorCompat = new GestureDetectorCompat(getContext(), listener);
		//添加手势拖拽与视图之间的回调接口
		viewDragHelper = ViewDragHelper.create(this, callback);
	}

	@Override
	public SlidingType getCurrentSate() {
		int left = contentView.getLeft();
		if (left == 0) {
			return SlidingType.Close;
		}
		if (left == 0 - horizontalOffset || left == horizontalOffset) {
			return SlidingType.Open;
		}
		return SlidingType.Sliding;
	}

	@Override
	public void close() {
		close(true);
	}

	public void close(boolean isSmooth) {
		close(isSmooth, true);
	}

	//isSmooth ＝ true  isNotify ＝ false
	public void close(boolean isSmooth, boolean isNotify) {
		if (isSmooth) {
			Rect contentRect = layoutContentView(false);
			if (this.viewDragHelper.smoothSlideViewTo(contentView,
					contentRect.left, contentRect.top)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			// 重新摆放布局
			this.layoutContent(false);
			updateSlidingType(isNotify);
		}
	}
	
	@Override
	public void open() {
		open(true, true);
	}

	/**
	 * 
	 * @param isSmooth
	 *            --是否是平移动画
	 * @param isNotify
	 *            --是否刷新UI界面
	 */
	public void open(boolean isSmooth, boolean isNotify) {
		if (isSmooth) {
			Rect contentRect = layoutContentView(true);
			if (this.viewDragHelper.smoothSlideViewTo(contentView,
					contentRect.left, contentRect.top)) {
				ViewCompat.postInvalidateOnAnimation(this);
			}
		} else {
			// 重新摆放布局
			this.layoutContent(true);
			updateSlidingType(isNotify);
		}
	}

	private SimpleOnGestureListener listener = new SimpleOnGestureListener() {

		@Override
		public boolean onScroll(MotionEvent e1,
				MotionEvent e2, float distanceX, float distanceY) {
			// 判断方向（X方向，还是Y方向）
			return Math.abs(distanceX) >= Math.abs(distanceY);
		};

	};

	ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

		//从写tryCaptureView方法（设置拖拽视图）
		@Override
		public boolean tryCaptureView(View child, int arg1) {
			return child == contentView || child == appendView;
		}

		//重写getViewHorizontalDragRange方法（设置滑动偏移量）
		@Override
		public int getViewHorizontalDragRange(View child) {
			return horizontalOffset;
		};

		//重写clampViewPositionHorizontal方法（控制水平滚动范围）
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 处理contentView的滚动范围
			int newLeft = left;
			Log.i("main", "horizontalOffset :" + horizontalOffset);
			if (child == contentView) {
				switch (slideDirection) {
				case Left:
					if (newLeft > horizontalOffset) {
						newLeft = horizontalOffset;
					} else if (newLeft < 0) {
						newLeft = 0;
					}
					break;
				case Right:
					//限制滑动
					if (newLeft < 0 - horizontalOffset) {
						newLeft = 0 - horizontalOffset;
					} else if (newLeft > 0) {
						newLeft = 0;
					}
					break;
				}
			} else if (child == appendView) {
				switch (slideDirection) {
				case Left:
					if (newLeft < 0 - horizontalOffset) {
						newLeft = 0 - horizontalOffset;
					} else if (newLeft > 0) {
						newLeft = 0;
					}
					break;
				case Right:
					//限制滑动
					if (newLeft < getMeasuredWidth() - horizontalOffset) {
						newLeft = getMeasuredWidth() - horizontalOffset;
					} else if (newLeft > getMeasuredWidth()) {
						newLeft = getMeasuredWidth();
					}
					break;
				}
			}
			//处理appendView的滚动范围
			return newLeft;
		};

		//重写onViewPoasitionChanged方法（拖拽视图的位置改变的同时，处理相关的事情）
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			//当我们拖拽contentView的时候，我们的appendView也要跟着动
			if (changedView == contentView) {
				appendView.offsetLeftAndRight(dx);
			}
			//当我们拖拽appendView的时候，我们的contentView也要跟着动
			if (changedView == appendView) {
				contentView.offsetLeftAndRight(dx);
			}
			//实时更新当前view的状态(Open、Close、Sliding)
			updateSlidingType();
			//刷新布局
			invalidate();
		};

		//重写onViewReleased方法（拖拽释放的时候我们需要处理视图位置）
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			//处理contentView的位置
			if (releasedChild == contentView) {
				onContentViewReleased(xvel, yvel);
			}
			//处理appendView的位置
			if (releasedChild == appendView) {
				onAppendViewReleased(xvel, yvel);
			}
			//刷新布局
			invalidate();
		};

	};

	// 实时更新当前view的状态(Open、Close、Sliding)
	private void updateSlidingType() {
		updateSlidingType(true);
	}

	//是否更新状态
	private void updateSlidingType(boolean isNotify) {
		SlidingType lastType = slidingType;
		SlidingType currentType = getCurrentSate();
		if (currentType != slidingType) {
			// 更新状态
			slidingType = currentType;
			if (!isNotify || this.onSlidingItemListener == null) {
				return;
			}
			// 更新视图的位置
			if (slidingType == SlidingType.Open) {
				this.onSlidingItemListener.onOpen(this);
			} else if (slidingType == SlidingType.Close) {
				this.onSlidingItemListener.onClose(this);
			} else if (slidingType == SlidingType.Sliding) {
				if (lastType == SlidingType.Open) {
					this.onSlidingItemListener.onStartClose(this);
				} else if (lastType == SlidingType.Close) {
					this.onSlidingItemListener.onStartOpen(this);
				}
			}
		} else {
			this.slidingType = currentType;
		}
	}

	//当我们的手势拖拽弹起，处理contentView的位置
	private void onContentViewReleased(float x, float y) {
		switch (this.slideDirection) {
		case Left:
			// 滑动到了临界值
			if (x == 0) {
				// 偏移多少我就还原位置，或者去到指定位置
				if (contentView.getLeft() > 0 - horizontalOffset * 0.5f) {
					open();
					return;
				}
			} else if (x > 0) {
				open();
				return;
			}
			break;
		case Right:
			// 滑动到了临界值
			if (x == 0) {
				// 偏移多少我就还原位置，或者去到指定位置
				if (contentView.getLeft() < 0 - horizontalOffset * 0.5f) {
					open();
					return;
				}
			} else if (x < 0) {
				open();
				return;
			}
			break;
		}
		close();
	}

	// 处理appendView的位置
	private void onAppendViewReleased(float x, float y) {
		switch (this.slideDirection) {
		case Left:
			// 滑动到了临界值
			if (x == 0) {
				// 偏移多少我就还原位置，或者去到指定位置
				if (appendView.getLeft() > (0 - horizontalOffset * 0.5f)) {
					open();
					return;
				}
			} else if (x > 0) {
				open();
				return;
			}
			break;
		case Right:
			// 滑动到了临界值
			if (x == 0) {
				// 偏移多少我就还原位置，或者去到指定位置
				if (appendView.getLeft() < getMeasuredWidth()
						- horizontalOffset * 0.5f) {
					open();
					return;
				}
			} else if (x < 0) {
				open();
				return;
			}
			break;
		}
		close();
	}

	@Override
	public void computeScroll() {
		// 判断我们的动画是否继续执行
		if (this.viewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(this);
		}
	}

	// 事件处理(父容器)
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return this.viewDragHelper.shouldInterceptTouchEvent(ev)
				& this.detectorCompat.onTouchEvent(ev);
	}

	private float downX;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getRawX();
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getRawX() - downX;
			// 请求父容器View不拦截touch事件
			if (moveX > this.viewDragHelper.getTouchSlop()) {
				requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:
			downX = 0;
			break;

		default:
			break;
		}
		this.viewDragHelper.processTouchEvent(event);
		return true;
	}

}
