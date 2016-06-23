package com.ssk.sskdemo.lollipop;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ssk.sskdemo.BaseToolBarActivity;
import com.ssk.sskdemo.R;

/**
 * Created by 杀死凯 on 2016/5/19.
 * DrawerLayout+NavigationView使用
 */
public class DrawerActivity extends BaseToolBarActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFm;
    private NavigationFragment mNavigationFragment;
    @Override
    protected int getContentView() {
        return R.layout.lollipop_activity_drawer;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDrawerLayout =(DrawerLayout)findViewById(R.id.drawlayout);
        mNavigationView= (NavigationView)findViewById(R.id.navigationView);

        mNavigationView.setNavigationItemSelectedListener(this);

        //主页面的第一个fragment
        mFm = getSupportFragmentManager();
        FragmentTransaction ft = mFm.beginTransaction();
        mNavigationFragment=new NavigationFragment();
        ft.replace(R.id.fl,mNavigationFragment);
        ft.commit();
        //官方提供的可旋转的导航菜单控件
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.draw_open,R.string.draw_close);
        //同步状态(刷新界面)
        drawerToggle.syncState();
        //给Drawlayout设置监听，1）自己实现监听   2)drawerToggle 自带了监听，会监听侧滑事件，同时对导航菜单设置了 旋转的效果
        mDrawerLayout.setDrawerListener(drawerToggle);
//        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
//            //在侧滑菜单滑动的时候调用
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//                //通过自己使用属性动画来 实现当侧滑菜单滑出的时候， 内容部分有一个向右平移的动画
//                Log.i("tz", "slideOffset" + slideOffset);//侧滑的偏移量的百分比
//                View content = mDrawerLayout.getChildAt(0);
//                View menu  = drawerView;
//
//                //侧滑的内容部分进行往右平移
//                ViewHelper.setTranslationX(content,slideOffset * menu.getMeasuredWidth());
//
//                ViewHelper.setScaleX(content,(1-slideOffset*0.7f));
//                ViewHelper.setScaleY(content,(1-slideOffset*0.7f));//0~1
//
//
//            }
//            //侧滑菜单打开的时候 调用
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//
//            }
//            //侧滑菜单关闭的时候 调用
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//            }
//            //侧滑带单状态发生变化的时候调用
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int mItemId = item.getItemId();
        switch (mItemId){
            case R.id.action_gallery:
                //替换gallaryFagment
//                FragmentTransaction ft1 = mFm.beginTransaction();
//                ft1.replace(R.id.fl,new GallaryFragment());
//                ft1.commit();
                mNavigationFragment.setTitle(item.getTitle().toString());
                break;
            case R.id.action_detail:
                //替换DetailFragment
//                FragmentTransaction ft2 = mFm.beginTransaction();
//                ft2.replace(R.id.fl,new DetailFragment());
//                ft2.commit();
                mNavigationFragment.setTitle(item.getTitle().toString());
                break;
            case R.id.action_help:
                //替换HelpFragment
//                FragmentTransaction ft3 = mFm.beginTransaction();
//                ft3.replace(R.id.fl,new HelpFragment());
//                ft3.commit();
                mNavigationFragment.setTitle(item.getTitle().toString());
                break;
            case R.id.action_play:
                //开启播放
                Toast.makeText(this,"播放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_next:
                //开启播放
                Toast.makeText(this,"下一曲",Toast.LENGTH_SHORT).show();
                break;
        }
        //关闭侧滑菜单
        mDrawerLayout.closeDrawer(mNavigationView);
        return false;
    }
}
