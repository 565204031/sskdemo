package com.ssk.sskdemo.lollipop;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/20.
 */
public class TabLayoutActivity extends BaseToolBarActivity{
    private String[] titles = {
            "头条",
            "新闻",
            "热点",
            "体育",
            "财经"
    };
    ViewPager viewPager;
    TabLayout tabLayout;
    View nav;
    @Override
    protected int getContentView() {
        return R.layout.lollipop_activity_tablayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager = (ViewPager) findViewById(R.id.vp);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        nav=findViewById(R.id.nav);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //切换颜色--tabLayout  toolbar  statusBar NavigationBar
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //解决状态栏 问题
            //解决底部导航栏问题
            ViewGroup.LayoutParams lp2 = nav.getLayoutParams();
            //1,设置navigationBar的高度(粗暴---文明人)
            if(hasNavigationBar(getWindowManager())) {//有虚拟导航我们才解决
            //if(isNavigationBarAvaliable()){
                lp2.height = lp2.height + getNavigationBarHeight(this);
            }
            nav.setLayoutParams(lp2);
        }
        //初始化首页的颜色
        changeColor(0);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {

            return titles[position];
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            NewsFragment fragment = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;

        }
    }
    private void changeColor(int position) {
        //切换颜色--tabLayout  toolbar  statusBar NavigationBar
        //1,提取调色板
        Palette.from(BitmapFactory.decodeResource(getResources(),NewsFragment.getBackGroudIconRes(position))).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //调色板完成
                Palette.Swatch swatch = palette.getVibrantSwatch();
                int rgb ;
                if(swatch==null){
                    rgb=Color.RED;
                }else{
                    rgb=  swatch.getRgb();
                }

                tabLayout.setBackgroundColor(rgb);
                mToolbar.setBackgroundColor(rgb);
                //api 19~21处理方式(4.4~2.0)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    //设置底部导航的颜色
                    nav.setBackgroundColor(rgb);
                }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){ //api 21以上
                    Window window = getWindow();
                    window.setStatusBarColor(rgb);
                    window.setNavigationBarColor(rgb);
                }else{//api 19以下

                }

            }
        });

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean hasNavigationBar(WindowManager wm){
        Display display = wm.getDefaultDisplay();
        //获取整个屏幕的高度

        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);

        int realHeight = outMetrics.heightPixels;
        int realWidth = outMetrics.widthPixels;

        //内容展示部分的高度

        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        //计算NavigationBar的高度
        int navHeight = realHeight - height;// 竖屏
        int navWidth = realWidth - width;//横屏

        return navHeight>0 || navWidth > 0 ;
    }
    /**
     * 如果同时没有 home和back键 那么表示可能有虚拟导航(适用性不好)
     * @return
     */
    public boolean isNavigationBarAvaliable(){
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        return !(hasBackKey && hasHomeKey);
    }

}
