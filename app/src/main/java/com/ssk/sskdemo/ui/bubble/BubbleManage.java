package com.ssk.sskdemo.ui.bubble;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/24.
 * 消息气泡管理类
 */
public class BubbleManage  implements View.OnTouchListener,OnDisappearListener{

    private Context context;
    private View pointView;
    private BubbleView bubbleView;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams lp;
    private Handler handler;

    public BubbleManage(Context context){
        this.context = context;
        this.bubbleView = new BubbleView(context);
        this.handler = new Handler(context.getMainLooper());
        initWindowManager();
    }
    private void initWindowManager(){
        mWindowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        lp=new WindowManager.LayoutParams();
        lp.format= PixelFormat.TRANSLUCENT;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int actionMasked = MotionEventCompat.getActionMasked(event);
        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
                ViewParent parent = v.getParent();
                parent.requestDisallowInterceptTouchEvent(true);

                // 将我们的默认的红点隐藏
                pointView=v;
                v.setVisibility(View.INVISIBLE);

                this.bubbleView.setBubbleText("20");
                this.bubbleView.setCenterPoint(event.getRawX(), event.getRawY());
                //设置回调
                this.bubbleView.setOnDisappearListener(this);

                // 绑定气泡
                this.mWindowManager.addView(bubbleView, lp);
                break;
        }
        this.bubbleView.onTouchEvent(event);
        return true;
    }

    @Override
    public void onDisappear(PointF dragCenter) {
        // 气泡消失
        if (this.mWindowManager != null && this.bubbleView.getParent() != null) {
            // 移除气泡
            this.mWindowManager.removeView(bubbleView);
            // 执行气泡爆炸动画
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.anim_bubble_pop);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView
                    .getDrawable();

            // 创建一个爆炸布局
            final BubbleLayout bubbleLayout = new BubbleLayout(context);
            bubbleLayout.setCenterPoint((int) dragCenter.x, (int) dragCenter.y
                    - BubbleUtils.getStatusBarHeight(context));
            // 绑定气泡爆炸的ImageView
            bubbleLayout.addView(imageView, new FrameLayout.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT));
            // 将我们的气泡的爆炸布局绑定到WindowManager
            this.mWindowManager.addView(bubbleLayout, lp);

            animationDrawable.start();

            // 什么时候我们需要将layout干掉---解决方案：通过handler消息机制，发送延时消息
            this.handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 主线程
                    mWindowManager.removeView(bubbleLayout);
                }
            }, 500);
        }
    }
    @Override
    public void onReset(boolean isOutOfRange) {
        if (this.mWindowManager != null && this.bubbleView.getParent() != null) {
            this.mWindowManager.removeView(bubbleView);
        }
        if(!isOutOfRange){
            //又回来了，显示View
            pointView.setVisibility(View.VISIBLE);
        }
    }
}
