package com.ssk.sskdemo.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杀死凯 on 2016/5/24.
 * 导航菜单弹出效果
 */
public class MenuView extends FrameLayout implements View.OnClickListener {

    View mMenu;
    //子控件
    List<View> items=new ArrayList<>();
    boolean mIsMenuOpen = false;
    public MenuView(Context context) {
        super(context);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount()<4){
            throw new IllegalArgumentException("控件必须大于4个");
        }
        for (int i=0;i<getChildCount();i++){
             if(i==0){
                 //主按钮
                 mMenu=getChildAt(0);
                 mMenu.setOnClickListener(this);
             }else{
                 //子按钮
                 items.add(getChildAt(i));
             }
        }
    }
    @Override
    public void onClick(View v) {
        if (v == mMenu) {
            if (!mIsMenuOpen) {
                mIsMenuOpen = true;
                for (int i=0;i<items.size();i++){
                    doAnimateOpen(items.get(i),i, items.size(), 300);
                }
            } else {
                mIsMenuOpen = false;
                for (int i=0;i<items.size();i++){
                    doAnimateClose(items.get(i),i, items.size(), 300);
                }
            }
        }
    }
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1 * 500).start();
    }
    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        set.setDuration(1 * 500).start();
    }
    public void setOnItemClick(@Nullable OnClickListener l){
        for (View item:items){
            item.setOnClickListener(l);
        }
    }
}
