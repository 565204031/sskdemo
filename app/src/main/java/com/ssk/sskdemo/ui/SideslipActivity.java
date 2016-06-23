package com.ssk.sskdemo.ui;

import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;
import com.ssk.sskdemo.ui.bubble.BubbleManage;
import com.ssk.sskdemo.ui.slide.SlideListAdapter;

/**
 * Created by 杀死凯 on 2016/5/24.
 * QQ侧滑
 */
public class SideslipActivity extends BaseToolBarActivity {
    private BubbleManage mBubbleManage;
    private SlideListAdapter adapter;
    private ListView listview;


    @Override
    protected int getContentView() {
        return R.layout.ui_activity_sideslip;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


        listview= (ListView) findViewById(android.R.id.list);
        adapter = new SlideListAdapter(this);
        listview.setAdapter(adapter);
        // 滑动关闭
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    adapter.getSlidingManager().closeAllLayout();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
    }
}
