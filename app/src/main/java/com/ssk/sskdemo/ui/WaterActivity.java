package com.ssk.sskdemo.ui;

import android.os.Bundle;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/22.
 * 水波纹效果
 */
public class WaterActivity extends BaseToolBarActivity{
    private WaterView wv_view;
    @Override
    protected int getContentView() {
        return R.layout.ui_activity_water;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        wv_view= (WaterView) findViewById(R.id.wv_view);
        wv_view.startAnim();
    }
}
