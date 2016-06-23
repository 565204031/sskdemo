package com.ssk.sskdemo.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ssk.sskdemo.BaseActivity;
import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/20.
 *
 */
public class MyViewPagerActivity extends BaseToolBarActivity{
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    int[] imgRes = {R.mipmap.ic_page1,R.mipmap.ic_page2,R.mipmap.ic_page3,R.mipmap.ic_page4,R.mipmap.ic_page5,R.mipmap.ic_page6,R.mipmap.ic_page7};

    @Override
    protected int getContentView() {
        return R.layout.ui_activity_viewpager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        //设置Page间间距
        mViewPager.setPageMargin(20);
        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter = new PagerAdapter()
        {
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ImageView view = new ImageView(MyViewPagerActivity.this);
                view.setImageResource(imgRes[position]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }

            @Override
            public int getCount()
            {
                return imgRes.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o)
            {
                return view == o;
            }
        });
    }
}
