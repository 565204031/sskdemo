package com.ssk.sskdemo.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssk.sskdemo.BaseActivity;
import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

import java.util.List;

/**
 * Created by 杀死凯 on 2016/5/20.
 * 查看大图，移动
 */
public class ZoomImageActivity extends BaseToolBarActivity{

    private ViewPager mViewPager;
    private int[] mImgs = new int[] { R.mipmap.meinu,R.mipmap.meinu2,R.mipmap.meinu3,R.mipmap.meinu4,R.mipmap.meinu5 };
    private ImageView[] mImageViews = new ImageView[mImgs.length];
    @Override
    protected int getContentView() {
        return R.layout.ui_activity_zoom;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(new PagerAdapter()
        {

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object)
            {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                return arg0 == arg1;
            }

            @Override
            public int getCount()
            {
                return mImgs.length;
            }
        });
    }
}
