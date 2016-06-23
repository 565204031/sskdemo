package com.ssk.sskdemo.ui;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.widget.TextView;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/23.
 * 动画效果
 */
public class AnimationActivity extends BaseToolBarActivity{

    private TextView tv;
    @Override
    protected int getContentView() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        PropertyValuesHolder pvh=   PropertyValuesHolder.ofInt("Background",123,123123);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tv,pvh);
        animator.setDuration(2000);
        animator.start();
    }
}
