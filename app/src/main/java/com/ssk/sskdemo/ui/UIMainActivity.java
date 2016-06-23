package com.ssk.sskdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ssk.sskdemo.BaseActivity;
import com.ssk.sskdemo.R;
import com.ssk.sskdemo.lollipop.DrawerActivity;
import com.ssk.sskdemo.lollipop.RecyclerActivity;
import com.ssk.sskdemo.lollipop.TabLayoutActivity;
import com.ssk.sskdemo.lollipop.TextInputActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 杀死凯 on 2016/5/20.
 */
public class UIMainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ListView ls_view;
    private List<HashMap<String,Object>> datas=new ArrayList<HashMap<String,Object>>(){
        {
            add(new HashMap<String, Object>(){{put("title","ViewPager效果");}});
            add(new HashMap<String, Object>(){{put("title","查看图片详情");}});
            add(new HashMap<String, Object>(){{put("title","水波纹效果");}});
            add(new HashMap<String, Object>(){{put("title","导航菜单弹出效果");}});
            add(new HashMap<String, Object>(){{put("title","QQ侧滑删除,QQ消息气泡效果");}});

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ls_view= (ListView) findViewById(R.id.ls_view);
        ls_view.setAdapter(new SimpleAdapter(this,datas,android.R.layout.simple_list_item_1,new String[]{"title"},new int[]{android.R.id.text1}));
        ls_view.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                //ViewPager
                //BaseActivity.start(this,MyViewPagerActivity.class);
                BaseActivity.start(this,NewsDigestActivity.class);
                break;
            case 1:
                //查看大图，移动
                BaseActivity.start(this,ZoomImageActivity.class);
                break;
            case 2:
                //水波纹效果
                BaseActivity.start(this,WaterActivity.class);
                break;
            case 3:
                //导航菜单弹出效果
                BaseActivity.start(this,MenuActivity.class);
                break;
            case 4:
                //QQ侧滑删除,QQ消息气泡
                BaseActivity.start(this,SideslipActivity.class);
                break;
            case 5:
                //网易新闻切换
                BaseActivity.start(this,WangYiActivity.class);
                break;
            case 6:
                //下拉刷新，上拉加载
                BaseActivity.start(this,RefreshActivity.class);
                break;
            case 7:
                //微信下拉拍照效果
                BaseActivity.start(this,WeixinslideActivity.class);
                break;
            case 8:
                //GIF动画引擎
                BaseActivity.start(this,GifActivity.class);
                break;
            case 9:
                //自定义筛选
                BaseActivity.start(this,DividingActivity.class);
                break;
            case 10:
                //微信通讯录字母导航
                BaseActivity.start(this,WxContactsActivity.class);
                break;
            case 11:
                //微信雷达加好友
                BaseActivity.start(this,SatelliteActivity.class);
                break;
            case 12:
                //QQ侧滑
                BaseActivity.start(this,QQSlidingMenuActivity.class);
                break;
        }
    }
}
